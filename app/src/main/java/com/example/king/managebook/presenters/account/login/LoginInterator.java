package com.example.king.managebook.presenters.account.login;


import com.example.king.managebook.presenters.BaseInteractor;

public interface LoginInterator extends BaseInteractor {
    void login(String username, String password, OnLoginSuccessListener listener);
}
