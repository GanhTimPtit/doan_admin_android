package com.example.king.managebook.view.account.register;

public interface RegisterActivityView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void gotoVerifyActivity();
    void showUserNameError();
    void showFullNameError();

    void showPossitionError();
    void showPasswordError();
    void showInvalidUser();
}
