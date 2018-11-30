package com.example.king.managebook.presenters.account.login;

import com.example.king.managebook.presenters.BasePresenter;

public interface LoginPresenter extends BasePresenter {
    void validateUsernameAndPassword(String username, String password);
}
