package com.example.king.managebook.presenters.account.register;

import android.content.Context;

import com.example.king.managebook.R;
import com.example.king.managebook.common.Base64UtilAccount;
import com.example.king.managebook.common.ResponseCode;
import com.example.king.managebook.model.body.AdminRegisterBody;
import com.example.king.managebook.model.response.ResponseBody;
import com.example.king.managebook.services.ApiClient;
import com.example.king.managebook.services.retrofit.auth.RegisterService;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RegisterInteratorImpl implements RegisterInterator {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public RegisterInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void register(String username, String password, AdminRegisterBody body, OnRegisterCompleteListener listener) {
        //Gọi đến api đăng ký
        Observable<Response<ResponseBody<String>>> observable = ApiClient.getClient().create(RegisterService.class)
                .register(Base64UtilAccount.getBase64Account(username, password), body);
        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    //mã 200 .đăng ký thành công
                                    listener.onRegisterSuccess(response.body().getData());
                                    break;
                                }
                                case ResponseCode.CONFLICT: {
                                    //mã 409 tài khoản đã tồn tài
                                    listener.onAccountExist();
                                    break;
                                }
                                case ResponseCode.FORBIDDEN: {
                                    //mã 403 mật khẩu không dủ
                                    listener.onError(context.getString(R.string.password_must_be_more_than_six_letter));
                                    break;
                                }
                                default:{
                                    //mặc didnh
                                    listener.onError(context.getString(R.string.server_error));
                                }
                            }
                        },
                        error -> {
                            //lỗi server
                            listener.onError(context.getString(R.string.server_error));
                        }
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        compositeDisposable.clear();
    }
}
