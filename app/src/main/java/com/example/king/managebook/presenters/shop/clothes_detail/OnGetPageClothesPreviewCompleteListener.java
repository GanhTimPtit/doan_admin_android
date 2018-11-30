package com.example.king.managebook.presenters.shop.clothes_detail;


import com.example.king.managebook.model.response.ClothesPreview;
import com.example.king.managebook.model.response.PageList;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface OnGetPageClothesPreviewCompleteListener {
    void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList);
    void onMessageEror(String msg);
}
