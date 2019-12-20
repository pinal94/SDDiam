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
import com.oozeetech.sddiam.Model.CategoryModel;
import com.oozeetech.sddiam.R;
import com.oozeetech.sddiam.Utils.SDInterface;
import com.oozeetech.sddiam.widget.DEditText;
import com.oozeetech.sddiam.widget.DTextView;
import com.oozeetech.sddiam.widget.MaterialButton;

import java.util.ArrayList;

public class SignupActivity extends BaseActivity {
    private DEditText editRegFirstName;
    private DEditText editRegCompanyName;
    private DEditText editRegEmail;
    private DEditText editPassword;
    private CountryCodePicker spRegCountryCode;
    private DEditText editRegContactNo;
    private DTextView editCountry;
    private DTextView editCity;
    private MaterialButton btnSignup;
    private DTextView tvCreateAccount;
    private ArrayList<CategoryModel> arrCountryList;
    private ArrayList<CategoryModel> arrCountryFilterList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login));
        initView();

        arrCountryList = new ArrayList<>();
        arrCountryFilterList = new ArrayList<>();

        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    signUPService();
                }
            }
        });

        editCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrCountryList.size() != 0) {
                    countryStateSelection();
                }
            }
        });
    }

    private void signUPService() {
        if (checkInternet()) {
            finish();
        } else {
            showNoInternetDialog();
        }
    }

    private boolean validate() {
        if (editRegFirstName.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_firstname), Toast.LENGTH_SHORT);
            return false;
        }

        if (editRegCompanyName.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_company_name), Toast.LENGTH_SHORT);
            return false;
        }

        if (editRegContactNo.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_contact_no), Toast.LENGTH_SHORT);
            return false;
        }
        if (editRegEmail.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_email), Toast.LENGTH_SHORT);
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editRegEmail.getText()).matches()) {
            showToast(getString(R.string.err_email_invalid), Toast.LENGTH_SHORT);
            return false;
        }

        if (editCountry.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_country), Toast.LENGTH_SHORT);
            return false;
        }

        if (editCity.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_city), Toast.LENGTH_SHORT);
            return false;
        }

        if (editPassword.getText().toString().trim().length() <= 0) {
            showToast(getString(R.string.err_password), Toast.LENGTH_SHORT);
            return false;
        }

        if (editRegContactNo.getText().length() > 0) {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber number = null;
            try {
                number = phoneUtil.parse(spRegCountryCode.getSelectedCountryCodeWithPlus() + editRegContactNo.getText().toString(), spRegCountryCode.getSelectedCountryNameCode());
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
        editRegFirstName = findViewById(R.id.editRegFirstName);
        editRegCompanyName = findViewById(R.id.editRegCompanyName);
        editRegEmail = findViewById(R.id.editRegEmail);
        editPassword = findViewById(R.id.editPassword);
        spRegCountryCode = findViewById(R.id.spRegCountryCode);
        editRegContactNo = findViewById(R.id.editRegContactNo);
        editCountry = findViewById(R.id.editCountry);
        editCity = findViewById(R.id.editCity);
        btnSignup = findViewById(R.id.btnSignup);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
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
            public void onCountryClick(String selectedRawItem) {
                editCountry.setText(selectedRawItem);
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
