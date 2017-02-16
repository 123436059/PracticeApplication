package com.practice.videorecord.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.practice.videorecord.MainActivity;
import com.practice.videorecord.R;
import com.practice.videorecord.TestActivity;
import com.practice.videorecord.inter.IVideoRecord;
import com.practice.videorecord.record.RecordManager;

public class BackGroundService extends Service implements IVideoRecord {

    public static final String ACTION_SHOW_HOME = "com.practice.videoRecord.ACTION_SHOW_HOME";
    public static final String ACTION_SHOW_RECORDER = "com.practice.videoRecord.ACTION_SHOW_RECORDER";

    private WindowManager mWindowManager;
    private View mRecordView;
    private SurfaceView mSurfaceView;
    private int screenWidth;
    private int screenHeight;
    private LocalBroadcastManager mLocalBroadCastManager;
    private WindowManager.LayoutParams mLayoutParams;
    private LocalReceiver mReceiver;
    private RecordManager mRecordManager;

    public BackGroundService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRecordManager = RecordManager.getInstance(this);
        Notification notification = new Notification.Builder(this).setContentTitle("后台录制service")
                .setContentText("点击进入app").setSmallIcon(R.drawable.icon_flash)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, TestActivity.class), 0))
                .build();

        startForeground(1234, notification);

//        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        mRecordView = LayoutInflater.from(this).inflate(R.layout.layout_record, null);
//        mSurfaceView = (SurfaceView) mRecordView.findViewById(R.id.surfaceView);
//
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        screenWidth = displayMetrics.widthPixels;
//        screenHeight = displayMetrics.heightPixels;
//
//        mLayoutParams = new WindowManager.LayoutParams();
//        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
//        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        mLayoutParams.width = screenWidth;
//        mLayoutParams.height = screenHeight;
//
//        mWindowManager.addView(mRecordView, mLayoutParams);

//        registerLocalReceiver();
    }

    private void registerLocalReceiver() {
        mLocalBroadCastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SHOW_HOME);
        intentFilter.addAction(ACTION_SHOW_RECORDER);
        mReceiver = new LocalReceiver();
        mLocalBroadCastManager.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWindowManager != null) {
            mWindowManager.removeView(mRecordView);
        }

        if (mRecordManager != null) {
            mRecordManager.stop();
        }
        mLocalBroadCastManager.unregisterReceiver(mReceiver);
        stopForeground(true);
    }

    @Override
    public void onPlay() {
        //得到Record，并进行处理
        if (mRecordManager != null) {
            mRecordManager.start();
        }
    }

    @Override
    public void onStop() {
        if (mRecordManager != null) {
            mRecordManager.stop();
        }
    }

    @Override
    public boolean isRecording() {
        return mRecordManager != null && mRecordManager.getIsRecoding();
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_SHOW_HOME.equals(action)) {
                hideRecord(true);
            } else if (ACTION_SHOW_RECORDER.equals(action)) {
                hideRecord(false);
            }
        }
    }

    private void hideRecord(boolean hide) {
        if (hide) {
            mLayoutParams.width = 1;
            mLayoutParams.height = 1;
        } else {
            mLayoutParams.width = screenWidth;
            mLayoutParams.height = screenHeight;
        }
        mWindowManager.updateViewLayout(mRecordView, mLayoutParams);
    }


    public class LocalBinder extends Binder {
        public BackGroundService getService() {
            return BackGroundService.this;
        }
    }

    //得到playcontrol
}

