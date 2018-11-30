package com.example.king.managebook.view.shop.add_clothes;

import com.example.king.managebook.model.body.ClothesBody;
import com.example.king.managebook.model.response.ClothesViewModel;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface AddClothesActivityView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showAddClothesSuccess();
    void showUpdateClothesSuccess(ClothesBody clothesBody);
    void showErrorLoading(String message);

}
