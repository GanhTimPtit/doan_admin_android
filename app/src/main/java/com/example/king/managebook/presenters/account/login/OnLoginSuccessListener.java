package com.example.king.managebook.presenters.account.login;

import com.example.king.managebook.model.response.AccessToken;

public interface OnLoginSuccessListener {
    void onLoginSuccess(String name);
    void onAccounNotVerify();
    void onError(String message);
}
