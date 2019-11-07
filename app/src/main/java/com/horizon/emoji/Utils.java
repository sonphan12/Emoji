package com.horizon.emoji;

import android.content.res.Resources;

public class Utils {
    private static String TAG = Utils.class.getSimpleName();

    //
    private static float density = 1;


    //First Init
    static {
        density = Resources.getSystem().getDisplayMetrics().density;
    }

    public static float getDensity() { return density; }


    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }
}
