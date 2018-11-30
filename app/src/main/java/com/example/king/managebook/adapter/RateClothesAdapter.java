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
import com.example.king.managebook.model.response.RateClothesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by KingIT on 4/26/2018.
 */

public class RateClothesAdapter  extends RecyclerViewAdapter{
    public RateClothesAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_rate_clothes, parent, false);
        return new RateClothesViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        RateClothesViewModel rateClothesViewModel=  getItem(position, RateClothesViewModel.class);
        RateClothesViewHolder viewHolder= (RateClothesViewHolder) holder;
        Glide.with(getContext()).load(rateClothesViewModel.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.avatar_placeholder)).into(viewHolder.imgCustomerAvatar);



        viewHolder.txtCustomerName.setText(rateClothesViewModel.getCustomerName());
        viewHolder.txtTime.setText(Utils.getTimeAndDate(rateClothesViewModel.getRateDate()));
        viewHolder.txtComment.setText(rateClothesViewModel.getMessage());
        viewHolder.ratingAttitude.setRating(rateClothesViewModel.getRating());
    }
    class RateClothesViewHolder extends NormalViewHolder {
            @BindView(R.id.img_customer_avatar)
            CircleImageView imgCustomerAvatar;
            @BindView(R.id.txt_customer_name)
            TextView txtCustomerName;
            @BindView(R.id.txt_time)
            TextView txtTime;
            @BindView(R.id.txt_comment)
            TextView txtComment;
            @BindView(R.id.rating_product)
            MaterialRatingBar ratingAttitude;
        public RateClothesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
