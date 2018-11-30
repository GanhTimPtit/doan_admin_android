package com.example.king.managebook.view.confirmation;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.king.managebook.common.ToastUtils;
import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.model.response.ConfirmOrderPreview;
import com.example.king.managebook.presenters.confirmation.order.ConfirmationPresenter;
import com.example.king.managebook.presenters.confirmation.order.ConfirmationPresenterImpl;
import com.example.king.managebook.view.confirmation.detail_order.DetailOrderActivity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryOrderFragment extends Fragment implements ConfirmFragmentView,
        RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        OrderPreviewAdapter.OnButtonDeleteClick{

    @BindView(R.id.rc_order)
    RecyclerView rc_order;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rl_no_result)
    RelativeLayout rl_no_result;
    LoadingDialog loadingDialog;
    OrderPreviewAdapter adapter;
    ConfirmationPresenter presenter;
    private MenuItem itemConfirm;
    private MenuItem itemSubmit;
    private MenuItem itemCancel;
    private MenuItem itemTickAll;
    private int posDelete=0;

    public DeliveryOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_delivery_order, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_confirm, menu);
        itemConfirm = menu.findItem(R.id.action_confirm);
        itemSubmit = menu.findItem(R.id.action_submit);
        itemCancel = menu.findItem(R.id.action_cancel);
        itemTickAll = menu.findItem(R.id.action_submit_all);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_submit:{
                List<ConfirmOrderPreview> confirmOrderPreviews = adapter.getSelectedItemModel(ConfirmOrderPreview.class);
                if(confirmOrderPreviews.size()>0) {
                    Set<String> orderIDSet = new HashSet<>();
                    Set<String> customerIDSet = new HashSet<>();
                    for (int i = 0; i < confirmOrderPreviews.size(); i++) {
                        orderIDSet.add(confirmOrderPreviews.get(i).getId());
                        customerIDSet.add(confirmOrderPreviews.get(i).getCustomerID());
                    }
                    presenter.confirmOrder(orderIDSet, customerIDSet, 1);
                    deleteAllSelectedClothes();
                    switchToViewMode();
                }else{
                    ToastUtils.quickToast(getContext(), "Bạn chưa chọn đơn hàng");
                }
            }
            case R.id.action_confirm: {
                switchToConfirmOrder();
            }
            break;
            case R.id.action_cancel: {
                adapter.recoverBackup();
                switchToViewMode();
            }
            break;
            case R.id.action_submit_all: {
                if(adapter.getSelectedItemModel(ConfirmOrderPreview.class).size()<adapter.getItemCount()){
                    itemTickAll.setIcon(R.drawable.ic_check_white);
                    for (int i = 0; i < adapter.getItemCount(); i++) {
                        adapter.setSelectedItem(i, true);
                    }}else {
                    itemTickAll.setIcon(R.drawable.ic_nocheck_white);
                    for (int i = 0; i < adapter.getItemCount(); i++) {
                        adapter.setSelectedItem(i, false);
                    }
                }

            }
            break;

            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    void initData() {
        presenter = new ConfirmationPresenterImpl(getContext(),this);
        loadingDialog = new LoadingDialog(getContext());
        adapter = new OrderPreviewAdapter(getContext(), this);
        adapter.addOnItemClickListener(this);
        adapter.setLoadingMoreListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rc_order.setLayoutManager(linearLayoutManager);
        rc_order.addItemDecoration(new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation()));
        rc_order.setHasFixedSize(true);
        rc_order.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);
        presenter.onRefreshOrder(4);

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
        rl_no_result.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoResult() {
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
        adapter.removeOnItemClickListener(this);
        adapter.setSelectedMode(true);
        itemConfirm.setVisible(false);
        itemSubmit.setVisible(true);
        itemCancel.setVisible(true);
        itemTickAll.setVisible(true);
    }

    @Override
    public void switchToViewMode() {
        adapter.addOnItemClickListener(this);
        adapter.setSelectedMode(false);
        itemConfirm.setVisible(true);
        itemSubmit.setVisible(false);
        itemCancel.setVisible(false);
        itemTickAll.setVisible(false);
        itemTickAll.setIcon(R.drawable.ic_nocheck_white);
    }

    @Override
    public void deleteOrderClothes() {
        adapter.removeModel(posDelete);
    }

    @Override
    public void onRefresh() {
        presenter.onRefreshOrder(4);
        adapter.removeOnItemClickListener(this);
        switchToViewMode();
    }

    @Override
    public void onLoadMore() {
        presenter.onLoadmoreOrder(4);
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        Intent intent = new Intent(getContext(), DetailOrderActivity.class);
        ConfirmOrderPreview orderPreview = this.adapter.getItem(position, ConfirmOrderPreview.class);
        intent.putExtra(Constants.KEY_ORDER_CLOTHES, orderPreview.getId());
        startActivity(intent);
    }

    @Override
    public void onClick(int pos, String msg) {
        posDelete = pos;
        ConfirmOrderPreview orderPreview = this.adapter.getItem(pos, ConfirmOrderPreview.class);
        presenter.fetchDeleteOrder(orderPreview.getId(), orderPreview.getCustomerID(), msg,3);
    }
}
