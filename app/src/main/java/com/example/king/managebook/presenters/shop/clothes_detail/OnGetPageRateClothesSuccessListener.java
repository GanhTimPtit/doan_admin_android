package com.example.king.managebook.presenters.shop.clothes_detail;

import com.example.king.managebook.model.response.RateClothesViewModel;


import java.util.List;

public interface OnGetPageRateClothesSuccessListener {
    void onGetRateClothesSuccess(List<RateClothesViewModel> rateClothesViewModelList);
    void onError(String msg);
}
