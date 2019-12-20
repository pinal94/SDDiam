package com.oozeetech.sddiam.Activity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.oozeetech.sddiam.R;
import com.oozeetech.sddiam.widget.DEditText;
import com.oozeetech.sddiam.widget.MaterialButton;

public class ForgetPasswordActivity extends BaseActivity {

    private DEditText editForgetEmail;
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
            finish();
        } else {
            showNoInternetDialog();
        }
    }

    private void initView() {
        editForgetEmail = findViewById(R.id.editForgetEmail);
        btnSendRequest = findViewById(R.id.btnSendRequest);
    }

    private boolean forgotPassWordValidate() {
        if (editForgetEmail.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_email), Toast.LENGTH_SHORT);
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editForgetEmail.getText()).matches()) {
            showToast(getString(R.string.err_email_invalid), Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }
}
