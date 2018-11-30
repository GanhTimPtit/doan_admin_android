package com.example.king.managebook.view.shop.clothes;


import com.example.king.managebook.model.response.ClothesPreview;
import com.example.king.managebook.model.response.PageList;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface ClothesActivityView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showLoadMoreProgress();
    void hideLoadMoreProgress();
    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void addClothesPreviews(PageList<ClothesPreview> clothesPreviewPageList);
    void refreshClothesPreview(PageList<ClothesPreview> clothesPreviewPageList);

    void removeClothes();
}
