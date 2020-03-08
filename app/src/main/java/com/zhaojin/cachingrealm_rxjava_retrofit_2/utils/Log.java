package com.zhaojin.cachingrealm_rxjava_retrofit_2.utils;

public class Log {
    private final static boolean DEBUG = true;
    private final static String TAG = "TAGG";

    public static void d(Object str) {
        if (DEBUG)
            android.util.Log.d(TAG, str != null ? str.toString() : null + "");
    }

    public static void e(Object str) {
        if (DEBUG) android.util.Log.e(TAG, str != null ? str.toString() : null + "");
    }

    public static void i(Object str) {
        if (DEBUG) android.util.Log.i(TAG, str != null ? str.toString() : null + "");
    }

    public static void w(Object str) {
        if (DEBUG) android.util.Log.w(TAG, str != null ? str.toString() : null + "");
    }
}
