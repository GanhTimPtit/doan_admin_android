package com.example.king.managebook.services.retrofit.auth;

import com.example.king.managebook.common.RequestConstants;
import com.example.king.managebook.model.body.AdminRegisterBody;
import com.example.king.managebook.model.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("api/auths/admin/register")
    Observable<Response<ResponseBody<String>>> register(@Header(RequestConstants.AUTHORIZATION) String base64Account,
                                                        @Body AdminRegisterBody body);
}
