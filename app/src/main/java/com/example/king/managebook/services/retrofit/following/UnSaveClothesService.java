package com.example.king.managebook.services.retrofit.following;

import com.example.king.managebook.common.RequestConstants;
import com.example.king.managebook.model.response.PageList;
import com.example.king.managebook.model.response.ResponseBody;
import com.example.king.managebook.model.response.SaveClothesPreview;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UnSaveClothesService {
    @DELETE("/api/customers/save_clothes/{id}")
    Observable<Response<ResponseBody<PageList<SaveClothesPreview>>>> UnSaveClothes(@Header(RequestConstants.AUTHORIZATION) String accessToken,
                                                                                   @Path("id") String clothesID);
}
