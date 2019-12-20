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
import com.oozeetech.sddiam.widget.DEditText;
import com.oozeetech.sddiam.widget.DTextView;
import com.oozeetech.sddiam.widget.MaterialButton;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity {
    private DEditText editLoginEmail;
    private DEditText editLoginPass;
    private DTextView tvForgotPass;
    private MaterialButton btnLogin;
    private DTextView tvCreateAccount;

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

        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
            }
        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
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
            requestModel.setAPIDomain("http://192.168.1.135/");
            requestModel.setUsername(editLoginEmail.getText().toString());
            requestModel.setPassword(editLoginPass.getText().toString());
            new LoginService(LoginActivity.this, requestModel, new SDInterface.OnGetLoginData() {
                @Override
                public void onGetLoginData(ArrayList<ResponseModel> responseModel) {
                    if (responseModel.get(0).getApiStatus().equalsIgnoreCase("1")){
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("serviceURL", responseModel.get(0).getSuccessResult());
                        startActivity(intent);
                        finishAffinity();
                    }else {
                        Toast.makeText(LoginActivity.this, responseModel.get(0).getStatusMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } else {
            showNoInternetDialog();
        }
    }

    private void initView() {
        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginPass = findViewById(R.id.editLoginPass);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        btnLogin = findViewById(R.id.btnLogin);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);

    }

    private boolean validate() {
        if (editLoginEmail.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_email), Toast.LENGTH_SHORT);
            return false;
        }

        if (editLoginPass.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_password), Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }
}
