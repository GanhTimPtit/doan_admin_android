package com.example.king.managebook.presenters.shop.clothes_detail;

import com.example.king.managebook.model.response.ClothesViewModel;


/**
 * Created by KingIT on 4/23/2018.
 */

public interface OnGetClothesDetailCompleteListener {
    void onGetClothesDetailComplete(ClothesViewModel clothesViewModel);
    void onMessageEror(String msg);
}
