package com.originalandtest.tx.downloaddemo.download;

import android.content.Context;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Taxi on 2017/3/7.
 */

public class DownloadTask implements Runnable {

    private final DownloadListener listener;
    private DownloadInfo bean;

    private int startPoint, endPoint, index;

    public DownloadTask(DownloadInfo info, int index, int block, DownloadListener listener) {
        bean = info;
        startPoint = index * block;
        endPoint = (index + 1) * block - 1;
        this.index = index;
        this.listener = listener;
    }

    public void start() {
        //TODO 改成线程池模式
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            URL u = new URL(bean.getUrl());
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("Range", "bytes=" + startPoint + "-" + endPoint);
            //只能控制单个任务。
            InputStream inputStream = conn.getInputStream();

            RandomAccessFile raf = new RandomAccessFile(bean.getFilePath(), "rwd");
            raf.seek(startPoint);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                raf.write(buffer, 0, len);
                listener.onProgress(len);
                if (DownloadManager.isPause) {
                    //保存数据


                    break;
                }
            }
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }
}
