package com.example.king.managebook.presenters.confirmation.order;

import com.example.king.managebook.model.body.OrderConfirmBody;
import com.example.king.managebook.model.body.OrderDeleteBody;
import com.example.king.managebook.presenters.BaseInteractor;
import com.example.king.managebook.presenters.OnRequestCompleteListener;

import java.util.Set;

public interface ConfirmationInteractor extends BaseInteractor {
    void callPageOrderPreview(int status, int pageIndex, int pageSize, OnGetPageOrderSuccessListener listener);
    void callDeleteOrder(OrderDeleteBody body, OnRequestCompleteListener listener);
    void getSuccessConfirm(OrderConfirmBody body, OnRequestCompleteListener listener);
}
