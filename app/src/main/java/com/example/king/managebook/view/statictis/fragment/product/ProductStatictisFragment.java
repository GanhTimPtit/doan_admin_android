package com.example.king.managebook.view.statictis.fragment.product;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.king.managebook.R;
import com.example.king.managebook.adapter.ClothesStatictisPreviewAdapter;
import com.example.king.managebook.adapter.EndlessLoadingRecyclerViewAdapter;
import com.example.king.managebook.adapter.OrderPreviewAdapter;
import com.example.king.managebook.common.ToastUtils;
import com.example.king.managebook.common.Utils;
import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.model.response.ClothesStatictisPreview;
import com.example.king.managebook.presenters.statictis.product_statictis.ProductStatictisPresent;
import com.example.king.managebook.presenters.statictis.product_statictis.ProductStatictisPresentImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductStatictisFragment extends Fragment implements ProductStatictisFragmentView,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener ,
        SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rg_choise)
    RadioGroup rgChoise;
    @BindView(R.id.rc_product_statictis)
    RecyclerView rcProduct;
    @BindView(R.id.tv_date_start)
    TextView tvDateStart;
    @BindView(R.id.tv_date_end)
    TextView tvDateEnd;
    @BindView(R.id.rb_product_hot)
    RadioButton rbHot;
    @BindView(R.id.rb_product_nohot)
    RadioButton rbNotHot;
    @BindView(R.id.bt_static)
    Button btStatic;
    private String dateStart="";
    private String dateEnd="";
    private List<String> dates;
    private LoadingDialog loadingDialog;
    private ProductStatictisPresent present;
    private ClothesStatictisPreviewAdapter adapter;
    private String status="0";
    private long timeStart=0;
    private long timeEnd=0;
    public ProductStatictisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_statictis, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initData();
    }

    private void initData() {
        loadingDialog= new LoadingDialog(getContext());
        present= new ProductStatictisPresentImpl(getContext(), this);
        dates= new ArrayList<>();

        rgChoise.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_product_hot:{
                        status="0";
                        if(!dateStart.equals("") && !dateEnd.equals("")) {
                            btStatic.setVisibility(View.VISIBLE);
                        }
                        adapter.clear();
                        break;
                    }
                    case R.id.rb_product_nohot:{
                        status="1";
                        if(!dateStart.equals("") && !dateEnd.equals("")) {
                            btStatic.setVisibility(View.VISIBLE);
                        }
                        adapter.clear();
                        break;
                    }
                }
            }
        });
        tvDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.dialogShowDate(getActivity(), "Chọn ngày bắt đầu", new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        String date = String.format("%02d", dayOfMonth)
                                + "-" +
                                String.format("%02d", (monthOfYear + 1))
                                + "-" +
                                year;
                        timeStart= Utils.millisecondsFromDate(date);
                        if(timeStart>timeEnd && !dateEnd.equals("")){
                            tvDateStart.setError("Thời gian bắt đầu trước trước thời gian kết thúc");
                            return;
                        }
                        dateStart= year+"-"+String.format("%02d", (monthOfYear + 1))+"-"+String.format("%02d", dayOfMonth);
                        tvDateStart.setText(date);

                        if(!dateStart.equals("") && !dateEnd.equals("")){
                            dates.clear();
                            dates.add(dateStart);
                            dates.add(dateEnd);
                            adapter.clear();
                            btStatic.setVisibility(View.VISIBLE);
                            tvDateStart.setError(null);
                        }
                    }
                });
            }
        });
        tvDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.dialogShowDate(getActivity(), "Chọn ngày bắt đầu", new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        String date = String.format("%02d", dayOfMonth)
                                + "-" +
                                String.format("%02d", (monthOfYear + 1))
                                + "-" +
                                year;
                        timeEnd = Utils.millisecondsFromDate(date);
                        if(timeStart>timeEnd && !dateStart.equals("")){
                            tvDateEnd.setError("Thời gian kết thúc phải sau thời gian bắt đầu");
                            return;
                        }
                        dateEnd= year+"-"+String.format("%02d", (monthOfYear + 1))+"-"+String.format("%02d", dayOfMonth);
                        tvDateEnd.setText(date);
                        if(!dateStart.equals("") && !dateEnd.equals("")){
                            dates.clear();
                            dates.add(dateStart);
                            dates.add(dateEnd);
                            adapter.clear();
                            btStatic.setVisibility(View.VISIBLE);
                            tvDateEnd.setError(null);
                        }
                    }
                });
            }
        });
        btStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.onRefreshClothesStatictis(status,dates);
                btStatic.setVisibility(View.GONE);
            }
        });
        adapter = new ClothesStatictisPreviewAdapter(getContext());
        adapter.setLoadingMoreListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcProduct.setLayoutManager(linearLayoutManager);
        rcProduct.addItemDecoration(new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation()));
        rcProduct.setHasFixedSize(true);
        rcProduct.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);

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
    public void loadMoreClothesStatictis(List<ClothesStatictisPreview> clothesStatictisPreviews) {
        adapter.addModels(clothesStatictisPreviews, false);
    }

    @Override
    public void refresheClothesStatictis(List<ClothesStatictisPreview> clothesStatictisPreviews) {
        adapter.refresh(clothesStatictisPreviews);
    }

    @Override
    public void onLoadMore() {
        present.onLoadmoreClothesStatictis(status,dates);
    }

    @Override
    public void onRefresh() {
        present.onRefreshClothesStatictis(status,dates);
    }
}
