package com.example.king.managebook.view.shop.clothes_detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.king.managebook.R;
import com.example.king.managebook.adapter.RateClothesAdapter;
import com.example.king.managebook.common.Constants;
import com.example.king.managebook.common.Utils;
import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.model.body.ClothesBody;
import com.example.king.managebook.model.response.ClothesPreview;
import com.example.king.managebook.model.response.ClothesViewModel;

import com.example.king.managebook.presenters.shop.clothes_detail.ClothesDetailPresenter;
import com.example.king.managebook.presenters.shop.clothes_detail.ClothesDetailPresenterImpl;
import com.example.king.managebook.view.shop.add_clothes.AddClothesActivity;
import com.example.king.managebook.view.shop.clothes.ClothesActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ClothesDetailActivity extends AppCompatActivity implements
        ClothesDetailActivityView{

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.img_clothes)
    ImageView imgClothes;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_name_product)
    TextView tvNameClothes;
    @BindView(R.id.tv_cost_product)
    TextView tvCostClothes;
    @BindView(R.id.rating_product)
    MaterialRatingBar ratingClothes;
    @BindView(R.id.tv_avarage_rate)
    TextView tv_avarage_rate;
    @BindView(R.id.tv_acount_rate)
    TextView tvAcountRate;
    @BindView(R.id.tv_detail_product)
    TextView tvDescriptionCLothes;
    @BindView(R.id.rc_customer_rate)
    RecyclerView rcCustomerRate;
    @BindView(R.id.tv_edit)
    TextView tvEditClothes;

    ClothesViewModel clothesViewModel;
    String clothesID;

    private LoadingDialog loadingDialog;
    private ClothesDetailPresenter clothesDetailPresenter;
    private RateClothesAdapter rateClothesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_detail);
        initVariables();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initVariables() {
        ButterKnife.bind(this);

        loadingDialog = new LoadingDialog(this);
        clothesDetailPresenter = new ClothesDetailPresenterImpl(this, this);
        nestedScrollView.scrollTo(-1, -1);
        nestedScrollView.smoothScrollTo(0, 0);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setTitle(R.string.clothes_detail);
        }

        clothesID = getIntent().getStringExtra(Constants.KEY_CLOTHES_ID);
        clothesDetailPresenter.fetchClothesDetail(clothesID);
        tvEditClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClothesDetailActivity.this, AddClothesActivity.class);
                intent.putExtra(Constants.KEY_CLOTHES, clothesViewModel);
                startActivityForResult(intent, Constants.REQUEST_CODE_CLOTHES_STATE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_CLOTHES_STATE: {
                if (resultCode == Activity.RESULT_OK) {
                    ClothesBody clothesBody= (ClothesBody) data.getSerializableExtra(Constants.KEY_CLOTHESBODY);
                    Glide.with(this).load(clothesBody.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.logo_clothes_wall)).into(imgClothes);
                    tvNameClothes.setText(clothesBody.getName());
                    tvCostClothes.setText(Utils.formatNumberMoney(clothesBody.getCost()) + " đ");
                    tvDescriptionCLothes.setText(clothesBody.getDescription());
                }
                break;
            }
            case Constants.REQUEST_CODE_CLOTHES_ORDER: {
                if (resultCode == Activity.RESULT_OK) {
//                    Intent intent = new Intent(ClothesDetailActivity.this, OrderActivity.class);
//                    intent.putExtra(Constants.KEY_ORDER_CLOTHES, item);
                }
                break;
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideProgress() {
        loadingDialog.hide();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void showClothesDetail(ClothesViewModel clothes) {
        this.clothesViewModel = clothes;
        Glide.with(this).load(clothes.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.logo_clothes_wall)).into(imgClothes);
        tvNameClothes.setText(clothes.getName());
        tvCostClothes.setText(Utils.formatNumberMoney(clothes.getPrice()) + " đ");
        tvDescriptionCLothes.setText(clothes.getDescription());

        rateClothesAdapter = new RateClothesAdapter(this);

        if (clothes.getRateClothesViewModels().size() == 0) {
            tvAcountRate.setText("Sản phẩm chưa có lượt đánh giá nào");
            rcCustomerRate.setVisibility(View.GONE);
        } else {
            tvAcountRate.setText("số lượt đánh giá (" + clothes.getRateClothesViewModels().size() + ")");
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        ratingClothes.setRating(clothes.getAvarageOfRate());
        tv_avarage_rate.setText(String.format("(%.1f/5)", clothes.getAvarageOfRate()));


        rateClothesAdapter = new RateClothesAdapter(ClothesDetailActivity.this);
        rcCustomerRate.setLayoutManager(linearLayoutManager);
        rcCustomerRate.setAdapter(rateClothesAdapter);
        rateClothesAdapter.addModels(clothes.getRateClothesViewModels(), false);
    }

    @Override
    public void showErrorLoading(String message) {

    }


}
