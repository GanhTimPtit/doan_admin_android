package com.example.king.managebook.presenters.account.login;

import android.content.Context;

import com.example.king.managebook.R;
import com.example.king.managebook.common.Base64UtilAccount;
import com.example.king.managebook.common.ResponseCode;
import com.example.king.managebook.model.response.AccessToken;
import com.example.king.managebook.model.response.ResponseBody;
import com.example.king.managebook.services.ApiClient;
import com.example.king.managebook.services.retrofit.auth.LoginService;



import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginInteratorImpl implements LoginInterator {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public LoginInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void login(String username, String password, OnLoginSuccessListener listener) {
        Observable<Response<ResponseBody<String>>> observable =
                ApiClient.getClient().create(LoginService.class).login(Base64UtilAccount.getBase64Account(username, password));
        Disposable disposable = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onLoginSuccess(response.body().getData());
                                    break;
                                }
                                case ResponseCode.UNAUTHORIZATION: {
                                    listener.onError(context.getString(R.string.wrong_password_or_email));
                                    break;
                                }
                                case ResponseCode.FORBIDDEN: {
                                    listener.onAccounNotVerify();
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onError(context.getString(R.string.api_not_found));
                                    break;
                                }

                            }
                        },
                        error -> {
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
