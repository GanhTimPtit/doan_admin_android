package com.example.king.managebook.view.shop.add_clothes;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.king.managebook.R;
import com.example.king.managebook.common.Constants;
import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.model.body.ClothesBody;
import com.example.king.managebook.model.response.ClothesViewModel;
import com.example.king.managebook.presenters.shop.add_clothes.AddClothesPresenter;
import com.example.king.managebook.presenters.shop.add_clothes.AddClothesPresenterImpl;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddClothesActivity extends AppCompatActivity implements AddClothesActivityView {
    private static final int REQUEST_CODE_PICK_AVATAR = 1;
    @BindView(R.id.edt_name_product)
    EditText etNameProduct;
    @BindView(R.id.edt_cost_product)
    EditText etCost;
    @BindView(R.id.edt_detail_product)
    EditText etDescription;
    @BindView(R.id.bt_add)
    Button btAdd;
    @BindView(R.id.img_avatar)
    ImageView imgLogoUrl;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private LoadingDialog loadingDialog;
    private ClothesViewModel clothesViewModel;
    private ClothesBody clothesBody;
    private AddClothesPresenter addClothesPresenter;
    Uri uri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);
        initVariables();
    }
    private void initVariables() {
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        addClothesPresenter = new AddClothesPresenterImpl(this, this);

        clothesViewModel =(ClothesViewModel) getIntent().getSerializableExtra(Constants.KEY_CLOTHES);
        String categoryID= getIntent().getStringExtra(Constants.KEY_CATEGORY_ID);
        setSupportActionBar(toolbar);
        if(clothesViewModel==null && categoryID!=null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
                actionBar.setTitle(R.string.addClothes);
            }
            btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addClothesPresenter.addClothes(uri, etNameProduct.getText().toString(), etDescription.getText().toString(),categoryID, Integer.parseInt(etCost.getText().toString()) );
                }
            });
        }else{
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
                actionBar.setTitle(R.string.update_clothes);
            }
            initData(clothesViewModel);
            btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addClothesPresenter.updateClothes(clothesViewModel.getId(), uri,clothesViewModel.getLogoUrl(), etNameProduct.getText().toString(), etDescription.getText().toString(),categoryID, Integer.parseInt(etCost.getText().toString()) );
                }
            });
        }
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseImg();
            }
        });

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
    private void initData(ClothesViewModel clothesViewModel){
        etNameProduct.setText(clothesViewModel.getName());
        etCost.setText(clothesViewModel.getPrice()+"");
        etDescription.setText(clothesViewModel.getDescription());
        Glide.with(this).load(clothesViewModel.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.logo_clothes)).into(imgLogoUrl);
    }
    private void choseImg(){
        FishBun.with(this)
                .setImageAdapter(new GlideAdapter())
                .setMaxCount(1)
                .setMinCount(1)
                .setActionBarColor(getResources().getColor(R.color.colorPrimary),
                        getResources().getColor(R.color.colorPrimaryDark),
                        false)
                .setActionBarTitleColor(getResources().getColor(android.R.color.white))
                .setButtonInAlbumActivity(false)
                .setCamera(true)
                .exceptGif(true)
                .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(this, R.drawable.ic_back))
                .setOkButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_select))
                .setAllViewTitle(getResources().getString(R.string.selected))
                .setActionBarTitle(getResources().getString(R.string.pick_avatar))
                .textOnNothingSelected(getResources().getString(R.string.must_pick_one_image))
                .setRequestCode(REQUEST_CODE_PICK_AVATAR)
                .startAlbum();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PICK_AVATAR: {
                if (Activity.RESULT_OK == resultCode) {
                    ArrayList<Parcelable> path = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                    uri = Uri.parse(path.get(0).toString());
                    Log.i("img1", "onActivityResult: " + path.get(0).toString());
                    Glide.with(this).load(path.get(0)).apply(new RequestOptions().placeholder(R.drawable.logo_clothes)).into(imgLogoUrl);

                }
                break;
            }
        }
    }
    @Override
    public void showLoadingDialog() {
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.hide();
    }

    @Override
    public void showAddClothesSuccess() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void showUpdateClothesSuccess(ClothesBody clothesBody) {
        Intent intent= new Intent();
        intent.putExtra(Constants.KEY_CLOTHESBODY, clothesBody);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void showErrorLoading(String message) {

    }
}
