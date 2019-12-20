package com.oozeetech.sddiam.Model;

import java.util.List;

/**
 * Created by Pinal on 30/11/2019.
 */

public class DataModel {

    private Boolean isLoggedIn;

    private List<CategoryModel> CountryList;

    public List<CategoryModel> getCountryList() {
        return CountryList;
    }

    public void setCountryList(List<CategoryModel> countryList) {
        CountryList = countryList;
    }

    public Boolean getLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
