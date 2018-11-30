package com.example.king.managebook.presenters.shop.clothes;

import com.example.king.managebook.presenters.BasePresenter;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface GetPageClothesPresenter extends BasePresenter {
    void loadMoreClothesPreviews(String categoryID);
    void refreshClothesPreviews(String categoryID);
    void deleteClothes(String clothesID);
}
