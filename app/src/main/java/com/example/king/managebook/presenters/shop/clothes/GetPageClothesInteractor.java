package com.example.king.managebook.presenters.shop.clothes;

import com.example.king.managebook.presenters.BaseInteractor;
import com.example.king.managebook.presenters.OnRequestCompleteListener;


/**
 * Created by KingIT on 4/22/2018.
 */

public interface GetPageClothesInteractor extends BaseInteractor {
    void getClothesPreviews(String categoryID, int pageIndex, int pageSize,
                            OnGetPageClothesSuccessListener listener);
    void callDeleteClothes(String clothesID, OnRequestCompleteListener listener);

}
