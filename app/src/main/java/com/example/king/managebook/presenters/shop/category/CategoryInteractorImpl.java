package com.example.king.managebook.presenters.shop.category;

import android.content.Context;


import com.example.king.managebook.common.ResponseCode;
import com.example.king.managebook.model.Category;
import com.example.king.managebook.model.body.CategoryBody;
import com.example.king.managebook.model.response.ResponseBody;
import com.example.king.managebook.services.ApiClient;
import com.example.king.managebook.services.ResponseObserver;
import com.example.king.managebook.services.retrofit.clothes.CategoryService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CategoryInteractorImpl implements CategoryInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public CategoryInteractorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getCategory(OnGetCategorySuccessListener listener) {
        Disposable disposable = ApiClient.getClient().create(CategoryService.class)
                .getCategory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<Response<ResponseBody<List<Category>>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBody<List<Category>>> response) {
                        switch (response.code()) {
                            case ResponseCode.OK: {
                                listener.onGetCategory(response.body().getData());
                            }
                            break;

                            default: {
                                listener.onMessageEror(response.message());
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
                        listener.onNetworkConnectionError();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void getAddCategory(CategoryBody categoryBody, OnAddCategorySuccessListener listener) {
        Disposable disposable = ApiClient.getClient().create(CategoryService.class)
                .addCategory(categoryBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<Response<ResponseBody<Category>>>() {
                    @Override
                    public void onSuccess(Response<ResponseBody<Category>> response) {
                        switch (response.code()) {
                            case ResponseCode.OK: {
                                listener.onGetCategory(response.body().getData());
                            }
                            break;

                            default: {
                                listener.onMessageEror(response.message());
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
                        listener.onNetworkConnectionError();
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
