package com.example.king.managebook.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.king.managebook.R;
import com.example.king.managebook.common.Constants;
import com.example.king.managebook.common.Utils;
import com.example.king.managebook.model.google_map.Location;
import com.example.king.managebook.model.response.ConfirmOrderPreview;
import com.example.king.managebook.view.confirmation.MapsOrderActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class OrderPreviewAdapter extends EndlessLoadingRecyclerViewAdapter implements RecyclerViewAdapter.OnItemClickListener,RecyclerViewAdapter.OnItemSelectionChangedListener  {
    private OnButtonDeleteClick onButtonDeleteClick;


    public OrderPreviewAdapter(Context context, OnButtonDeleteClick onButtonDeleteClick) {
        super(context, false);
        setOnItemSelectionChangeListener(this);
        this.onButtonDeleteClick = onButtonDeleteClick;
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
        View view = getInflater().inflate(R.layout.item_order_preview, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        OrderViewHolder viewHolder = (OrderViewHolder) holder;
        ConfirmOrderPreview confirmOrderPreview = getItem(position,ConfirmOrderPreview.class);
        Glide.with(getContext()).load(confirmOrderPreview.getLogoAvatar()).apply(new RequestOptions().placeholder(R.drawable.avatar_placeholder)).into(viewHolder.imgCustomerAvatar);
        viewHolder.tvName.setText("Người Nhận: "+confirmOrderPreview.getNameCustomer());
        viewHolder.tvPhone.setText(confirmOrderPreview.getPhone());
        viewHolder.tvLocation.setText(confirmOrderPreview.getLocationName());
        viewHolder.tvLocation.setSelected(true);
        viewHolder.tvprice.setText(Utils.formatNumberMoney(confirmOrderPreview.getTotalCost())+" đ");
        viewHolder.tvOrderDate.setText(Utils.getDateFromMilliseconds(confirmOrderPreview.getCreatedDate()));
        switch (confirmOrderPreview.getStatus()) {
            case 0: {
                viewHolder.imgStatus.setImageResource(R.drawable.ic_pending);
                break;
            }
            case 1: {
                viewHolder.imgStatus.setImageResource(R.drawable.ic_complete_order);
                viewHolder.imgClose.setVisibility(View.GONE);
                break;
            }
            case 4: {
                viewHolder.imgStatus.setImageResource(R.drawable.ic_delivery_order);
                break;
            }
        }
    }
    @Override
    public void setSelectedMode(boolean isSelected) {
        super.setSelectedMode(isSelected);
        if (isSelected) {
            backup();
            addOnItemClickListener(this);
            notifyItemRangeChanged(0, getItemCount());
        } else {
            removeOnItemClickListener(this);
        }
    }
    @Override
    public void onItemSelectionChanged(RecyclerView.ViewHolder viewHolder, int viewType, boolean isSelected) {
        if (viewType == VIEW_TYPE_NORMAL) {
            OrderViewHolder orderViewHolder = (OrderViewHolder) viewHolder;
            if (isSelected) {
                orderViewHolder.itemView
                        .setBackgroundColor(getContext().getResources()
                                .getColor(R.color.light_gray));
                orderViewHolder.imgDeleteMark.setVisibility(View.VISIBLE);
            } else {
                TypedValue outValue = new TypedValue();
                getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                orderViewHolder.itemView.setBackgroundResource(outValue.resourceId);
                orderViewHolder.imgDeleteMark.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        if (isInSelectedMode() && viewType == VIEW_TYPE_NORMAL) {
            setSelectedItem(position, !isItemSelected(position));
        }
    }

    class OrderViewHolder extends NormalViewHolder {
        @BindView(R.id.img_avatar)
        CircleImageView imgCustomerAvatar;
        @BindView(R.id.txt_title)
        TextView tvName;
        @BindView(R.id.txt_phone)
        TextView tvPhone;
        @BindView(R.id.txt_price)
        TextView tvprice;
        @BindView(R.id.txt_location)
        TextView tvLocation;
        @BindView(R.id.txt_order_date)
        TextView tvOrderDate;
        @BindView(R.id.img_delete_mark)
        ImageView imgDeleteMark;
        @BindView(R.id.img_state)
        ImageView imgStatus;
        @BindView(R.id.img_close)
        ImageView imgClose;
        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), MapsOrderActivity.class);
                    ConfirmOrderPreview confirmOrderPreview= getItem(getPosition(), ConfirmOrderPreview.class);
                    Location location= new Location(confirmOrderPreview.getLat(), confirmOrderPreview.getLog());
                    intent.putExtra(Constants.ADDRESS, confirmOrderPreview.getLocationName());
                    intent.putExtra(Constants.LOCATION, location);
                    getContext().startActivity(intent);
                }
            });
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialogRating = new Dialog(getContext());
                    dialogRating.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogRating.setContentView(R.layout.dialog_delete_order);
                    EditText edt_msg;
                    Button btn_submit;
                    edt_msg = dialogRating.findViewById(R.id.edt_cmt);
                    btn_submit = dialogRating.findViewById(R.id.btn_rate);
                    btn_submit.setOnClickListener(view1 -> {
                        if(edt_msg.getText().toString().equals("")){
                            edt_msg.setError("bạn chưa nhập trường này!");
                            return;
                        }
                        onButtonDeleteClick.onClick(getAdapterPosition(),edt_msg.getText().toString());
                        dialogRating.dismiss();
                    });
                    dialogRating.show();
                    Window window = dialogRating.getWindow();
                    window.setLayout(GridLayoutManager.LayoutParams.MATCH_PARENT, GridLayoutManager.LayoutParams.MATCH_PARENT);
                }
            });
        }
    }
    public interface OnButtonDeleteClick{
        void onClick(int pos, String msg);
    }
}
