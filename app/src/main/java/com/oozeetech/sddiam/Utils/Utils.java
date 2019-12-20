package com.oozeetech.sddiam.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
    public Utils() {
    }

    public int getDeviceHeight(Context context) {
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.heightPixels;
        } catch (Exception e) {
            sendExceptionReport(e);
        }

        return 800;
    }

    public void sendExceptionReport(Exception e) {
        e.printStackTrace();

        try {
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    public int getDeviceWidth(Context context) {
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.widthPixels;
        } catch (Exception e) {
            sendExceptionReport(e);
        }

        return 480;
    }

}
