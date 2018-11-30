package com.example.king.managebook.presenters.account.login;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.king.managebook.R;
import com.example.king.managebook.common.UserAuth;

import com.example.king.managebook.view.account.login.LoginActivityView;


public class LoginPresenterImpl implements LoginPresenter {
    private Context context;
    private LoginActivityView loginView;
    private LoginInterator loginInterator;

    public LoginPresenterImpl(Context context, LoginActivityView loginView) {
        this.context = context;
        this.loginView = loginView;
        this.loginInterator = new LoginInteratorImpl(context);
    }

    @Override
    public void validateUsernameAndPassword(String username, String password) {
        if (username.isEmpty()) {
            loginView.showEmailInputError();
            return;
        }
        if (password.isEmpty()) {
            loginView.showPasswordInputError();
            return;
        }

        loginView.showProgress();
        loginInterator.login(username, password, new OnLoginSuccessListener() {
            @Override
            public void onLoginSuccess(String name) {
                UserAuth.saveAccessToken(context, name);
                loginView.hideProgress();
                loginView.backToHomeScreen(Activity.RESULT_OK);
            }

            @Override
            public void onAccounNotVerify() {
                loginView.hideProgress();
                Toast.makeText(context, context.getString(R.string.account_not_verify), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                loginView.hideProgress();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onViewDestroy() {
        context = null;
        loginInterator.onViewDestroy();
    }
}
