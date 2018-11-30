package com.example.king.managebook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.king.managebook.R;
import com.example.king.managebook.common.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillStaticYearAdapter extends RecyclerViewAdapter{
    public BillStaticYearAdapter(Context context) {
        super(context, false);
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View view = getInflater().inflate(R.layout.item_bill_statictis_year, parent, false);
        return new BillStaticViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        BillStaticViewHolder billStaticViewHolder= (BillStaticViewHolder) holder;
        String totalPrice= getItem(position, String.class);
        billStaticViewHolder.tvMonth.setText("Tháng "+(position+1));
        billStaticViewHolder.tvBill.setText(Utils.formatNumberMoney(Integer.parseInt(totalPrice)));

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) billStaticViewHolder.tvBill.getLayoutParams();
        int width= 2*( Integer.parseInt(totalPrice)/100000);
        lp.width=width+45;
        billStaticViewHolder.tvBill.setLayoutParams(lp);
    }
    class BillStaticViewHolder extends NormalViewHolder {
        @BindView(R.id.tv_month)
        TextView tvMonth;
        @BindView(R.id.tv_bill_static)
        TextView tvBill;
        public BillStaticViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
