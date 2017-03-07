package com.originalandtest.tx.downloaddemo.download;

/**
 * Created by Taxi on 2017/3/7.
 */

public class DownloadInfo {
    private int id;
    private String url;
    private int threadId;
    private long block;  //每个块应该下载的大小

    private int downloadProgress;//已经下载的长度

    private int maxLength; //总的长度

    private String fileName;

    private String filePath;

    public long getBlock() {
        return block;
    }

    public void setBlock(long block) {
        this.block = block;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getDownloadProgress() {
        return downloadProgress;
    }

    public void setDownloadProgress(int downloadProgress) {
        this.downloadProgress = downloadProgress;
    }
}
