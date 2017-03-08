package com.originalandtest.tx.downloaddemo;

import android.app.Application;

import com.originalandtest.tx.downloaddemo.db.GreenDaoHelper;

/**
 * Created by Taxi on 2017/3/8.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GreenDaoHelper.initDataBase(this);
    }
}
