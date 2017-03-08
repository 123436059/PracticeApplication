package com.originalandtest.tx.downloaddemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.originalandtest.tx.downloaddemo.dao.DaoMaster;
import com.originalandtest.tx.downloaddemo.dao.DaoSession;

/**
 * Created by Taxi on 2017/3/8.
 */

public class GreenDaoHelper {

    private static DaoSession daoSession;
    private static SQLiteDatabase db;

    public static void initDataBase(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "download.db", null);
        db = devOpenHelper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static SQLiteDatabase getDb() {
        return db;
    }
}
