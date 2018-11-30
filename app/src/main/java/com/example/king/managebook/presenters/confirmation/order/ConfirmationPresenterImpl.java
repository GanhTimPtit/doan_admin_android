package com.example.king.managebook.presenters.confirmation.order;

import android.content.Context;

import com.example.king.managebook.R;
import com.example.king.managebook.common.Constants;
import com.example.king.managebook.common.ToastUtils;
import com.example.king.managebook.model.body.OrderConfirmBody;
import com.example.king.managebook.model.body.OrderDeleteBody;
import com.example.king.managebook.model.response.ConfirmOrderPreview;
import com.example.king.managebook.model.response.PageList;
import com.example.king.managebook.presenters.OnRequestCompleteListener;
import com.example.king.managebook.view.confirmation.ConfirmFragmentView;

import java.util.Set;

public class ConfirmationPresenterImpl implements ConfirmationPresenter{
    private Context context;
    private ConfirmFragmentView confirmFragmentView;
    private ConfirmationInteractor confirmationInteractor;
    int currenIndex = 0;
    public ConfirmationPresenterImpl(Context context, ConfirmFragmentView confirmFragmentView) {
        this.context = context;
        this.confirmFragmentView = confirmFragmentView;
        this.confirmationInteractor = new ConfirmationInteractorImpl(context);
    }

    @Override
    public void onLoadmoreOrder(int status) {
        confirmFragmentView.showLoadMoreProgress();
        confirmFragmentView.enableRefreshing(false);
        confirmationInteractor.callPageOrderPreview(status,currenIndex + 1, Constants.PAGE_SIZE,
                new OnGetPageOrderSuccessListener() {
                    @Override
                    public void onSuccess(PageList<ConfirmOrderPreview> confirmOrderPreviewPageList) {
                        currenIndex++;
                        confirmFragmentView.hideLoadMoreProgress();
                        confirmFragmentView.enableRefreshing(true);
                        if (confirmOrderPreviewPageList.getPageIndex() == confirmOrderPreviewPageList.getTotalPage() - 1) {
                            confirmFragmentView.enableLoadMore(false);
                        }
                        confirmFragmentView.loadMoreOrderViewModel(confirmOrderPreviewPageList.getResults());
                    }

                    @Override
                    public void onError(String msg) {
                        confirmFragmentView.hideLoadMoreProgress();
                        confirmFragmentView.enableRefreshing(true);
                        ToastUtils.quickToast(context, R.string.error_message);
                    }
                });
    }

    @Override
    public void onRefreshOrder(int status) {
        confirmFragmentView.showRefreshingProgress();
        confirmFragmentView.enableRefreshing(true);
        confirmationInteractor.callPageOrderPreview(status,0, Constants.PAGE_SIZE, new OnGetPageOrderSuccessListener() {
            @Override
            public void onSuccess(PageList<ConfirmOrderPreview> confirmOrderPreviewPageList) {
                currenIndex=0;
                if (confirmOrderPreviewPageList.getPageIndex() == confirmOrderPreviewPageList.getTotalPage() - 1) {
                    confirmFragmentView.enableLoadMore(false);
                }
                confirmFragmentView.hideRefreshingProgress();
                confirmFragmentView.enableLoadMore(true);
                confirmFragmentView.refreshOrderViewModel(confirmOrderPreviewPageList.getResults());
                if(confirmOrderPreviewPageList.getTotalItem()==0){
                    confirmFragmentView.showNoResult();
                }else {
                    confirmFragmentView.hideNoResult();
                }
            }

            @Override
            public void onError(String msg) {
                confirmFragmentView.hideNoResult();
                confirmFragmentView.hideRefreshingProgress();
                confirmFragmentView.enableRefreshing(true);
                ToastUtils.quickToast(context, msg);
            }
        });
    }

    @Override
    public void confirmOrder(Set<String> orderIDSet, Set<String> customerIDSet, int status) {
        OrderConfirmBody body= new OrderConfirmBody(orderIDSet,customerIDSet,status);
        confirmFragmentView.showLoadingDialog();
        confirmationInteractor.getSuccessConfirm(body, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                confirmFragmentView.hideLoadingDialog();
                confirmFragmentView.switchToViewMode();
                ToastUtils.quickToast(context, "Phê duyệt đơn hàng thành công");
            }

            @Override
            public void onServerError(String message) {
                confirmFragmentView.hideLoadingDialog();
                confirmFragmentView.switchToViewMode();
                ToastUtils.quickToast(context,message);
            }
        });
    }

    @Override
    public void fetchDeleteOrder(String orderID, String customerID, String msg, int status) {
        OrderDeleteBody body= new OrderDeleteBody(orderID, customerID, msg, status);
        confirmFragmentView.showLoadingDialog();
        confirmationInteractor.callDeleteOrder(body, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                confirmFragmentView.deleteOrderClothes();
                confirmFragmentView.hideLoadingDialog();
            }

            @Override
            public void onServerError(String message) {
                confirmFragmentView.hideLoadingDialog();
                ToastUtils.quickToast(context,message);
            }
        });
    }


    @Override
    public void onViewDestroy() {
        confirmationInteractor.onViewDestroy();
    }
}
