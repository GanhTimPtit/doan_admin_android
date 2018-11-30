package com.example.king.managebook.presenters.statictis.bill_statictis;

import com.example.king.managebook.presenters.BaseRequestListener;

import java.util.List;

public interface OnBillStatictisSuccessListener extends BaseRequestListener {
    void onGetSuccessBillStatictisByYear(List<String> billStatictis);
    void onMessageEror(String msg);
}
