package com.example.king.managebook.view.statictis.fragment.customer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.king.managebook.R;
import com.example.king.managebook.adapter.CustomerStatictisPreviewAdapter;
import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.model.response.CustomerStatictisPreView;
import com.example.king.managebook.presenters.statictis.customer_statictis.CustomerStatictisPresenter;
import com.example.king.managebook.presenters.statictis.customer_statictis.CustomerStatictisPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerStaticFragment extends Fragment implements CustomerStaticFragmentView{
    @BindView(R.id.tv_amount_customer)
    TextView tvTotalCustomer;
    @BindView(R.id.tv_amount_rate)
    TextView tvTotalRate;
    @BindView(R.id.tv_amount_bill)
    TextView tvTotalBill;
    @BindView(R.id.tv_amount_price)
    TextView tvTotalPrice;
    @BindView(R.id.rc_customer_static)
    RecyclerView mRecycleView;
    private CustomerStatictisPreviewAdapter adapter;
    private CustomerStatictisPresenter presenter;
    private LoadingDialog loadingDialog;
    public CustomerStaticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_customer_static, container, false);
        ButterKnife.bind(this,view);
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
        presenter= new CustomerStatictisPresenterImpl(getContext(),this);
        adapter= new CustomerStatictisPreviewAdapter(getContext());
        presenter.getListCustomerStatictis();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation()));
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setAdapter(adapter);

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
    public void showCustomerStatictisList(List<CustomerStatictisPreView> customerStatictisPreViews) {
        adapter.addModels(customerStatictisPreViews, false);
        int totalCustomer=0;
        int totalRate=0;
        int totalBill=0;
        int totalPrice=0;
        for (CustomerStatictisPreView statictisPreView: customerStatictisPreViews) {
            totalCustomer++;
            totalRate+= statictisPreView.getTotalRate();
            totalBill+= statictisPreView.getTotalBill();
            totalPrice+=statictisPreView.getTotalPrice();
        }
        tvTotalCustomer.setText(tvTotalCustomer.getText().toString()+totalCustomer);
        tvTotalRate.setText(tvTotalRate.getText().toString()+totalRate);
        tvTotalBill.setText(tvTotalBill.getText().toString()+totalBill);
        tvTotalPrice.setText(tvTotalPrice.getText().toString()+tvTotalPrice);
    }


}
