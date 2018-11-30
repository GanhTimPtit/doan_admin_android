package com.example.king.managebook.presenters.shop.add_clothes;


import android.net.Uri;

import com.example.king.managebook.model.body.CategoryBody;
import com.example.king.managebook.model.body.ClothesBody;
import com.example.king.managebook.presenters.BasePresenter;

import java.net.URL;

public interface AddClothesPresenter extends BasePresenter {
    void addClothes(Uri uri, String name, String des, String categoryID, int cost);
    void updateClothes(String clothesID, Uri uri,String logoAvatar, String name, String des, String categoryID,int cost);
}
