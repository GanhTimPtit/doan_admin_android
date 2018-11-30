package com.example.king.managebook.view.shop.fragment.category;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.king.managebook.R;
import com.example.king.managebook.adapter.CategoryAdapter;

import com.example.king.managebook.adapter.RecyclerViewAdapter;
import com.example.king.managebook.common.Constants;
import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.model.Category;
import com.example.king.managebook.model.body.CategoryBody;
import com.example.king.managebook.presenters.shop.category.CategoryPresenter;
import com.example.king.managebook.presenters.shop.category.CategoryPresenterImpl;
import com.example.king.managebook.view.shop.clothes.ClothesActivity;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements CategoryFragmentView,
        RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.rc_posts)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton fbAdd;
    private CategoryAdapter categoryAdapter;
    private CategoryPresenter categoryPresenter;
    private LoadingDialog loadingDialog;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initData();
    }
    private void initData(){
        loadingDialog= new LoadingDialog(getContext());
        categoryPresenter= new CategoryPresenterImpl(getContext(),this);
        categoryAdapter= new CategoryAdapter(getContext());

        categoryPresenter.fetchListCategory();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(categoryAdapter);

        categoryAdapter.addOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
                Category category = categoryAdapter.getItem(position, Category.class);
                Intent intent = new Intent(getContext(), ClothesActivity.class);
                intent.putExtra(Constants.KEY_CATEGORY_ID, category);
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);
        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
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
    public void showCategoryList(List<Category> categoryList) {
        categoryAdapter.refresh(categoryList);
    }


    @Override
    public void showServerErrorDialog() {

    }



    @Override
    public void showNoInternetConnectionDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.no_internet_connection)
                .setMessage(R.string.please_make_sure_the_intenet_connection_is_enable)
                .setCancelable(false)
                .setPositiveButton(R.string.retry, (dialogInterface, i) -> {
                    categoryPresenter.fetchListCategory();
                }).show();
    }

    @Override
    public void enableLoadMore(boolean enable) {

    }

    @Override
    public void enableRefreshing(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    @Override
    public void showRefreshingProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshingProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void getAddSuccess(Category category) {
        dialogAdd.dismiss();
        categoryAdapter.addModel(category,false);
    }

    @Override
    public void onRefresh() {
        categoryPresenter.fetchListCategory();
    }


    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {

    }
    Dialog dialogAdd;
    void showAddDialog() {
        dialogAdd = new Dialog(getContext());
        dialogAdd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAdd.setContentView(R.layout.add_category_dialog);
        EditText edt_msg;
        Button btn_submit;
        RadioGroup radioGender;
        final int[] gender = {0};


        edt_msg = dialogAdd.findViewById(R.id.edt_cmt);
        btn_submit = dialogAdd.findViewById(R.id.btn_rate);
        radioGender= dialogAdd.findViewById(R.id.rg_choise);
        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rd_choise_direct:{
                        gender[0] =1;
                        break;
                    }
                    case R.id.rd_choise_online:{
                        gender[0] =0;
                        break;
                    }
                }
            }
        });
        CategoryBody categoryBody= new CategoryBody(edt_msg.getText().toString(),null, gender[0]);
        btn_submit.setOnClickListener(v1 -> categoryPresenter.addCategory(categoryBody));
        dialogAdd.show();
    }

}
