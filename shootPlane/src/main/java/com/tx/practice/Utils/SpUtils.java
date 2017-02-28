package com.tx.practice.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2017/2/11.
 */

public class SpUtils {

    private static final String fileName = "score";

    public static void saveIntValue(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value).apply();
    }


    public static void saveStrValue(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static int readIntValue(Context context, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static String readStrValue(Context context, String key, String defaultStr) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getString(key, defaultStr);
    }

    public static void clearAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }
}
