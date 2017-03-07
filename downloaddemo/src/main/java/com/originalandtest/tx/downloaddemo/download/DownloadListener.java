package com.originalandtest.tx.downloaddemo.download;

/**
 * Created by Taxi on 2017/3/7.
 */

public interface DownloadListener {

    void onFinish();

    void onProgress(int progress);

    void onStart();

    void onError(String msg);

}
