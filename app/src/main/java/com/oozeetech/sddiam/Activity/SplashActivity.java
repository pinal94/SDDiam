package com.oozeetech.sddiam.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.oozeetech.sddiam.R;

public class SplashActivity extends BaseActivity {
    private TextView splashMsg;

    private Handler handler = new Handler();
    Runnable startApp = new Runnable() {
        @SuppressLint("NewApi")
        @Override
        public void run() {
            handler.removeCallbacks(startApp);
            if (sdApplication.isLoggedIn) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("serviceURL", sdApplication.webURL);
                startActivity(intent);
                finishAffinity();
            } else {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                finishAffinity();
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashMsg = findViewById(R.id.splashMsg);
        splashMsg.setVisibility(View.INVISIBLE);

        if (checkInternet()) {
            startApplication(1000);
        } else {
            showNoInternetDialog();
        }
    }

    private void startApplication(long sleepTime) {
        handler.postDelayed(startApp, sleepTime);
    }
}
