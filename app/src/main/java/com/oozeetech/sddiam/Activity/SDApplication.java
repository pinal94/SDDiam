package com.oozeetech.sddiam.Activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.oozeetech.sddiam.Model.ResponseModel;

public class SDApplication extends Application {
    public boolean isLoggedIn = false;
    public String webURL;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setLoginSession(Activity activity, ResponseModel responseModel) {
        SharedPreferences pref = activity.getSharedPreferences("sessionPrefLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isLoggedIn", responseModel.getData().getLoggedIn());
        editor.putString("webURL", "");
        editor.clear();
        editor.commit();
        editor.apply();
        getLoginSession(activity);
    }

    public void getLoginSession(Activity activity) {
        isLoggedIn = false;
        webURL = "";
        SharedPreferences pref = activity.getSharedPreferences("sessionPrefLogin", Context.MODE_PRIVATE);
        isLoggedIn = pref.getBoolean("isLoggedIn", false);
        webURL = pref.getString("webURL", "");
    }

    public void clearLoginSession(Activity activity) {
        isLoggedIn = false;
        webURL = "";
        SharedPreferences pref = activity.getSharedPreferences("sessionPrefLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        activity.finish();
    }
}
