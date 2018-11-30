package com.example.king.managebook.presenters.statictis.product_statictis;

import android.content.Context;

import com.example.king.managebook.R;
import com.example.king.managebook.common.Constants;
import com.example.king.managebook.common.ToastUtils;
import com.example.king.managebook.model.response.ClothesStatictisPreview;
import com.example.king.managebook.model.response.PageList;
import com.example.king.managebook.view.statictis.fragment.product.ProductStatictisFragmentView;

import java.util.List;

public class ProductStatictisPresentImpl implements ProductStatictisPresent {

    private Context context;
    private ProductStatictisFragmentView productStatictisFragmentView;
    private ProductStatictisInteractor productStatictisInteractor;
    int currenIndex = 0;
    String status="0";
    public ProductStatictisPresentImpl(Context context, ProductStatictisFragmentView productStatictisFragmentView) {
        this.context = context;
        this.productStatictisFragmentView = productStatictisFragmentView;
        this.productStatictisInteractor = new ProductStatictisInteractorImpl(context);
    }

    @Override
    public void onLoadmoreClothesStatictis(String status, List<String> dates) {
        if(!this.status.equals(status)) {
            currenIndex=0;
            this.status= status;
        }
        productStatictisFragmentView.showLoadMoreProgress();
        productStatictisFragmentView.enableRefreshing(false);
        productStatictisInteractor.callPageClothesStatictis(status, dates, currenIndex+1, 20,
                new OnGetPageProductStatictisSuccessListener() {
                    @Override
                    public void onGetPageClothesStatictisSuccess(PageList<ClothesStatictisPreview> clothesStatictisPreviewPageList) {
                        currenIndex++;
                        productStatictisFragmentView.hideLoadMoreProgress();
                        productStatictisFragmentView.enableRefreshing(true);
                        if (clothesStatictisPreviewPageList.getPageIndex() == clothesStatictisPreviewPageList.getTotalPage() - 1) {
                            productStatictisFragmentView.enableLoadMore(false);
                        }
                        productStatictisFragmentView.loadMoreClothesStatictis(clothesStatictisPreviewPageList.getResults());
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        productStatictisFragmentView.hideLoadMoreProgress();
                        productStatictisFragmentView.enableRefreshing(true);
                        ToastUtils.quickToast(context, R.string.error_message);
                    }


                    @Override
                    public void onServerError(String message) {
                        ToastUtils.quickToast(context, message);
                        productStatictisFragmentView.enableRefreshing(true);
                        productStatictisFragmentView.hideLoadMoreProgress();
                    }

                    @Override
                    public void onNetworkConnectionError() {
                        ToastUtils.quickToast(context, "Không có mạng (not network)");
                        productStatictisFragmentView.enableRefreshing(true);
                        productStatictisFragmentView.hideLoadMoreProgress();
                    }
                });
    }

    @Override
    public void onRefreshClothesStatictis(String status, List<String> dates) {
        if(!this.status.equals(status)) {
            currenIndex=0;
            this.status= status;
        }
        productStatictisFragmentView.showRefreshingProgress();
        productStatictisFragmentView.enableRefreshing(true);
        productStatictisInteractor.callPageClothesStatictis(status, dates, 0, 20,
                new OnGetPageProductStatictisSuccessListener() {
                    @Override
                    public void onGetPageClothesStatictisSuccess(PageList<ClothesStatictisPreview> clothesStatictisPreviewPageList) {
                        currenIndex=0;
                        if (clothesStatictisPreviewPageList.getPageIndex() == clothesStatictisPreviewPageList.getTotalPage() - 1) {
                            productStatictisFragmentView.enableLoadMore(false);
                        }
                        productStatictisFragmentView.hideRefreshingProgress();
                        productStatictisFragmentView.enableLoadMore(true);
                        productStatictisFragmentView.refresheClothesStatictis(clothesStatictisPreviewPageList.getResults());
                    }

                    @Override
                    public void onMessageEror(String msg) {
                        productStatictisFragmentView.hideRefreshingProgress();
                        productStatictisFragmentView.enableRefreshing(true);
                        ToastUtils.quickToast(context, R.string.error_message);
                    }


                    @Override
                    public void onServerError(String message) {
                        ToastUtils.quickToast(context, message);
                        productStatictisFragmentView.hideRefreshingProgress();
                        productStatictisFragmentView.enableRefreshing(true);
                    }

                    @Override
                    public void onNetworkConnectionError() {
                        ToastUtils.quickToast(context, "Không có mạng (not network)");
                        productStatictisFragmentView.hideRefreshingProgress();
                        productStatictisFragmentView.enableRefreshing(true);
                    }
                });
    }

    @Override
    public void onViewDestroy() {
        productStatictisInteractor.onViewDestroy();
    }
}
