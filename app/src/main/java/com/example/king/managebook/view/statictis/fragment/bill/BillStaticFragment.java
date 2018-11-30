package com.example.king.managebook.view.statictis.fragment.bill;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.king.managebook.R;
import com.example.king.managebook.adapter.BillStaticYearAdapter;
import com.example.king.managebook.adapter.CustomerStatictisPreviewAdapter;
import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.presenters.statictis.bill_statictis.BillStatictisPresenter;
import com.example.king.managebook.presenters.statictis.bill_statictis.BillStatictisPresenterImpl;
import com.example.king.managebook.presenters.statictis.customer_statictis.CustomerStatictisPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillStaticFragment extends Fragment implements BillStaticFragmentView{

    @BindView(R.id.rc_bill_static)
    RecyclerView rcBillStatic;
    private BillStaticYearAdapter adapter;
    private BillStatictisPresenter presenter;
    private LoadingDialog loadingDialog;
    public BillStaticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bill_static, container, false);
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
        presenter= new BillStatictisPresenterImpl(getContext(),this);
        adapter= new BillStaticYearAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcBillStatic.setLayoutManager(linearLayoutManager);
        rcBillStatic.addItemDecoration(new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation()));
        rcBillStatic.setHasFixedSize(true);
        rcBillStatic.setAdapter(adapter);
        presenter.staticBillByYear("2018");
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
    public void showResultBillStatictisByYear(List<String> billStatictis) {
        adapter.addModels(billStatictis,false);
    }
}
