package com.example.king.managebook.presenters.statictis.product_statictis;

import com.example.king.managebook.presenters.BaseInteractor;

import java.util.List;

public interface ProductStatictisInteractor extends BaseInteractor {
    void callPageClothesStatictis(String status, List<String> dates, int pageIndex, int pageSize, OnGetPageProductStatictisSuccessListener listener);
}
