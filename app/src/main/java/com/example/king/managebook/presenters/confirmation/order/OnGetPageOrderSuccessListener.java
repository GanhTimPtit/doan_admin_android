package com.example.king.managebook.presenters.confirmation.order;


import com.example.king.managebook.model.response.ConfirmOrderPreview;
import com.example.king.managebook.model.response.PageList;

public interface OnGetPageOrderSuccessListener {
    void onSuccess(PageList<ConfirmOrderPreview> confirmOrderPreviewPageList);
    void onError(String msg);
}
