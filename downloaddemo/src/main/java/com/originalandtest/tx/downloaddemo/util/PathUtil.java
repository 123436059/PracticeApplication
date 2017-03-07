package com.originalandtest.tx.downloaddemo.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Taxi on 2017/3/7.
 */

public class PathUtil {

    private static final String MY_PATH = "z_taxi_data";
    private static final String DOWNLOAD = "download";

    public static String getRootPath() {
        String path = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return path;

    }

    public static String getDownloadPath() {
        StringBuffer sb = new StringBuffer(getRootPath());
        sb.append(File.separator).append(MY_PATH).append(File.separator)
                .append(DOWNLOAD).append(File.separator);

        String folder = sb.toString();

        if (!new File(folder).exists()) {
            new File(folder).mkdirs();
        }
        return folder;
    }

}
