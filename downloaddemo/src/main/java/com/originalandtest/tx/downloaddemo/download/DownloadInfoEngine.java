package com.originalandtest.tx.downloaddemo.download;

import com.originalandtest.tx.downloaddemo.dao.DownloadInfoDao;
import com.originalandtest.tx.downloaddemo.db.GreenDaoHelper;

import java.util.List;

/**
 * Created by Taxi on 2017/3/8.
 */

public class DownloadInfoEngine {
    private static DownloadInfoEngine instance;
    private final DownloadInfoDao mDao;

    public static DownloadInfoEngine getInstance() {
        if (instance == null) {
            instance = new DownloadInfoEngine();
        }
        return instance;
    }

    private DownloadInfoEngine() {
        mDao = GreenDaoHelper.getDaoSession().getDownloadInfoDao();
    }

    public boolean isExit(String url) {
        List<DownloadInfo> list = mDao.queryBuilder().where(DownloadInfoDao.Properties.Url.eq(url)).list();
        if (list == null || list.size() == 0) {
            return false;
        }
        return true;
    }

    public int getDownloadProgress(DownloadInfo info) {
        int res = 0;
        String url = info.getUrl();
        List<DownloadInfo> list = mDao.queryBuilder().where(DownloadInfoDao.Properties.Url.eq(url)).list();
        if (list == null) {
            return 0;
        }
        for (DownloadInfo item : list) {
            res += item.getDownloadProgress();
        }
        return res;
    }

    public void insertIfNotExit(DownloadInfo info) {
        //先查询，没有则创建
        DownloadInfo bean = mDao.queryBuilder().where(DownloadInfoDao.Properties.Url.eq(info.getUrl())
                , DownloadInfoDao.Properties.ThreadId.eq(info.getThreadId())).unique();
        if (bean == null) {
            mDao.insert(info);
        }
    }


    public DownloadInfo getDownloadInfo(DownloadInfo father,int threadId) {
        DownloadInfo info = mDao.queryBuilder().where(DownloadInfoDao.Properties.Url.eq(father.getUrl()), DownloadInfoDao.Properties.ThreadId.eq(father.getThreadId()))
                .unique();
        if (info == null) {
            info = new DownloadInfo();
            info.setUrl(father.getUrl());
//            info.setFilePath(filePath);
            info.setThreadId(threadId);

        }
        return info;
    }


    public long getProgressByThread(DownloadInfo son) {
        DownloadInfo unique = mDao.queryBuilder().where(DownloadInfoDao.Properties.Url.eq(son.getUrl()), DownloadInfoDao.Properties.ThreadId.eq(son.getThreadId()))
                .unique();
        return unique.getDownloadProgress();
    }

    public void update(DownloadInfo son) {
        mDao.update(son);
    }
}
