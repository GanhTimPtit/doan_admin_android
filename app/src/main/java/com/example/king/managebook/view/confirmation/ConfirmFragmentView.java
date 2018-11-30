package com.example.king.managebook.view.confirmation;

import com.example.king.managebook.model.response.ConfirmOrderPreview;

import java.util.List;

public interface ConfirmFragmentView {
    void showLoadMoreProgress();
    void hideLoadMoreProgress();
    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void loadMoreOrderViewModel(List<ConfirmOrderPreview> confirmOrderPreviews);
    void refreshOrderViewModel(List<ConfirmOrderPreview> confirmOrderPreviews);
    void showNoResult();
    void hideNoResult();
    void showLoadingDialog();
    void hideLoadingDialog();

    void deleteAllSelectedClothes();

    void switchToConfirmOrder();
    void switchToViewMode();
    void deleteOrderClothes();
}
