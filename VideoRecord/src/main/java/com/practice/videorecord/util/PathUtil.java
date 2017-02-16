package com.practice.videorecord.util;

import android.os.Environment;

import com.utill.tx.txlibrary.Log.L;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

        L.d("file.exit=" + file.exists());
        L.d("file.isDirectory=" + file.isDirectory());
        L.d("file.mkdir=" + file.mkdir());
        L.d("file.mkdris=" + file.mkdirs());

        if (file.exists() && file.isDirectory()) {
            return path;
        }
        if (file.mkdirs()) {
            L.i("cache path=" + path);
            return path;
        }
        return "";
    }

    public static String getMediaPath() {
        File mediaFile = new File(getCachePath(), "media");
        if (mediaFile.exists() && mediaFile.isDirectory()) {
            return mediaFile.getAbsolutePath();
        }

        if (mediaFile.mkdirs()) {
            L.d("media path=" + mediaFile.getAbsolutePath());
            return mediaFile.getAbsolutePath();
        }
        return "";
    }

    public static String getVideoPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE);
        String path = getMediaPath() + File.separator + "VID_" + sdf.format(new Date()) + ".mp4";
        L.e("videoPath=" + path);
        return path;
    }
}
