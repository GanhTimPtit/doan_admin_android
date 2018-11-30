package com.example.king.managebook.presenters.shop.category;


import com.example.king.managebook.model.body.CategoryBody;
import com.example.king.managebook.presenters.BasePresenter;

public interface CategoryPresenter extends BasePresenter {
    void fetchListCategory();
    void addCategory(CategoryBody categoryBody);
}
