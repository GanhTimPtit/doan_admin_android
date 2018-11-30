package com.example.king.managebook.presenters.shop.clothes;

import android.content.Context;


import com.example.king.managebook.R;
import com.example.king.managebook.common.ResponseCode;
import com.example.king.managebook.presenters.OnRequestCompleteListener;
import com.example.king.managebook.services.ApiClient;
import com.example.king.managebook.services.retrofit.clothes.ClothesService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by KingIT on 4/22/2018.
 */

public class GetPageClothesInteratorImpl implements GetPageClothesInteractor{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public GetPageClothesInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable= new CompositeDisposable();
    }

    @Override
    public void getClothesPreviews(String categoryID,int pageIndex, int pageSize,  OnGetPageClothesSuccessListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .getClothesPreview(categoryID, pageIndex, pageSize,null, null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    switch (response.code()) {
                        case ResponseCode.OK: {
                            listener.onGetPageClothesPreviewsSuccess(response.body().getData());
                            break;
                        }
                        case ResponseCode.NOT_FOUND: {
                            listener.onMessageEror(response.message());
                            break;
                        }
                        default:{
                            listener.onMessageEror(response.message());
                            break;
                        }
                    }
        }, error -> {
            listener.onMessageEror(context.getString(R.string.server_error));
        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void callDeleteClothes(String clothesID, OnRequestCompleteListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .deleteClothes(clothesID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    switch (response.code()) {
                        case ResponseCode.OK: {
                            listener.onSuccess();
                            break;
                        }
                        case ResponseCode.NOT_FOUND: {
                            listener.onServerError(response.message());
                            break;
                        }
                        default:{
                            listener.onServerError(response.message());
                            break;
                        }
                    }
                }, error -> {
                    listener.onServerError(context.getString(R.string.server_error));
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        this.compositeDisposable.clear();
    }
}
