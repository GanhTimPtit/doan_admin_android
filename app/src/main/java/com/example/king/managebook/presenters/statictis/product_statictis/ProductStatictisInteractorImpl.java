package com.example.king.managebook.presenters.statictis.product_statictis;

import android.content.Context;

import com.example.king.managebook.common.ResponseCode;
import com.example.king.managebook.model.response.ClothesStatictisPreview;
import com.example.king.managebook.model.response.PageList;
import com.example.king.managebook.model.response.ResponseBody;
import com.example.king.managebook.services.ApiClient;
import com.example.king.managebook.services.retrofit.statictis.StatictisService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProductStatictisInteractorImpl implements ProductStatictisInteractor{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ProductStatictisInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void callPageClothesStatictis(String status, List<String> dates, int pageIndex, int pageSize, OnGetPageProductStatictisSuccessListener listener) {
        Observable<Response<ResponseBody<PageList<ClothesStatictisPreview>>>> observable= null;
        switch (status) {
            case "0": {
                observable =
                        ApiClient.getClient().create(StatictisService.class).getPageClothesStatictisHot(dates, pageIndex, pageSize);
                break;
            }
            case "1": {
                observable =
                        ApiClient.getClient().create(StatictisService.class).getPageClothesStatictisNotHot(dates, pageIndex, pageSize);
                break;
            }
        }
        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response->{
                            switch (response.code()){
                                case ResponseCode.OK:{
                                    listener.onGetPageClothesStatictisSuccess(response.body().getData());
                                    break;
                                }
                                default:{
                                    listener.onMessageEror(response.message());
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
