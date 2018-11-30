package com.example.king.managebook.presenters.statictis.customer_statictis;

import com.example.king.managebook.model.response.CustomerStatictisPreView;
import com.example.king.managebook.presenters.BaseRequestListener;

import java.util.List;

public interface OnGetCustomerStatictisSuccessListener extends BaseRequestListener {
    void onGetListCustomerStatictis(List<CustomerStatictisPreView> statictisPreViews);
    void onMessageEror(String msg);
}
