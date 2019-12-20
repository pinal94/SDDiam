package com.oozeetech.sddiam.Utils;

import com.oozeetech.sddiam.Model.ResponseModel;

import java.util.ArrayList;

public interface SDInterface {

    public interface OnCountryClick {
        void onCountryClick(String selectedRawItem,String selectedRawItemID);
    }

    public interface OnGetLoginData {
        void onGetLoginData(ArrayList<ResponseModel> responseModel);
    }

    public interface OnGetCountryListData {
        void onGetCountryListData(ArrayList<ResponseModel> responseModel);
    }

    public interface OnGetRegisterData {
        void onGetRegisterData(ArrayList<ResponseModel> responseModel);
    }

    public interface OnGetForgotPasswordData {
        void onGetForgotPasswordData(ArrayList<ResponseModel> responseModel);
    }
}
