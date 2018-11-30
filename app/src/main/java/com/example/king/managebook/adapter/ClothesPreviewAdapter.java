package com.example.king.managebook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.king.managebook.R;
import com.example.king.managebook.common.Utils;
import com.example.king.managebook.model.response.ClothesPreview;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by KingIT on 4/22/2018.
 */

public class ClothesPreviewAdapter extends EndlessLoadingRecyclerViewAdapter{
    private OnButtonDeleteClick onButtonDeleteClick;
    public ClothesPreviewAdapter(Context context, OnButtonDeleteClick onButtonDeleteClick) {
        super(context, false);
        this.onButtonDeleteClick= onButtonDeleteClick;
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
        View view = getInflater().inflate(R.layout.item_clothes, parent, false);
        return new ClothesPreviewHodel(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        ClothesPreviewHodel clothesPreviewHodel= (ClothesPreviewHodel) holder;
        ClothesPreview clothesPreview= getItem(position, ClothesPreview.class);
        Glide.with(getContext()).load(clothesPreview.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.logo_clothes)).into(clothesPreviewHodel.imgAvatar);
        clothesPreviewHodel.tvAcountSave.setText("("+clothesPreview.getNumberSave()+")");
        clothesPreviewHodel.tvName.setText(clothesPreview.getName());
        clothesPreviewHodel.tvType.setText(clothesPreview.getCategory());
        clothesPreviewHodel.tvCost.setText(Utils.formatNumberMoney(clothesPreview.getPrice())+" đ");

        clothesPreviewHodel.ratingClothes.setRating(clothesPreview.getAvarageOfRate());
        clothesPreviewHodel.tvAcountRate.setText("(" + clothesPreview.getNumberAvageOfRate() + ")");
    }
    class ClothesPreviewHodel extends NormalViewHolder{
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.tv_acount_save)
        TextView tvAcountSave;
        @BindView(R.id.tv_full_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_cost)
        TextView tvCost;
        @BindView(R.id.rating_product)
        MaterialRatingBar ratingClothes;
        @BindView(R.id.tv_acount_rate)
        TextView tvAcountRate;
        @BindView(R.id.img_close)
        ImageView imgClose;
        public ClothesPreviewHodel(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new android.app.AlertDialog.Builder(getContext())
                            .setTitle(R.string.delete_clothes)
                            .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", (dialogInterface, i) -> {
                                onButtonDeleteClick.onClick(getAdapterPosition());
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }
    }
    public interface OnButtonDeleteClick{
        void onClick(int pos);
    }
}
