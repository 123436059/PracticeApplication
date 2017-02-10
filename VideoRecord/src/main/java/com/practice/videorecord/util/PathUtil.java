package com.practice.videorecord.util;

import android.os.Environment;

import com.utill.tx.txlibrary.Log.L;

import java.io.File;

/**
 * Created by Taxi on 2017/2/10.
 */

public class PathUtil {

    public static String getRootPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    public static String getCachePath() {
        StringBuffer sb = new StringBuffer(getRootPath());
        sb.append("/z_taxi_data/");
        String path = sb.toString();
        File file = new File(path);
        if (file.mkdirs()) {
            L.i("cache path=" + path);
            return path;
        }
        return "";
    }

    public static String getMediaPath() {
        File mediaFile = new File(getCachePath(), "media");
        if (mediaFile.mkdirs()) {
            L.d("media path=" + mediaFile.getAbsolutePath());
            return mediaFile.getAbsolutePath();
        }
        return "";
    }
}
