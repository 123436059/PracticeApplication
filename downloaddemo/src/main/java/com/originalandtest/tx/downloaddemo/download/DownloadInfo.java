package com.originalandtest.tx.downloaddemo.download;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Taxi on 2017/3/7.
 */

@Entity
public class DownloadInfo {
    @Id(autoincrement = true)
    private Long id;
    private String url;
    private int threadId;
    private Long block;  //每个块应该下载的大小

    private Long downloadProgress;//已经下载的长度

    private Long maxLength; //总的长度

    private String fileName;

    private String filePath;

    @Generated(hash = 931500993)
    public DownloadInfo(Long id, String url, int threadId, long block,
                        long downloadProgress, long maxLength, String fileName, String filePath) {
        this.id = id;
        this.url = url;
        this.threadId = threadId;
        this.block = block;
        this.downloadProgress = downloadProgress;
        this.maxLength = maxLength;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    @Generated(hash = 327086747)
    public DownloadInfo() {
    }

    public long getBlock() {
        return block;
    }

    public void setBlock(long block) {
        this.block = block;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(long maxLength) {
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

    public long getDownloadProgress() {
        return downloadProgress;
    }

    public void setDownloadProgress(long downloadProgress) {
        this.downloadProgress = downloadProgress;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
