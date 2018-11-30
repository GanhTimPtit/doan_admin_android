package com.example.king.managebook.presenters.shop.clothes_detail;


import com.example.king.managebook.model.body.RateClothesBody;
import com.example.king.managebook.presenters.BaseInteractor;
import com.example.king.managebook.presenters.OnRequestCompleteListener;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface ClothesDetailInteractor extends BaseInteractor {
    void getClothesDetail(String clothesID, OnGetClothesDetailCompleteListener listener);

}
