package com.example.king.managebook.view.confirmation.detail_order;

import com.example.king.managebook.model.response.ItemPreview;

import java.util.List;

public interface DetailOrderActivityView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showItemPreview(List<ItemPreview> itemPreviews);
}
