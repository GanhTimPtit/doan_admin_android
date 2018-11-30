package com.example.king.managebook.presenters.account.register;

import android.content.Context;
import android.widget.Toast;

import com.example.king.managebook.R;
import com.example.king.managebook.common.Constants;
import com.example.king.managebook.common.Utils;
import com.example.king.managebook.model.body.AdminRegisterBody;
import com.example.king.managebook.view.account.register.RegisterActivityView;


public class RegisterPresenterImpl implements RegisterPresenter {
    private Context context;
    private RegisterActivityView registerView;
    private RegisterInterator registerInterator;

    public RegisterPresenterImpl(Context context, RegisterActivityView registerView) {
        this.context = context;
        this.registerView = registerView;
        this.registerInterator = new RegisterInteratorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        registerInterator.onViewDestroy();
    }

    @Override
    public void validateUsernameAndPassword(String username, String password, AdminRegisterBody adminRegisterBody) {
        //Xử lý lỗi dữ liệu đầu vào
        if (username.isEmpty()) {
            registerView.showUserNameError();
            return;
        }
        if (password.isEmpty()) {
            registerView.showPasswordError();
            return;
        }
        if (adminRegisterBody.getFullName().isEmpty()) {
            registerView.showFullNameError();
            return;
        }
        if (adminRegisterBody.getPosition().isEmpty()) {
            registerView.showPossitionError();
            return;
        }
        if(!Utils.isEmailValid(username)){
            registerView.showInvalidUser();
            return;
        }


        registerView.showLoadingDialog();

        registerInterator.register(username, password, adminRegisterBody, new OnRegisterCompleteListener() {
            @Override
            public void onRegisterSuccess(String username) {
                registerView.hideLoadingDialog();
                Utils.setSharePreferenceValues(context,Constants.USER_NAME, username);
                Toast.makeText(context, context.getString(R.string.register_successfully), Toast.LENGTH_SHORT).show();
                registerView.gotoVerifyActivity();
            }

            @Override
            public void onError(String message) {
                registerView.hideLoadingDialog();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAccountExist() {
                registerView.hideLoadingDialog();
                Toast.makeText(context, context.getString(R.string.account_existed), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
