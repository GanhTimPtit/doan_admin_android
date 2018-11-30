package com.example.king.managebook.view.account.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.king.managebook.MainActivity;
import com.example.king.managebook.R;
import com.example.king.managebook.common.Constants;
import com.example.king.managebook.common.Utils;
import com.example.king.managebook.custom.LoadingDialog;


import com.example.king.managebook.presenters.account.login.LoginPresenter;
import com.example.king.managebook.presenters.account.login.LoginPresenterImpl;
import com.example.king.managebook.view.account.register.RegisterActivity;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class LoginActivity extends AppCompatActivity implements LoginActivityView{
    EditText etEmail;
    EditText etPass;
    Button btLogin;
    TextView tvRegister;
    private LoadingDialog loadingDialog;
    private LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if( Constants.LOGIN_TRUE.equals(Utils.getSharePreferenceValues(this, Constants.STATUS_LOGIN))){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail= findViewById(R.id.et_email);
        etPass= findViewById(R.id.et_pass);
        btLogin= findViewById(R.id.bt_login);
        tvRegister= findViewById(R.id.txt_register);
        loadingDialog= new LoadingDialog(this);
        loginPresenter=new LoginPresenterImpl(this,this);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
                view.startAnimation(animation);
                loginPresenter.validateUsernameAndPassword(etEmail.getText().toString().trim(), etPass.getText().toString().trim());
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_REGISTER);
            }
        });
        String email = getIntent().getStringExtra(Constants.KEY_EMAIL);
        if(email != null) {
            etEmail.setText(email);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_REGISTER: {
                if (resultCode == Activity.RESULT_OK) {
                    if (Utils.getSharePreferenceValues(this, Constants.USER_NAME) != null) {
                        etEmail.setText(Utils.getSharePreferenceValues(this, Constants.USER_NAME));
                    }
                }
            }
            break;
        }
    }
    @Override
    public void showProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideProgress() {
        loadingDialog.hide();
    }

    @Override
    public void showEmailInputError() {
        etEmail.setError("Không được để trống");
    }

    @Override
    public void showPasswordInputError() {
        etPass.setError("Không được để trống");
    }

    @Override
    public void backToHomeScreen(int resultCode) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

}
