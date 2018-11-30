package com.example.king.managebook.presenters.shop.clothes_detail;

import android.content.Context;
import android.widget.Toast;


import com.example.king.managebook.model.response.ClothesViewModel;
import com.example.king.managebook.view.shop.clothes_detail.ClothesDetailActivityView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by KingIT on 4/23/2018.
 */

public class ClothesDetailPresenterImpl implements ClothesDetailPresenter {
    private Context context;
    private ClothesDetailActivityView clothesDetailActivityView;
    private ClothesDetailInteractor clothesDetailInteractor;
    private int currentPage = 0;
    private int currentPage1 = 0;

    public ClothesDetailPresenterImpl(Context context, ClothesDetailActivityView clothesDetailActivityView) {
        this.context = context;
        this.clothesDetailActivityView = clothesDetailActivityView;
        this.clothesDetailInteractor = new ClothesDetailInteractorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        clothesDetailInteractor.onViewDestroy();
    }

    @Override
    public void fetchClothesDetail(String clothesID) {
        clothesDetailActivityView.showProgress();
        clothesDetailInteractor.getClothesDetail(clothesID, new OnGetClothesDetailCompleteListener() {
            @Override
            public void onGetClothesDetailComplete(ClothesViewModel clothesViewModel) {
                clothesDetailActivityView.showClothesDetail(clothesViewModel);
                clothesDetailActivityView.hideProgress();
            }

            @Override
            public void onMessageEror(String msg) {
                clothesDetailActivityView.hideProgress();
                clothesDetailActivityView.showErrorLoading(msg);
            }
        });
    }

}
