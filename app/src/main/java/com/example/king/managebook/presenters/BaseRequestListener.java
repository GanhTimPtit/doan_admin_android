package com.example.king.managebook.presenters;



public interface BaseRequestListener {
    void onServerError(String message);
    void onNetworkConnectionError();
}
