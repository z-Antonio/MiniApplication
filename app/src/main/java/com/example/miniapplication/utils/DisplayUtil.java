package com.example.miniapplication.utils;

import android.util.DisplayMetrics;

public class DisplayUtil {

    private static final Boolean TRANSFORM_PT = false;

    private static float value = 0f;

    public static void transformDisplay(DisplayMetrics dm) {
        if (TRANSFORM_PT) {
            if (value == 0f) {
                value = (float) dm.widthPixels * 72f / 750f;
            }
            if (dm.xdpi != value) {
                dm.xdpi = value;
            }
        }
    }
}
