package com.example.king.managebook.view.shop.fragment.category;

import com.example.king.managebook.model.Category;


import java.util.List;

public interface CategoryFragmentView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showCategoryList(List<Category> categoryList);
    void showServerErrorDialog();
    void showNoInternetConnectionDialog();
    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void getAddSuccess(Category category);
}
