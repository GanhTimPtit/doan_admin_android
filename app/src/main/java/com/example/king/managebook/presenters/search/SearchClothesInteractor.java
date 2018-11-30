package com.example.king.managebook.presenters.search;



import com.example.king.managebook.presenters.BaseInteractor;


public interface SearchClothesInteractor extends BaseInteractor {
    void callClothesSearchPreview(OnGetClothesSearchPreviewSuccessListener listener);
}
