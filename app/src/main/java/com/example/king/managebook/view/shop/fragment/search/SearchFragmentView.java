package com.example.king.managebook.view.shop.fragment.search;

import com.example.king.managebook.model.response.ClothesSearchPreview;
import java.util.List;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface SearchFragmentView {
    void showLoadingDialog();
    void hideLoadingDialog();
    void showClothesSearchPreviews(List<ClothesSearchPreview> clothesSearchPreviews);
}
