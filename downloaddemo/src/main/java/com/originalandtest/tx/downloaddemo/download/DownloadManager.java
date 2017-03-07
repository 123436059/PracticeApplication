package com.originalandtest.tx.downloaddemo.download;

import com.originalandtest.tx.downloaddemo.util.PathUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Taxi on 2017/3/7.
 */

public class DownloadManager {

    public static final boolean isPause = false;

    private static final int THREAD_SIZE = 3;

    private static DownloadManager mInstance;
    private String mTargetFolder;

    public static DownloadManager getInstance() {
        if (null == mInstance) {
            synchronized (DownloadManager.class) {
                if (null == mInstance) {
                    mInstance = new DownloadManager();
                }
            }
        }
        return mInstance;
    }

    private DownloadManager() {
        //得到UIHandler


        //初始化下载目录
        mTargetFolder = PathUtil.getDownloadPath();
    }


    /*直接传handler的话，就可以少实现一个listener*/
    public void download(DownloadInfo info, DownloadListener listener) {
        //得到当前最大的长度
        if (info == null) {
            listener.onError("下载信息出错");
            return;
        }
        String url = info.getUrl();

        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            int responseCode = conn.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                int maxLength = conn.getContentLength();
                //做数据库的操作

                int downloadProgress = 0;
                info.setDownloadProgress(downloadProgress);
                info.setMaxLength(maxLength);

                RandomAccessFile raf = new RandomAccessFile(info.getFilePath(), "rwd");
                raf.setLength(maxLength);
                raf.close();

                int block = maxLength % THREAD_SIZE == 0 ? maxLength / THREAD_SIZE : maxLength / THREAD_SIZE + 1;

                /*感觉下面的才对*/
//                int block = maxLength % THREAD_SIZE == 0 ? maxLength / THREAD_SIZE : maxLength / THREAD_SIZE + THREAD_SIZE;
                //block差异性
                for (int i = 0; i < THREAD_SIZE; i++) {
                    //传一个数据库的操作
                    DownloadTask task = new DownloadTask(info, i, block, listener);
                    task.start();
                }
            }
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }
}
