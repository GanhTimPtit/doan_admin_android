package com.example.king.managebook.presenters.statictis.bill_statictis;

import android.content.Context;

import com.example.king.managebook.common.ToastUtils;
import com.example.king.managebook.view.statictis.fragment.bill.BillStaticFragmentView;

import java.util.List;

public class BillStatictisPresenterImpl implements BillStatictisPresenter{
    private Context context;
    private BillStatictisInteractor billStatictisInteractor;
    private BillStaticFragmentView billStaticFragmentView;
    public BillStatictisPresenterImpl(Context context, BillStaticFragmentView billStaticFragmentView) {
        this.context = context;
        this.billStaticFragmentView = billStaticFragmentView;
        this.billStatictisInteractor = new BillStatictisInteractorImpl(context);
    }
    @Override
    public void onViewDestroy() {
        billStatictisInteractor.onViewDestroy();
    }

    @Override
    public void staticBillByYear(String year) {
        billStaticFragmentView.showLoadingDialog();
        billStatictisInteractor.callStatictisBillByYear(year, new OnBillStatictisSuccessListener() {
            @Override
            public void onGetSuccessBillStatictisByYear(List<String> billStatictis) {
                billStaticFragmentView.showResultBillStatictisByYear(billStatictis);
                billStaticFragmentView.hideLoadingDialog();
            }

            @Override
            public void onMessageEror(String msg) {
                ToastUtils.quickToast(context, msg);
                billStaticFragmentView.hideLoadingDialog();
            }

            @Override
            public void onServerError(String message) {
                ToastUtils.quickToast(context, message);
                billStaticFragmentView.hideLoadingDialog();
            }

            @Override
            public void onNetworkConnectionError() {
                ToastUtils.quickToast(context, "Không có mạng (not network)");
                billStaticFragmentView.hideLoadingDialog();
            }
        });
    }
}
