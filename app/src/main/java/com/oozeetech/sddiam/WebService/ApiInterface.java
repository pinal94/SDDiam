package com.oozeetech.sddiam.WebService;

import com.oozeetech.sddiam.Model.RequestModel;
import com.oozeetech.sddiam.Model.ResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("API_LoginPrivate")
    Call<ArrayList<ResponseModel>> getLoginData(@Body RequestModel requestModel);

}
