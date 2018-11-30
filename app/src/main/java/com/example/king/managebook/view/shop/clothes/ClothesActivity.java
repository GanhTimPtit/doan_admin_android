package com.example.king.managebook.view.shop.clothes;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.example.king.managebook.R;
import com.example.king.managebook.adapter.ClothesPreviewAdapter;
import com.example.king.managebook.adapter.EndlessLoadingRecyclerViewAdapter;
import com.example.king.managebook.adapter.RecyclerViewAdapter;
import com.example.king.managebook.common.Constants;
import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.model.Category;
import com.example.king.managebook.model.response.ClothesPreview;
import com.example.king.managebook.model.response.PageList;
import com.example.king.managebook.presenters.shop.clothes.GetPageClothesPresenter;
import com.example.king.managebook.presenters.shop.clothes.GetPageClothesPresenterImpl;
import com.example.king.managebook.view.shop.add_clothes.AddClothesActivity;
import com.example.king.managebook.view.shop.clothes_detail.ClothesDetailActivity;
import com.sangcomz.fishbun.define.Define;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClothesActivity extends AppCompatActivity implements ClothesActivityView,
        RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        ClothesPreviewAdapter.OnButtonDeleteClick{
    @BindView(R.id.rc_posts)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fabAdd;
    private ClothesPreviewAdapter adapter;
    private GetPageClothesPresenter presenter;
    private List<ClothesPreview> clothesPreviews;
    private Category category;
    private LoadingDialog loadingDialog;
    private int positionDelete=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        category =(Category) getIntent().getSerializableExtra(Constants.KEY_CATEGORY_ID);
        presenter= new GetPageClothesPresenterImpl(this, this);
        if (category != null) {
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
                actionBar.setTitle(category.getTitle());
            }
        }
        initData();
    }
    private void initData() {
        loadingDialog= new LoadingDialog(this);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ClothesActivity.this, AddClothesActivity.class);
                intent.putExtra(Constants.KEY_CATEGORY_ID, category.getId());
                startActivityForResult(intent, Constants.REQUEST_CODE_CLOTHES_STATE);
            }
        });
        adapter = new ClothesPreviewAdapter(this, this);
        adapter.addOnItemClickListener(this);
        adapter.setLoadingMoreListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);

        presenter.refreshClothesPreviews(category.getId());
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
    public void showLoadingDialog() {
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.hide();
    }

    @Override
    public void showLoadMoreProgress() {
        adapter.showLoadingItem(true);
    }

    @Override
    public void hideLoadMoreProgress() {
        adapter.hideLoadingItem();
    }

    @Override
    public void enableLoadMore(boolean enable) {
        adapter.enableLoadingMore(enable);
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
    public void addClothesPreviews(PageList<ClothesPreview> clothesPreviewPageList) {
        adapter.addModels(clothesPreviewPageList.getResults(), false);
    }

    @Override
    public void refreshClothesPreview(PageList<ClothesPreview> clothesPreviewPageList) {
        adapter.refresh(clothesPreviewPageList.getResults());
        this.clothesPreviews = clothesPreviewPageList.getResults();
    }

    @Override
    public void removeClothes() {
        this.adapter.removeModel(positionDelete);
    }

    @Override
    public void onRefresh() {
        presenter.refreshClothesPreviews(category.getId());
    }

    @Override
    public void onLoadMore() {
        presenter.loadMoreClothesPreviews(category.getId());
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        ClothesPreview clothesPreview = this.adapter.getItem(position, ClothesPreview.class);
        Intent intent = new Intent(ClothesActivity.this, ClothesDetailActivity.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID, clothesPreview.getId());
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_CLOTHES_STATE: {
                if (Activity.RESULT_OK == resultCode) {
                   presenter.refreshClothesPreviews(category.getId());
                }
                break;
            }
        }
    }

    @Override
    public void onClick(int pos) {
        positionDelete=pos;
        ClothesPreview clothesPreview = this.adapter.getItem(pos, ClothesPreview.class);
        presenter.deleteClothes(clothesPreview.getId());
    }
}
