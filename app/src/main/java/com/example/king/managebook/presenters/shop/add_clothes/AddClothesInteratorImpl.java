package com.example.king.managebook.presenters.shop.add_clothes;

import android.content.Context;

import com.example.king.managebook.common.ResponseCode;
import com.example.king.managebook.model.body.ClothesBody;
import com.example.king.managebook.model.response.ResponseBody;
import com.example.king.managebook.presenters.OnRequestCompleteListener;
import com.example.king.managebook.services.ApiClient;
import com.example.king.managebook.services.ResponseObserver;
import com.example.king.managebook.services.retrofit.clothes.ClothesService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AddClothesInteratorImpl implements AddClothesInterator{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public AddClothesInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }



    @Override
    public void getAddClothes(ClothesBody clothesBody, OnRequestCompleteListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .addClothes(clothesBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<Response<ResponseBody<String>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBody<String>> response) {
                        switch (response.code()) {
                            case ResponseCode.OK: {
                                listener.onSuccess();
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
    public void getUpdateClothes(String clothesID, ClothesBody clothesBody, OnRequestCompleteListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .updateClothes(clothesID,clothesBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<Response<ResponseBody<String>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBody<String>> response) {
                        switch (response.code()) {
                            case ResponseCode.OK: {
                                listener.onSuccess();
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
