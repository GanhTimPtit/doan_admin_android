package com.example.king.managebook.presenters.statictis.product_statictis;

import com.example.king.managebook.presenters.BasePresenter;

import java.util.List;

public interface ProductStatictisPresent extends BasePresenter {
    void onLoadmoreClothesStatictis(String status, List<String> dates);
    void onRefreshClothesStatictis(String status, List<String> dates);
}
