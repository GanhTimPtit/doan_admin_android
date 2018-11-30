package com.example.king.managebook.presenters;



public interface OnRequestCompleteListener {
    void onSuccess();
    void onServerError(String message);
}
