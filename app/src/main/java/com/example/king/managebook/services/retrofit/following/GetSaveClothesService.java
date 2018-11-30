package com.example.king.managebook.services.retrofit.following;

import com.example.king.managebook.common.RequestConstants;
import com.example.king.managebook.model.response.PageList;
import com.example.king.managebook.model.response.ResponseBody;
import com.example.king.managebook.model.response.SaveClothesPreview;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetSaveClothesService {
    @GET("/api/customers/save_clothes")
    Observable<Response<ResponseBody<PageList<SaveClothesPreview>>>> getSaveClothes(
            @Header(RequestConstants.AUTHORIZATION) String accessToken,
            @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
            @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
            @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
            @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);
}
