package com.example.king.managebook.view.statictis.fragment.customer;



import com.example.king.managebook.model.response.CustomerStatictisPreView;

import java.util.List;

public interface CustomerStaticFragmentView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showCustomerStatictisList(List<CustomerStatictisPreView> customerStatictisPreViews);

}
