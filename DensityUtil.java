package com.example.lzm.photo;

import android.content.Context;

/**
 * dp与px转换
 */
public class DensityUtil {
    // 从dp转换成px
    public static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);    // dp * 像素密度 = 像素
    }

    // 从px转换成dp
    public static int px2pd(Context context, float pxValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }
}
