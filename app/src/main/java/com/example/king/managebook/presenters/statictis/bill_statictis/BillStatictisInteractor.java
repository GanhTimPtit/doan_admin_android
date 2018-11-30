package com.example.king.managebook.presenters.statictis.bill_statictis;

import com.example.king.managebook.presenters.BaseInteractor;

public interface BillStatictisInteractor extends BaseInteractor {
    void callStatictisBillByYear(String year, OnBillStatictisSuccessListener listener);
}
