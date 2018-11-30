package com.example.king.managebook.presenters.shop.add_clothes;


import com.example.king.managebook.model.body.CategoryBody;
import com.example.king.managebook.model.body.ClothesBody;
import com.example.king.managebook.presenters.BaseInteractor;
import com.example.king.managebook.presenters.OnRequestCompleteListener;


public interface AddClothesInterator extends BaseInteractor {
    void getAddClothes(ClothesBody clothesBody, OnRequestCompleteListener listener);
    void getUpdateClothes(String clothesID, ClothesBody clothesBody, OnRequestCompleteListener listener);
}
