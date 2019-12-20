package com.oozeetech.sddiam.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;
import com.oozeetech.sddiam.Adapter.CountryListFilterAdapter;
import com.oozeetech.sddiam.Model.RequestModel;
import com.oozeetech.sddiam.Model.ResponseModel;
import com.oozeetech.sddiam.R;
import com.oozeetech.sddiam.Service.CountryListService;
import com.oozeetech.sddiam.Service.RegisterService;
import com.oozeetech.sddiam.Utils.SDInterface;
import com.oozeetech.sddiam.Widget.DEditText;
import com.oozeetech.sddiam.Widget.DTextView;
import com.oozeetech.sddiam.Widget.MaterialButton;

import java.util.ArrayList;

public class SignupActivity extends BaseActivity {
    private DEditText edtFirstName, edtCompanyName, edtEmail, edtPassword, edtContactNo;
    private CountryCodePicker spCountryCode;
    private DTextView edtCountry, edtCity, txtCreateAccount;
    private MaterialButton btnSignUp;
    private ArrayList<ResponseModel> arrCountryList, arrCountryFilterList;
    private RequestModel requestModel;
    private String strCountryId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login));
        initView();

        requestModel = new RequestModel();

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    signUPService();
                }
            }
        });

        edtCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrCountryList.size() != 0) {
                    countryStateSelection();
                }
            }
        });

        if (checkInternet()) {
            new CountryListService(SignupActivity.this, requestModel, new SDInterface.OnGetCountryListData() {
                @Override
                public void onGetCountryListData(ArrayList<ResponseModel> responseModel) {
                    arrCountryList = new ArrayList<>();
                    arrCountryFilterList = new ArrayList<>();
                    arrCountryList.addAll(responseModel);
                    arrCountryFilterList.addAll(responseModel);
                }
            });
        } else {
            showNoInternetDialog();
        }
    }

    private void signUPService() {
        if (checkInternet()) {
            requestModel = new RequestModel();
            requestModel.setDomainName(sdApplication.apiDomainName);
            requestModel.setFull_Name(edtFirstName.getText().toString());
            requestModel.setCompany_Name(edtCompanyName.getText().toString());
            requestModel.setLogin_Name(edtEmail.getText().toString());
            requestModel.setLogin_Pass(edtPassword.getText().toString());
            requestModel.setLogin_Pass_Confirm(edtPassword.getText().toString());
            requestModel.setContact_No(edtContactNo.getText().toString());
            requestModel.setCountry_ID(strCountryId);
            requestModel.setCity_ID("0");
            new RegisterService(SignupActivity.this, requestModel, new SDInterface.OnGetRegisterData() {
                @Override
                public void onGetRegisterData(ArrayList<ResponseModel> responseModel) {
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

    private boolean validate() {
        if (edtFirstName.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_firstname), Toast.LENGTH_SHORT);
            return false;
        }

        if (edtCompanyName.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_company_name), Toast.LENGTH_SHORT);
            return false;
        }

        if (edtEmail.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_email), Toast.LENGTH_SHORT);
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches()) {
            showToast(getString(R.string.err_email_invalid), Toast.LENGTH_SHORT);
            return false;
        }

        if (edtPassword.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_password), Toast.LENGTH_SHORT);
            return false;
        }


        if (edtCountry.getText().toString().trim().length() <= 0 || edtCountry.getText().toString().equalsIgnoreCase("Select Country")) {
            showToast(getString(R.string.err_country), Toast.LENGTH_SHORT);
            return false;
        }

       /* if (edtCity.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_city), Toast.LENGTH_SHORT);
            return false;
        }*/


        if (edtContactNo.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_contact_no), Toast.LENGTH_SHORT);
            return false;
        }

        if (edtContactNo.getText().length() > 0) {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber number = null;
            try {
                number = phoneUtil.parse(spCountryCode.getSelectedCountryCodeWithPlus() + edtContactNo.getText().toString(), spCountryCode.getSelectedCountryNameCode());
            } catch (NumberParseException e) {
                e.printStackTrace();
            }
            if (phoneUtil.isValidNumber(number)) {
                return true;
            } else {
                showToast(getString(R.string.err_invalid_contact_no), Toast.LENGTH_SHORT);
                return false;
            }
        }
        return true;
    }

    private void initView() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtCompanyName = findViewById(R.id.edtCompanyName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        spCountryCode = findViewById(R.id.spCountryCode);
        edtContactNo = findViewById(R.id.edtContactNo);
        edtCountry = findViewById(R.id.edtCountry);
        edtCity = findViewById(R.id.edtCity);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtCreateAccount = findViewById(R.id.txtCreateAccount);
    }

    public void countryStateSelection() {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_categories_list, null);
        ImageView imgClose = v.findViewById(R.id.imgClose);

        RecyclerView rvCategories = v.findViewById(R.id.rvCategories);
        DTextView txtTitle = v.findViewById(R.id.txtTitle);
        final EditText edSearch = v.findViewById(R.id.edSearch);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
        dialog.setCancelable(false);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        rvCategories.setLayoutManager(new LinearLayoutManager(getActivity()));
        final CountryListFilterAdapter categoriesAdapter = new CountryListFilterAdapter(getActivity(), arrCountryList,
                arrCountryFilterList, new SDInterface.OnCountryClick() {
            @Override
            public void onCountryClick(String selectedRawItem, String selectedRawItemID) {
                edtCountry.setText(selectedRawItem);
                strCountryId = selectedRawItemID;
                dialog.dismiss();
            }
        });
        rvCategories.setAdapter(categoriesAdapter);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                categoriesAdapter.getFilter().filter(edSearch.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        dialog.show();
    }
}
