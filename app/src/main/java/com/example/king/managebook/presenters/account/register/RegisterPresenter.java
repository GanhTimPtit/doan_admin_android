package com.example.king.managebook.presenters.account.register;

import com.example.king.managebook.model.body.AdminRegisterBody;
import com.example.king.managebook.presenters.BasePresenter;

public interface RegisterPresenter extends BasePresenter {
    void validateUsernameAndPassword(String username, String password, AdminRegisterBody body);


}
