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
import com.example.king.managebook.model.Category;
import com.example.king.managebook.model.response.CustomerStatictisPreView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerStatictisPreviewAdapter extends RecyclerViewAdapter{

    public CustomerStatictisPreviewAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_customer_statictis, parent, false);
        return new CustomerStatictisViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        CustomerStatictisViewHolder customerStatictisViewHolder= (CustomerStatictisViewHolder) holder;
        CustomerStatictisPreView customerStatictisPreView= getItem(position, CustomerStatictisPreView.class);
        customerStatictisViewHolder.tvSTT.setText((position+1)+"");
        customerStatictisViewHolder.tvFullName.setText(customerStatictisPreView.getName());
        customerStatictisViewHolder.tvTotalRate.setText(customerStatictisPreView.getTotalRate()+"");
        customerStatictisViewHolder.tvTotalBill.setText(customerStatictisPreView.getTotalBill()+"");
        customerStatictisViewHolder.tvTotalPrice.setText(Utils.formatNumberMoney(customerStatictisPreView.getTotalPrice()));
    }
    class CustomerStatictisViewHolder extends NormalViewHolder {
        @BindView(R.id.tv_stt)
        TextView tvSTT;
        @BindView(R.id.tv_full_name)
        TextView tvFullName;
        @BindView(R.id.tv_total_rate)
        TextView tvTotalRate;
        @BindView(R.id.tv_total_bill)
        TextView tvTotalBill;
        @BindView(R.id.tv_total_price)
        TextView tvTotalPrice;
        public CustomerStatictisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
