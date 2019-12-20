package com.oozeetech.sddiam.Utils;

import com.oozeetech.sddiam.Model.ResponseModel;

import java.util.ArrayList;

public interface SDInterface {

    public interface OnCountryClick {
        void onCountryClick(String selectedRawItem);
    }

    public interface OnGetLoginData {
        void onGetLoginData(ArrayList<ResponseModel> responseModel);
    }
}
