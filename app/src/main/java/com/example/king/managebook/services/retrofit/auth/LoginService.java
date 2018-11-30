package com.example.king.managebook.services.retrofit.auth;

import com.example.king.managebook.common.RequestConstants;

import com.example.king.managebook.model.response.ResponseBody;


import io.reactivex.Observable;
import retrofit2.Response;

import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginService {
    @POST("api/auths/admin/login")
    Observable<Response<ResponseBody<String>>> login(@Header(RequestConstants.AUTHORIZATION) String base64Account);
}
