package com.example.king.managebook.presenters.statictis.customer_statictis;

import android.content.Context;

import com.example.king.managebook.common.ResponseCode;
import com.example.king.managebook.model.response.CustomerStatictisPreView;
import com.example.king.managebook.model.response.ResponseBody;
import com.example.king.managebook.services.ApiClient;
import com.example.king.managebook.services.ResponseObserver;
import com.example.king.managebook.services.retrofit.clothes.ClothesService;
import com.example.king.managebook.services.retrofit.statictis.StatictisService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CustomerStatictisInteratorImpl implements CustomerStatictisInterator {

    private Context context;
    private CompositeDisposable compositeDisposable;

    public CustomerStatictisInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }
    @Override
    public void callListCustomerStatictis(OnGetCustomerStatictisSuccessListener listener) {
        Disposable disposable = ApiClient.getClient().create(StatictisService.class)
                .getCustomerStatics()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<Response<ResponseBody<List<CustomerStatictisPreView>>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBody<List<CustomerStatictisPreView>>> response) {
                        switch (response.code()) {
                            case ResponseCode.OK: {
                                listener.onGetListCustomerStatictis(response.body().getData());
                            }
                            break;

                            default: {
                                listener.onServerError(response.message());
                                break;
                            }
                        }
                    }

                    @Override
                    public void onServerError(String message) {
                        listener.onServerError(message);
                    }

                    @Override
                    public void onNetworkConnectionError() {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        context=null;
        compositeDisposable.clear();
    }
}
