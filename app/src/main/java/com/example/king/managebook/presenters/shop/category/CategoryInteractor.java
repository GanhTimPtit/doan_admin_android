package com.example.king.managebook.presenters.shop.category;


import com.example.king.managebook.model.body.CategoryBody;
import com.example.king.managebook.presenters.BaseInteractor;
import com.example.king.managebook.presenters.OnRequestCompleteListener;

public interface CategoryInteractor extends BaseInteractor {
    void getCategory(OnGetCategorySuccessListener listener);
    void getAddCategory(CategoryBody categoryBody, OnAddCategorySuccessListener listener);
}
