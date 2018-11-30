package com.example.king.managebook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.king.managebook.R;
import com.example.king.managebook.common.Utils;
import com.example.king.managebook.model.response.ClothesStatictisPreview;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ClothesStatictisPreviewAdapter extends EndlessLoadingRecyclerViewAdapter{

    public ClothesStatictisPreviewAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initLoadingViewHolder(ViewGroup parent) {
        View loadingView = getInflater().inflate(R.layout.item_load_more, parent, false);
        return new LoadingViewHolder(loadingView);
    }

    @Override
    protected void bindLoadingViewHolder(LoadingViewHolder loadingViewHolder, int position) {

    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_clothes_statictis_preview, parent, false);
        return new ClothesStatictisViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        ClothesStatictisViewHolder customerStatictisViewHolder= (ClothesStatictisViewHolder) holder;
        ClothesStatictisPreview customerStatictisPreView= getItem(position, ClothesStatictisPreview.class);
        Glide.with(getContext()).load(customerStatictisPreView.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.logo_clothes)).into(customerStatictisViewHolder.imgLogo);
        customerStatictisViewHolder.tvFullName.setText(customerStatictisPreView.getName());
        if(customerStatictisPreView.getTotalOrder()==0){
            customerStatictisViewHolder.tvAmount.setVisibility(View.GONE);
        }else {
            customerStatictisViewHolder.tvAmount.setText(customerStatictisPreView.getTotalOrder() + "");
        }
        customerStatictisViewHolder.tvPrice.setText(Utils.formatNumberMoney(customerStatictisPreView.getPrice())+"đ");
    }


    class ClothesStatictisViewHolder extends NormalViewHolder {
        @BindView(R.id.img_avatar)
        CircleImageView imgLogo;
        @BindView(R.id.txt_title)
        TextView tvFullName;
        @BindView(R.id.txt_price)
        TextView tvPrice;
        @BindView(R.id.tv_amount_order)
        TextView tvAmount;
        public ClothesStatictisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
