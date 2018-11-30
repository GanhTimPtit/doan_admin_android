package com.example.king.managebook.services.retrofit.statictis;

import com.example.king.managebook.common.RequestConstants;
import com.example.king.managebook.model.response.ClothesStatictisPreview;
import com.example.king.managebook.model.response.ConfirmOrderPreview;
import com.example.king.managebook.model.response.CustomerStatictisPreView;
import com.example.king.managebook.model.response.PageList;
import com.example.king.managebook.model.response.ResponseBody;

import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StatictisService {

    @GET("api/admins/customer/statictis")
    Observable<Response<ResponseBody<List<CustomerStatictisPreView>>>> getCustomerStatics();
    @POST("api/admins/bill/statictis/{year}")
    Observable<Response<ResponseBody<List<String>>>> getBillStaticsByYear(@Path("year") String year);
    @POST("/api/admins/product/statictis/nothot")
    Observable<Response<ResponseBody<PageList<ClothesStatictisPreview>>>> getPageClothesStatictisNotHot(@Body List<String> dates,
                                                                                                  @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                                                  @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize);
    @POST("/api/admins/product/statictis/hot")
    Observable<Response<ResponseBody<PageList<ClothesStatictisPreview>>>> getPageClothesStatictisHot(@Body List<String> dates,
                                                                                                  @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                                                  @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize);
}
