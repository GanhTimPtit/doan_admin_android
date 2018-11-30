package com.example.king.managebook.presenters.confirmation.detail_order;

import android.content.Context;


import com.example.king.managebook.R;
import com.example.king.managebook.common.ResponseCode;
import com.example.king.managebook.services.ApiClient;
import com.example.king.managebook.services.retrofit.order.ConfirmOrderService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by KingIT on 4/22/2018.
 */

public class GetItemsInteratorImpl implements GetItemsInteractor{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public GetItemsInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable= new CompositeDisposable();
    }

    @Override
    public void getItemPreviews(String orderID, OnGetItemsSuccessListener listener) {
        Disposable disposable = ApiClient.getClient().create(ConfirmOrderService.class)
                .getItemsPreview(orderID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    switch (response.code()) {
                        case ResponseCode.OK: {
                            listener.onGetListPreviewsSuccess(response.body().getData());
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
    public void onViewDestroy() {
        this.compositeDisposable.clear();
    }
}
