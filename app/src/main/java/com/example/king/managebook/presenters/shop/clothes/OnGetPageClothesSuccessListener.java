package com.example.king.managebook.presenters.shop.clothes;

import com.example.king.managebook.model.response.ClothesPreview;
import com.example.king.managebook.model.response.PageList;


/**
 * Created by KingIT on 4/22/2018.
 */

public interface OnGetPageClothesSuccessListener {
    void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList);
    void onMessageEror(String msg);
}
