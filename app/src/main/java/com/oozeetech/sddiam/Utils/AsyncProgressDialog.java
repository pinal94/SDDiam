package com.oozeetech.sddiam.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Looper;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.oozeetech.sddiam.R;


public class AsyncProgressDialog extends Thread {

    private Dialog pd;
    private Activity activity;
    private Utils utils = new Utils();

    public AsyncProgressDialog(Activity activity) {
        this.activity = activity;
        pd = new Dialog(activity);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pd.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        pd.setContentView(
                activity.getLayoutInflater().inflate(R.layout.custom_progress,
                        null),
                new ViewGroup.LayoutParams(utils.getDeviceWidth(activity), utils.getDeviceHeight(activity)));
    }

    public AsyncProgressDialog(Activity activity, String msg) {
        this.activity = activity;
        pd = new ProgressDialog(activity);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pd.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        pd.setContentView(
                activity.getLayoutInflater().inflate(R.layout.custom_progress,
                        null),
                new ViewGroup.LayoutParams(utils.getDeviceWidth(activity), utils.getDeviceHeight(activity)));
    }

    public static AsyncProgressDialog getInstant(Activity context) {
        return new AsyncProgressDialog(context);
    }

    public static AsyncProgressDialog getInstant(Activity context, String msg) {
        return new AsyncProgressDialog(context, msg);
    }

    @Override
    public void run() {
        try {
            Looper.prepare();
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    pd.show();
                }
            });
            Looper.loop();
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    public void show() {
        try {
            this.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void dismiss() {
        try {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void setMessage(final String message) {
        try {
            if (pd != null) {
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // pd.setMessage(message);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShowing() {
        try {
            return pd.isShowing();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
