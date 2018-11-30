package com.example.king.managebook.view.statictis.fragment.bill;

import java.util.List;

public interface BillStaticFragmentView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showResultBillStatictisByYear(List<String> billStatictis);
}
