package com.example.king.managebook.presenters.shop.category;

import android.content.Context;

import com.example.king.managebook.common.ToastUtils;
import com.example.king.managebook.model.Category;
import com.example.king.managebook.model.body.CategoryBody;
import com.example.king.managebook.view.shop.fragment.category.CategoryFragmentView;


import java.util.List;

public class CategoryPresenterImpl implements CategoryPresenter {
    private Context context;
    private CategoryInteractor categoryInteractor;
    private CategoryFragmentView categoryFragmentView;

    public CategoryPresenterImpl(Context context, CategoryFragmentView categoryFragmentView) {
        this.context = context;
        this.categoryFragmentView= categoryFragmentView;
        this.categoryInteractor = new CategoryInteractorImpl(context);
    }

    @Override
    public void fetchListCategory() {
        categoryFragmentView.showRefreshingProgress();
        categoryFragmentView.enableRefreshing(true);
        categoryInteractor.getCategory(new OnGetCategorySuccessListener() {
            @Override
            public void onGetCategory(List<Category> categoryList) {
                categoryFragmentView.showCategoryList(categoryList);
                categoryFragmentView.hideRefreshingProgress();
            }

            @Override
            public void onMessageEror(String msg) {
                ToastUtils.quickToast(context, msg);
                categoryFragmentView.hideRefreshingProgress();
            }

            @Override
            public void onServerError(String message) {
                ToastUtils.quickToast(context,message);
                categoryFragmentView.hideRefreshingProgress();
            }

            @Override
            public void onNetworkConnectionError() {
                ToastUtils.quickToast(context, "Không có mạng (not network)");
                categoryFragmentView.hideRefreshingProgress();
            }
        });
    }

    @Override
    public void addCategory(CategoryBody categoryBody) {
        categoryFragmentView.showLoadingDialog();
        categoryInteractor.getAddCategory(categoryBody, new OnAddCategorySuccessListener() {
            @Override
            public void onGetCategory(Category category) {
                categoryFragmentView.getAddSuccess(category);
                categoryFragmentView.hideLoadingDialog();
            }

            @Override
            public void onMessageEror(String msg) {
                ToastUtils.quickToast(context,msg);
                categoryFragmentView.hideLoadingDialog();
            }

            @Override
            public void onServerError(String message) {
                ToastUtils.quickToast(context,message);
                categoryFragmentView.hideLoadingDialog();
            }

            @Override
            public void onNetworkConnectionError() {
                categoryFragmentView.showNoInternetConnectionDialog();
                categoryFragmentView.hideRefreshingProgress();
            }
        });
    }


    @Override
    public void onViewDestroy() {
        categoryInteractor.onViewDestroy();
    }
}
