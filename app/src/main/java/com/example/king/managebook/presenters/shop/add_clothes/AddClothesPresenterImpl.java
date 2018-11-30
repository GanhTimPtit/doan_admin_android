package com.example.king.managebook.presenters.shop.add_clothes;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.king.managebook.common.ToastUtils;
import com.example.king.managebook.model.body.ClothesBody;
import com.example.king.managebook.presenters.OnRequestCompleteListener;
import com.example.king.managebook.view.shop.add_clothes.AddClothesActivityView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public class AddClothesPresenterImpl implements AddClothesPresenter {
    private Context context;
    private AddClothesInterator addClothesInterator;
    private AddClothesActivityView addClothesActivityView;

    public AddClothesPresenterImpl(Context context, AddClothesActivityView addClothesActivityView) {
        this.context = context;
        this.addClothesActivityView = addClothesActivityView;
        this.addClothesInterator = new AddClothesInteratorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        addClothesInterator.onViewDestroy();
    }

    @Override
    public void addClothes(Uri uri, String name, String des, String categoryID, int cost) {
        addClothesActivityView.showLoadingDialog();
        ClothesBody clothesBody= new ClothesBody(name, null, categoryID,cost, des);
        if (uri != null) {
            FirebaseApp.initializeApp(context);
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            //  FirebaseOptions opts = FirebaseApp.getInstance().getOptions();
            StorageReference sr = firebaseStorage.getReference();
            StorageReference storageReference = sr.getStorage().getReference().child("ClothesAdd/" +UUID.randomUUID().toString());
            storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                clothesBody.setLogoUrl(taskSnapshot.getDownloadUrl().toString());
                Log.i("firebaswURI", "onSuccess: " + taskSnapshot.getDownloadUrl().toString());
                add(clothesBody);
            }).addOnFailureListener(e -> {
                Log.i("firebaswURI", "onSuccess:" + e.getCause().toString());
                Toast.makeText(context, "Upload Image Fail!", Toast.LENGTH_SHORT).show();
                return;
            });
        }


    }

    @Override
    public void updateClothes(String clothesID, Uri uri, String logoAvatar, String name, String des, String categoryID, int cost) {
        addClothesActivityView.showLoadingDialog();
        ClothesBody clothesBody= new ClothesBody(name, logoAvatar, categoryID,cost, des);
        if (uri != null) {
            FirebaseApp.initializeApp(context);
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference sr = firebaseStorage.getReference();
            StorageReference storageReference = sr.getStorage().getReference().child("ClothesUpdate/" + clothesID);
            storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                clothesBody.setLogoUrl(taskSnapshot.getDownloadUrl().toString());
                Log.i("firebaswURI", "onSuccess: " + taskSnapshot.getDownloadUrl().toString());
                update(clothesBody, clothesID);
            }).addOnFailureListener(e -> {
                Log.i("firebaswURI", "onSuccess:" + e.getCause().toString());
                Toast.makeText(context, "Upload Image Fail!", Toast.LENGTH_SHORT).show();
                return;
            });
        }
        else{
            update(clothesBody,clothesID);
        }

    }
    private void add(ClothesBody clothesBody){
        addClothesInterator.getAddClothes(clothesBody, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                addClothesActivityView.showAddClothesSuccess();
                addClothesActivityView.hideLoadingDialog();
            }

            @Override
            public void onServerError(String message) {
                ToastUtils.quickToast(context, message);
                addClothesActivityView.hideLoadingDialog();
            }
        });
    }
    private void update(ClothesBody clothesBody, String clothesID){
        addClothesInterator.getUpdateClothes(clothesID,clothesBody, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                addClothesActivityView.showUpdateClothesSuccess(clothesBody);
                addClothesActivityView.hideLoadingDialog();
            }

            @Override
            public void onServerError(String message) {
                ToastUtils.quickToast(context, message);
                addClothesActivityView.hideLoadingDialog();
            }
        });
    }
}
