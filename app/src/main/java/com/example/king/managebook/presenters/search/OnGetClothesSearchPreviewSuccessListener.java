package com.example.king.managebook.presenters.search;

import com.example.king.managebook.model.response.ClothesSearchPreview;
import com.example.king.managebook.presenters.BaseRequestListener;

import java.util.List;

public interface OnGetClothesSearchPreviewSuccessListener extends BaseRequestListener {
    void onGetClothesSearchPreview(List<ClothesSearchPreview> searchPreviewList);
    void onMessageEror(String msg);
}
