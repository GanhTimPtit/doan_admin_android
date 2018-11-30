package com.example.king.managebook.view.statictis.fragment.product;

import com.example.king.managebook.model.response.ClothesStatictisPreview;

import java.util.List;

public interface ProductStatictisFragmentView {
    void showLoadMoreProgress();
    void hideLoadMoreProgress();
    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void loadMoreClothesStatictis(List<ClothesStatictisPreview> clothesStatictisPreviews);
    void refresheClothesStatictis(List<ClothesStatictisPreview> clothesStatictisPreviews);
}
