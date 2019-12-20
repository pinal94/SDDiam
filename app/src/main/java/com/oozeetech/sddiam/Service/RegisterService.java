package com.oozeetech.sddiam.Service;

import android.app.Activity;

import com.oozeetech.sddiam.Model.RequestModel;
import com.oozeetech.sddiam.Model.ResponseModel;
import com.oozeetech.sddiam.Utils.AsyncProgressDialog;
import com.oozeetech.sddiam.Utils.SDInterface;
import com.oozeetech.sddiam.WebService.ApiClient;
import com.oozeetech.sddiam.WebService.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterService {
    private Activity activity;
    private AsyncProgressDialog asyncProgressDialog;
    private RequestModel requestModel;
    private SDInterface.OnGetRegisterData onGetRegisterData;

    public RegisterService(Activity activity, RequestModel requestModel, final SDInterface.OnGetRegisterData onGetRegisterData) {
        this.activity = activity;
        this.requestModel = requestModel;
        this.onGetRegisterData = onGetRegisterData;

        asyncProgressDialog = AsyncProgressDialog.getInstant(activity);
        asyncProgressDialog.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ResponseModel>> call = apiService.getRegisterData(requestModel);

        call.enqueue(new Callback<ArrayList<ResponseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseModel>> call, Response<ArrayList<ResponseModel>> response) {
                ArrayList<ResponseModel> responseModel = response.body();
                onGetRegisterData.onGetRegisterData(responseModel);
                if (asyncProgressDialog != null && asyncProgressDialog.isShowing()) {
                    asyncProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseModel>> call, Throwable t) {

            }
        });
        if (asyncProgressDialog != null && asyncProgressDialog.isShowing()) {
            asyncProgressDialog.dismiss();
        }

    }
}
