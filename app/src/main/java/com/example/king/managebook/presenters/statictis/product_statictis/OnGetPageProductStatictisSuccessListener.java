package com.example.king.managebook.presenters.statictis.product_statictis;

import com.example.king.managebook.model.response.ClothesStatictisPreview;
import com.example.king.managebook.model.response.PageList;
import com.example.king.managebook.presenters.BaseRequestListener;

import java.util.List;

public interface OnGetPageProductStatictisSuccessListener extends BaseRequestListener {
    void onGetPageClothesStatictisSuccess(PageList<ClothesStatictisPreview> clothesStatictisPreviewPageList);
    void onMessageEror(String msg);
}
