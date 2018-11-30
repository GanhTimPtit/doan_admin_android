package com.example.king.managebook.presenters.account.register;

import com.example.king.managebook.model.body.AdminRegisterBody;
import com.example.king.managebook.presenters.BaseInteractor;

public interface RegisterInterator extends BaseInteractor {
    void register(String username, String password,
                  AdminRegisterBody body,
                  OnRegisterCompleteListener listener);
}
