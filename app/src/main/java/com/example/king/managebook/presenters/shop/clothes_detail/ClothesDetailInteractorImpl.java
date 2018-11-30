package com.example.king.managebook.presenters.shop.clothes_detail;

import android.content.Context;


import com.example.king.managebook.R;
import com.example.king.managebook.common.ResponseCode;
import com.example.king.managebook.model.response.ClothesPreview;
import com.example.king.managebook.model.response.ClothesViewModel;
import com.example.king.managebook.model.response.ResponseBody;
import com.example.king.managebook.services.ApiClient;
import com.example.king.managebook.services.retrofit.clothes.ClothesService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by KingIT on 4/23/2018.
 */

public class ClothesDetailInteractorImpl implements ClothesDetailInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ClothesDetailInteractorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewDestroy() {
        this.compositeDisposable.clear();
    }

    @Override
    public void getClothesDetail(String clothesID, OnGetClothesDetailCompleteListener listener) {
        Observable<Response<ResponseBody<ClothesViewModel>>> observable= ApiClient.getClient().create(ClothesService.class)
                    .getClothesViewModelWithoutAuth(clothesID);
        Disposable disposable = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onGetClothesDetailComplete(response.body().getData());
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onMessageEror(response.message());
                                    break;
                                }
                                default: {
                                    listener.onMessageEror(response.message());
                                    break;
                                }

                            }
                        }, error -> {
                            listener.onMessageEror(context.getString(R.string.server_error));
                        });
        compositeDisposable.add(disposable);
    }



}
