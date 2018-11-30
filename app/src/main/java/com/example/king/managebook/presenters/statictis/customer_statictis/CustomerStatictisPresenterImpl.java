package com.example.king.managebook.presenters.statictis.customer_statictis;

import android.content.Context;

import com.example.king.managebook.common.ToastUtils;
import com.example.king.managebook.model.Category;
import com.example.king.managebook.model.response.CustomerStatictisPreView;
import com.example.king.managebook.presenters.shop.add_clothes.AddClothesInteratorImpl;
import com.example.king.managebook.presenters.shop.category.OnGetCategorySuccessListener;
import com.example.king.managebook.view.statictis.fragment.customer.CustomerStaticFragmentView;

import java.util.List;

public class CustomerStatictisPresenterImpl implements CustomerStatictisPresenter{

    private Context context;
    private CustomerStatictisInterator customerStatictisInterator;
    private CustomerStaticFragmentView customerStaticFragmentView;

    public CustomerStatictisPresenterImpl(Context context, CustomerStaticFragmentView customerStaticFragmentView) {
        this.context = context;
        this.customerStaticFragmentView = customerStaticFragmentView;
        this.customerStatictisInterator = new CustomerStatictisInteratorImpl(context);
    }
    @Override
    public void getListCustomerStatictis() {
        customerStaticFragmentView.showLoadingDialog();
        customerStatictisInterator.callListCustomerStatictis(new OnGetCustomerStatictisSuccessListener() {
            @Override
            public void onGetListCustomerStatictis(List<CustomerStatictisPreView> statictisPreViews) {
                customerStaticFragmentView.showCustomerStatictisList(statictisPreViews);
                customerStaticFragmentView.hideLoadingDialog();
            }

            @Override
            public void onMessageEror(String msg) {
                ToastUtils.quickToast(context, msg);
                customerStaticFragmentView.hideLoadingDialog();
            }

            @Override
            public void onServerError(String message) {
                ToastUtils.quickToast(context, message);
                customerStaticFragmentView.hideLoadingDialog();
            }

            @Override
            public void onNetworkConnectionError() {
                ToastUtils.quickToast(context, "Không có mạng (not network)");
                customerStaticFragmentView.hideLoadingDialog();
            }
        });
    }
    @Override
    public void onViewDestroy() {
        customerStatictisInterator.onViewDestroy();
    }

}
