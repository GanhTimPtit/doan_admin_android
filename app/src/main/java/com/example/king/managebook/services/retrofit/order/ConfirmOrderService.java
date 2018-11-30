package com.example.king.managebook.services.retrofit.order;



import com.example.king.managebook.common.RequestConstants;
import com.example.king.managebook.model.body.OrderConfirmBody;
import com.example.king.managebook.model.body.OrderDeleteBody;
import com.example.king.managebook.model.response.ConfirmOrderPreview;
import com.example.king.managebook.model.response.ItemPreview;
import com.example.king.managebook.model.response.PageList;
import com.example.king.managebook.model.response.ResponseBody;

import java.util.List;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConfirmOrderService {
    @GET("/api/admins/orders/{status}")
    Observable<Response<ResponseBody<PageList<ConfirmOrderPreview>>>> getPageComfirmOrder(@Path("status") int status,
                                                                                          @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                                          @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
                                                                                          @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
                                                                                          @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);

    @GET("/api/admins/orders/{oid}/closthes")
    Observable<Response<ResponseBody<List<ItemPreview>>>> getItemsPreview(@Path("oid") String orderID);

    @PUT("/api/admins/orders/confirm")
    Observable<Response<ResponseBody<String>>> confirmOrder(@Body OrderConfirmBody body);
    @PUT("/api/admins/orders/delete")
    Observable<Response<ResponseBody<String>>> deleteOrder(@Body OrderDeleteBody body);

}
