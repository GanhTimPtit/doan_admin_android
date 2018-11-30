package com.example.king.managebook.presenters.confirmation.detail_order;


import com.example.king.managebook.model.response.ItemPreview;

import java.util.List;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface OnGetItemsSuccessListener {
    void onGetListPreviewsSuccess(List<ItemPreview> itemPreviewList);
    void onMessageEror(String msg);
}
