package com.example.king.managebook.services.retrofit.clothes;

import com.example.king.managebook.common.RequestConstants;
import com.example.king.managebook.model.Category;
import com.example.king.managebook.model.body.CategoryBody;
import com.example.king.managebook.model.body.ClothesBody;
import com.example.king.managebook.model.body.RateClothesBody;
import com.example.king.managebook.model.response.ClothesPreview;
import com.example.king.managebook.model.response.ClothesSearchPreview;
import com.example.king.managebook.model.response.ClothesViewModel;
import com.example.king.managebook.model.response.PageList;
import com.example.king.managebook.model.response.RateClothesViewModel;
import com.example.king.managebook.model.response.ResponseBody;
import com.example.king.managebook.model.response.SaveClothesPreview;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface ClothesService {
    @GET("/api/products/clothes/{id}/category")
    Observable<Response<ResponseBody<PageList<ClothesPreview>>>> getClothesPreview(@Path("id") String categoryID,
                                                                                   @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                                   @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
                                                                                   @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
                                                                                   @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);


    @GET("/api/products/clothes")
    Observable<Response<ResponseBody<PageList<ClothesPreview>>>> getClothesPreview(@Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                                   @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
                                                                                   @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
                                                                                   @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);



    @GET("/api/products/clothes/{id}")
    Observable<Response<ResponseBody<ClothesViewModel>>> getClothesViewModelWithoutAuth(@Path("id") String clothesID);

    @POST("api/admins/clothes")
    Observable<Response<ResponseBody<String>>> addClothes(@Body ClothesBody clothesBody);
    @PUT("api/admins/clothes/{id}")
    Observable<Response<ResponseBody<String>>> updateClothes(@Path("id") String clothesID,@Body ClothesBody clothesBody);

    @GET("/api/products/search/clothes/")
    Observable<Response<ResponseBody<List<ClothesSearchPreview>>>> getClothesSearchPreview();
    @DELETE("/api/admins/clothes/{id}")
    Observable<Response<ResponseBody<String>>> deleteClothes(@Path("id") String clothesID);

}
