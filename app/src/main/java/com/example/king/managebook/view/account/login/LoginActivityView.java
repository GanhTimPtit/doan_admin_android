package com.example.king.managebook.view.account.login;

/**
 * Created by KingIT on 4/7/2018.
 */

public interface LoginActivityView {
    void showProgress();
    void hideProgress();
    void showEmailInputError();
    void showPasswordInputError();
    void backToHomeScreen(int resultCode);
}
