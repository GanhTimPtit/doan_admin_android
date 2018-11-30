package com.example.king.managebook.presenters.account.register;

public interface OnRegisterCompleteListener  {
    void onRegisterSuccess(String username);
    void onError(String message);
    void onAccountExist();
}
