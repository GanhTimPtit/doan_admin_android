package com.example.king.managebook.presenters.shop.category;

import com.example.king.managebook.model.Category;
import com.example.king.managebook.presenters.BaseRequestListener;

import java.util.List;

public interface OnAddCategorySuccessListener extends BaseRequestListener {
    void onGetCategory(Category category);
    void onMessageEror(String msg);
}
