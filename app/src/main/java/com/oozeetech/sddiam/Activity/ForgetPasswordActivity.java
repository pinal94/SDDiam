package com.oozeetech.sddiam.Activity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.oozeetech.sddiam.Model.RequestModel;
import com.oozeetech.sddiam.Model.ResponseModel;
import com.oozeetech.sddiam.R;
import com.oozeetech.sddiam.Service.ForgotPasswordService;
import com.oozeetech.sddiam.Utils.SDInterface;
import com.oozeetech.sddiam.Widget.DEditText;
import com.oozeetech.sddiam.Widget.MaterialButton;

import java.util.ArrayList;

public class ForgetPasswordActivity extends BaseActivity {

    private DEditText edtEmail;
    private MaterialButton btnSendRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (forgotPassWordValidate()) {
                    callForGotPasswordService();
                }
            }
        });
    }

    private void callForGotPasswordService() {
        if (checkInternet()) {
            RequestModel requestModel = new RequestModel();
            requestModel.setDomainName(sdApplication.apiDomainName);
            requestModel.setEmailID(edtEmail.getText().toString());
            new ForgotPasswordService(ForgetPasswordActivity.this, requestModel, new SDInterface.OnGetForgotPasswordData() {
                @Override
                public void onGetForgotPasswordData(ArrayList<ResponseModel> responseModel) {
                    showToast(responseModel.get(0).getStatusMsg(), Toast.LENGTH_SHORT);
                    if (responseModel.get(0).getApiStatus().equalsIgnoreCase("1")) {
                        finish();
                    }
                }
            });
        } else {
            showNoInternetDialog();
        }
    }

    private void initView() {
        edtEmail = findViewById(R.id.edtEmail);
        btnSendRequest = findViewById(R.id.btnSendRequest);
    }

    private boolean forgotPassWordValidate() {
        if (edtEmail.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_email), Toast.LENGTH_SHORT);
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches()) {
            showToast(getString(R.string.err_email_invalid), Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }
}
