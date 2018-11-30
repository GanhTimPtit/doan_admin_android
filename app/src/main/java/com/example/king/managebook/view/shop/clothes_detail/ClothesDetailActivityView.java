package com.example.king.managebook.view.shop.clothes_detail;

import com.example.king.managebook.model.response.ClothesViewModel;

import java.util.List;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface ClothesDetailActivityView {
    void showProgress();
    void hideProgress();
    void showClothesDetail(ClothesViewModel clothesViewModel);
    void showErrorLoading(String message);

}
