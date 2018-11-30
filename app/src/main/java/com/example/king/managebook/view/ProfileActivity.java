package com.example.king.managebook.view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.widget.ImageButton;

import android.widget.TextView;

import com.example.king.managebook.R;

import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.model.Profile;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PICK_AVATAR = 1;
    LoadingDialog loadingDialog;
    CircleImageView img_avt;
    TextView txt_name;
    TextView txt_birthday;
    TextView txt_address;
    TextView txt_email;
    ImageButton btn_edit_profile;
    ImageButton
            imgCamera;
    Toolbar toolbar;
    Profile profile;
    Uri uri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initWidget();
    }
    void initWidget() {
        img_avt = findViewById(R.id.img_avatar);
        txt_name = findViewById(R.id.txt_name);
        txt_birthday = findViewById(R.id.txt_birthday);
        txt_address = findViewById(R.id.txt_address);
        txt_email = findViewById(R.id.txt_email);
        btn_edit_profile = findViewById(R.id.btn_edit_user_profile);
        imgCamera= findViewById(R.id.img_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        imgCamera.setOnClickListener(v -> {
            updateImage();
        });
        btn_edit_profile.setOnClickListener(v -> {
            updateImage();
        });
        loadingDialog = new LoadingDialog(this);
     //   showProfile(Utils.getHeaderProfile(this));
    }
    public void updateImage(){
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

                break;
            }
        }
    }
    public void showAlertDialog(String s){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Avatar");
        builder.setMessage(s);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    public void showProfile(Profile prof) {

    }

}

