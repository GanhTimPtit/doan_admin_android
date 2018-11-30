package com.example.king.managebook.services.retrofit.clothes;

import com.example.king.managebook.model.Category;
import com.example.king.managebook.model.body.CategoryBody;
import com.example.king.managebook.model.response.ResponseBody;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoryService {

    @GET("api/products/category")
    Observable<Response<ResponseBody<List<Category>>>> getCategory();
    @POST("api/admins/addCategory")
    Observable<Response<ResponseBody<Category>>> addCategory(@Body CategoryBody categoryBody);
}
