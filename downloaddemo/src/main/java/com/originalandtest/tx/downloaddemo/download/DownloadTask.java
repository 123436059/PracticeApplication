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

    private int startPoint, endPoint, index;
    private final DownloadInfoEngine downEngine;
    private final DownloadInfo son;

    public DownloadTask(DownloadInfo father, int index, int block, DownloadListener listener) {
        startPoint = index * block;
        endPoint = (index + 1) * block - 1;
        this.index = index;
        this.listener = listener;
        downEngine = DownloadInfoEngine.getInstance();
        son = downEngine.getDownloadInfo(father);
        downEngine.insertIfNotExit(son);
    }

    public void start() {
        //TODO 改成线程池模式
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {

            long progress = downEngine.getProgressByThread(son);
            startPoint+=progress;
            URL u = new URL(son.getUrl());
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("Range", "bytes=" + startPoint + "-" + endPoint);
            //只能控制单个任务。
            InputStream inputStream = conn.getInputStream();

            RandomAccessFile raf = new RandomAccessFile(son.getFilePath(), "rwd");
            raf.seek(startPoint);
            int len = 0;
            long sum = progress;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                raf.write(buffer, 0, len);
                listener.onProgress(len);
                sum+=len;
                if (DownloadManager.isPause) {
                    //保存数据
                    son.setDownloadProgress(sum);
                    downEngine.update(son);
                    break;
                }
            }
        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }
}
