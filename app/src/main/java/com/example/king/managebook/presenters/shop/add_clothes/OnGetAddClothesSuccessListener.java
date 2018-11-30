package com.example.king.managebook.presenters.shop.add_clothes;

import com.example.king.managebook.model.Category;
import com.example.king.managebook.presenters.BaseRequestListener;

import java.util.List;

public interface OnGetAddClothesSuccessListener extends BaseRequestListener {
    void onGetAdd(List<Category> categoryList);
    void onMessageEror(String msg);
}
