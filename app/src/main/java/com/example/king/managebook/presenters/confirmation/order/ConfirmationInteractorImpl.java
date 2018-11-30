package com.example.king.managebook.presenters.confirmation.order;

import android.content.Context;

import com.example.king.managebook.common.ResponseCode;
import com.example.king.managebook.model.body.OrderConfirmBody;
import com.example.king.managebook.model.body.OrderDeleteBody;
import com.example.king.managebook.model.response.ConfirmOrderPreview;
import com.example.king.managebook.model.response.PageList;
import com.example.king.managebook.model.response.ResponseBody;
import com.example.king.managebook.presenters.OnRequestCompleteListener;
import com.example.king.managebook.services.ApiClient;
import com.example.king.managebook.services.retrofit.order.ConfirmOrderService;

import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ConfirmationInteractorImpl implements ConfirmationInteractor{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ConfirmationInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void callPageOrderPreview(int status, int pageIndex, int pageSize, OnGetPageOrderSuccessListener listener) {
        Observable<Response<ResponseBody<PageList<ConfirmOrderPreview>>>> observable =
                ApiClient.getClient().create(ConfirmOrderService.class).getPageComfirmOrder(status, pageIndex,pageSize,null,null);

        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onSuccess(response.body().getData());
                                    break;
                                }
                                default:{
                                    listener.onError(response.message());
                                    break;
                                }
                            }
                        },
                        error->{
                            listener.onError(error.getMessage());
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void callDeleteOrder(OrderDeleteBody body, OnRequestCompleteListener listener) {
        Disposable disposable = ApiClient.getClient()
                .create(ConfirmOrderService.class)
                .deleteOrder(body)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onSuccess();
                                    break;
                                }
                                default:{
                                    listener.onServerError(response.message());
                                    break;
                                }
                            }
                        },
                        error->{
                            listener.onServerError(error.getMessage());
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getSuccessConfirm(OrderConfirmBody body, OnRequestCompleteListener listener) {
        Disposable disposable = ApiClient.getClient()
                .create(ConfirmOrderService.class)
                .confirmOrder(body)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onSuccess();
                                    break;
                                }
                                default:{
                                    listener.onServerError(response.message());
                                    break;
                                }
                            }
                        },
                        error->{
                            listener.onServerError(error.getMessage());
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }
}
