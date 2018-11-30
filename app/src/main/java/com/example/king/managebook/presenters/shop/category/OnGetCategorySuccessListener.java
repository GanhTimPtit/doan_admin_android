package com.example.king.managebook.presenters.shop.category;

import com.example.king.managebook.model.Category;
import com.example.king.managebook.presenters.BaseRequestListener;

import java.util.List;

public interface OnGetCategorySuccessListener extends BaseRequestListener {
    void onGetCategory(List<Category> categoryList);
    void onMessageEror(String msg);
}
