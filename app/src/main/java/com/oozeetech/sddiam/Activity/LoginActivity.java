package com.oozeetech.sddiam.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.oozeetech.sddiam.Model.RequestModel;
import com.oozeetech.sddiam.Model.ResponseModel;
import com.oozeetech.sddiam.R;
import com.oozeetech.sddiam.Service.LoginService;
import com.oozeetech.sddiam.Utils.SDInterface;
import com.oozeetech.sddiam.Widget.DEditText;
import com.oozeetech.sddiam.Widget.DTextView;
import com.oozeetech.sddiam.Widget.MaterialButton;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity {
    private DEditText edtEmail,edtPassword;
    private DTextView txtForgotPass;
    private MaterialButton btnLogin;
    private DTextView txtCreateAccount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login));
        initView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if (validate()) {
                    loginService();
                }
            }
        });

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
            }
        });

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("NewApi")
    private void loginService() {
        if (checkInternet()) {
            RequestModel requestModel = new RequestModel();
            requestModel.setAPIDomain(sdApplication.apiDomainName);
            requestModel.setUsername(edtEmail.getText().toString());
            requestModel.setPassword(edtPassword.getText().toString());
            new LoginService(LoginActivity.this, requestModel, new SDInterface.OnGetLoginData() {
                @Override
                public void onGetLoginData(ArrayList<ResponseModel> responseModel) {
                    if (responseModel.get(0).getApiStatus().equalsIgnoreCase("1")){
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("serviceURL", responseModel.get(0).getSuccessResult());
                        startActivity(intent);
                        finishAffinity();
                    }else {
                        showToast(responseModel.get(0).getStatusMsg(), Toast.LENGTH_SHORT);
                    }

                }
            });

        } else {
            showNoInternetDialog();
        }
    }

    private void initView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        txtForgotPass = findViewById(R.id.txtForgotPass);
        btnLogin = findViewById(R.id.btnLogin);
        txtCreateAccount = findViewById(R.id.txtCreateAccount);

    }

    private boolean validate() {
        if (edtEmail.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_email), Toast.LENGTH_SHORT);
            return false;
        }

        if (edtPassword.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_password), Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }
}
