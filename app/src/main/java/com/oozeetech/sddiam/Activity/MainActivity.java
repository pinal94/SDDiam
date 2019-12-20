package com.oozeetech.sddiam.Activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.oozeetech.sddiam.R;

public class MainActivity extends BaseActivity {
    private WebView wvMainView;
    private ProgressBar progressBar;
    private String strURL = "";

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            if (checkInternet()) {
                if (!getIntent().getStringExtra("serviceURL").equals("")) {
                    strURL = getIntent().getStringExtra("serviceURL");
                }

                wvMainView = findViewById(R.id.wvMainView);
                progressBar = findViewById(R.id.progressbar);
                progressBar.setMax(100);
                progressBar.setProgress(1);

                wvMainView.getSettings().setJavaScriptEnabled(true);

                wvMainView.loadUrl(strURL);

                wvMainView.setWebChromeClient(new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {
                        progressBar.setProgress(progress);
                    }
                });

                wvMainView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } else {
                showNoInternetDialog();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
