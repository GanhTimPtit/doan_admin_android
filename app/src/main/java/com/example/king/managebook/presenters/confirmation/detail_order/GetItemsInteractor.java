package com.example.king.managebook.presenters.confirmation.detail_order;


import com.example.king.managebook.presenters.BaseInteractor;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface GetItemsInteractor extends BaseInteractor {
    void getItemPreviews(String orderID,
                            OnGetItemsSuccessListener listener);
}
