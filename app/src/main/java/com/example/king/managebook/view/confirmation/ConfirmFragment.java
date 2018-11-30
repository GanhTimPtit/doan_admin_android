package com.example.king.managebook.view.confirmation;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.king.managebook.R;
import com.example.king.managebook.adapter.EndlessLoadingRecyclerViewAdapter;
import com.example.king.managebook.adapter.OrderPreviewAdapter;
import com.example.king.managebook.adapter.RecyclerViewAdapter;
import com.example.king.managebook.common.Constants;
import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.model.response.ConfirmOrderPreview;
import com.example.king.managebook.presenters.confirmation.order.ConfirmationPresenter;
import com.example.king.managebook.presenters.confirmation.order.ConfirmationPresenterImpl;
import com.example.king.managebook.view.confirmation.detail_order.DetailOrderActivity;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmFragment extends Fragment implements ConfirmFragmentView,
        RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener{

    @BindView(R.id.rc_order)
    RecyclerView rc_order;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rl_no_result)
    RelativeLayout rl_no_result;
    LoadingDialog loadingDialog;
    OrderPreviewAdapter adapter;
    ConfirmationPresenter presenter;

    public ConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initData();
    }



    void initData() {
        presenter = new ConfirmationPresenterImpl(getContext(),this);
        loadingDialog = new LoadingDialog(getContext());
        adapter = new OrderPreviewAdapter(getContext(),null);
        adapter.addOnItemClickListener(this);
        adapter.setLoadingMoreListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rc_order.setLayoutManager(linearLayoutManager);
        rc_order.addItemDecoration(new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation()));
        rc_order.setHasFixedSize(true);
        rc_order.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);
        presenter.onRefreshOrder(1);

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
    public void loadMoreOrderViewModel(List<ConfirmOrderPreview> confirmOrderPreviews) {
        adapter.addModels(confirmOrderPreviews, false);
    }

    @Override
    public void refreshOrderViewModel(List<ConfirmOrderPreview> confirmOrderPreviews) {
        adapter.refresh(confirmOrderPreviews);
    }

    @Override
    public void showNoResult() {
        swipeRefreshLayout.setVisibility(View.GONE);
        rl_no_result.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoResult() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        rl_no_result.setVisibility(View.GONE);
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
    public void deleteAllSelectedClothes() {
        adapter.removeAllSelectedItems();
    }

    @Override
    public void switchToConfirmOrder() {

    }

    @Override
    public void switchToViewMode() {

    }

    @Override
    public void deleteOrderClothes() {

    }

    @Override
    public void onRefresh() {
        presenter.onRefreshOrder(1);
        adapter.removeOnItemClickListener(this);
        switchToViewMode();
    }

    @Override
    public void onLoadMore() {
        presenter.onLoadmoreOrder(1);
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        Intent intent = new Intent(getContext(), DetailOrderActivity.class);
        ConfirmOrderPreview orderPreview = this.adapter.getItem(position, ConfirmOrderPreview.class);
        intent.putExtra(Constants.KEY_ORDER_CLOTHES, orderPreview.getId());
        startActivity(intent);
    }
}
