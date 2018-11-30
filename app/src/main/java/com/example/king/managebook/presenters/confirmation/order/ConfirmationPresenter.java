package com.example.king.managebook.presenters.confirmation.order;

import com.example.king.managebook.presenters.BasePresenter;

import java.util.Set;

public interface ConfirmationPresenter extends BasePresenter {
    void onLoadmoreOrder(int status);
    void onRefreshOrder(int status);
    void confirmOrder(Set<String> orderIDSet, Set<String> customerIDSet, int status);
    void fetchDeleteOrder(String orderID, String customerID,String msg, int status);
}
