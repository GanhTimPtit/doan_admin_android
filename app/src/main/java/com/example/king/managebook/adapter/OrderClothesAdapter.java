package com.example.king.managebook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.king.managebook.R;
import com.example.king.managebook.common.Utils;
import com.example.king.managebook.model.response.ProductOderPreview;



import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderClothesAdapter extends EndlessLoadingRecyclerViewAdapter {
    Context context;

    public OrderClothesAdapter(Context context) {
        super(context, false);
        this.context = context;
    }

    @Override
    protected RecyclerView.ViewHolder initLoadingViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_load_more, parent, false);
        return new OrderClothesViewHolder(view);
    }

    @Override
    protected void bindLoadingViewHolder(LoadingViewHolder loadingViewHolder, int position) {

    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_clothes, parent, false);
        return new OrderClothesViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        OrderClothesViewHolder viewHolder = (OrderClothesViewHolder) holder;
        ProductOderPreview productOderPreview = getItem(position,ProductOderPreview.class);
        Glide.with(getContext()).load(productOderPreview.getLogoClothes()).apply(new RequestOptions().placeholder(R.drawable.logo_clothes)).into(viewHolder.imgAvatar);

        viewHolder.tvName.setText(productOderPreview.getNameClothes());
        viewHolder.tvprice.setText("Giá : "+Utils.formatNumberMoney(productOderPreview.getPrice())+" vnđ/chiếc");
        viewHolder.txt_amount.setText(productOderPreview.getAmount()+"");
    }

    class OrderClothesViewHolder extends NormalViewHolder {
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.txt_title)
        TextView tvName;
        @BindView(R.id.tv_cost)
        TextView tvprice;
        @BindView(R.id.tv_total_count)
        TextView txt_amount;
        @BindView(R.id.txt_order_date)
        TextView txt_order_date;


        public OrderClothesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
