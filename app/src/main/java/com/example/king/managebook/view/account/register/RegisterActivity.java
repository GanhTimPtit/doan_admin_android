package com.example.king.managebook.view.account.register;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import com.example.king.managebook.R;
import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.model.User;
import com.example.king.managebook.model.body.AdminRegisterBody;
import com.example.king.managebook.presenters.account.register.RegisterPresenter;
import com.example.king.managebook.presenters.account.register.RegisterPresenterImpl;

public class RegisterActivity extends AppCompatActivity implements RegisterActivityView{
    EditText etEmail;
    EditText etPass;
    EditText etConfirmPassword;
    EditText etAddress;
    EditText etName;
    Button btRegister;
    private LoadingDialog loadingDialog;
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etEmail= findViewById(R.id.et_email);
        etPass= findViewById(R.id.et_pass);
        etConfirmPassword= findViewById(R.id.et_comfirm);
        etAddress= findViewById(R.id.et_address);
        etName= findViewById(R.id.et_name);
        btRegister= findViewById(R.id.bt_register);
        loadingDialog= new LoadingDialog(this);
        registerPresenter= new RegisterPresenterImpl(this,this);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
                view.startAnimation(animation);
                if(!etConfirmPassword.getText().toString().equals(etPass.getText().toString())){
                    etConfirmPassword.setError("Pass chưa khớp, nhập lại");
                    return;
                }
                AdminRegisterBody adminRegisterBody= new AdminRegisterBody(etName.getText().toString(), etAddress.getText().toString());
                registerPresenter.validateUsernameAndPassword(etEmail.getText().toString(),etPass.getText().toString(), adminRegisterBody);
            }
        });
    }


    @Override
    public void showLoadingDialog() {
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.hide();
    }

    @Override
    public void gotoVerifyActivity() {
        setResult(Activity.RESULT_OK);
        finish();

    }

    @Override
    public void showUserNameError() {
        etEmail.setError("Bạn nhập email");
    }

    @Override
    public void showFullNameError() {
        etName.setError("Bạn nhập tên");
    }

    @Override
    public void showPossitionError() {
        etAddress.setError("Bạn nhập vị trí");
    }

    @Override
    public void showPasswordError() {
        etPass.setError("Bạn nhập pass");
    }

    @Override
    public void showInvalidUser() {
        etEmail.setError("Bạn nhập email");
    }
}
