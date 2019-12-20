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

    @POST("Get_Countrylist")
    Call<ArrayList<ResponseModel>> getCountryListData(@Body RequestModel requestModel);

    @POST("API_RegistrationPrivate")
    Call<ArrayList<ResponseModel>> getRegisterData(@Body RequestModel requestModel);

    @POST("API_ForgetPasswordPrivate")
    Call<ArrayList<ResponseModel>> getForgotPasswordData(@Body RequestModel requestModel);

}
