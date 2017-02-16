package com.practice.videorecord;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.videorecord.inter.IVideoRecord;
import com.practice.videorecord.record.CameraManager;
import com.practice.videorecord.record.RecordManager;
import com.practice.videorecord.service.BackGroundService;
import com.practice.videorecord.view.PreView;

import java.util.Locale;

import kr.co.namee.permissiongen.PermissionGen;

public class TestActivity extends AppCompatActivity implements RecordManager.UpdateListener {

    private ImageView ivVideo;
    private TextView tvTime;
    private Intent recordService;
    private RecordManager mRecorderManager;

    private IVideoRecord IVideoControl;


    private static final int UPDATE_TIME = 0x01;
    private static final int UPDATE_PLAY = 0x02;
    private static final int UPDATE_STOP = 0x03;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PLAY:
                    ivVideo.setImageResource(R.drawable.ic_video_white_on);
                    break;

                case UPDATE_STOP:
                    ivVideo.setImageResource(R.drawable.ic_video_white);
                    break;

                case UPDATE_TIME:
                    int sec = msg.arg1;
                    tvTime.setText(getFormatStr(sec));
                    break;
            }
        }
    };


    ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BackGroundService.LocalBinder binder = (BackGroundService.LocalBinder) service;
            IVideoControl = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            IVideoControl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initPermission();

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_content);
        PreView preView = new PreView(this, CameraManager.getCamera(getApplicationContext()));
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        preView.setLayoutParams(layoutParams);
        frameLayout.addView(preView);
        mRecorderManager = RecordManager.getInstance(this);
        mRecorderManager.initSurface(preView.getHolder().getSurface());
        mRecorderManager.setUpdateListener(this);
        ivVideo = (ImageView) findViewById(R.id.btnRecord);
        tvTime = (TextView) findViewById(R.id.tvTime);

        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IVideoControl == null) {
                    return;
                }
                if (IVideoControl.isRecording()) {
                    mRecorderManager.stop();
                } else {
                    mRecorderManager.start();
                }

            }
        });
        recordService = new Intent(this, BackGroundService.class);
        bindService(recordService, mConn, Context.BIND_AUTO_CREATE);
    }

    private void initPermission() {
        PermissionGen.with(this).addRequestCode(100)
                .permissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO).request();
    }

    @Override
    public void updateSeconds(int seconds) {
        mHandler.obtainMessage(UPDATE_TIME, seconds, 0).sendToTarget();
    }

    private String getFormatStr(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format(Locale.CHINESE, "%02d:%02d", min, sec);
    }

    @Override
    public void startRecord() {
        mHandler.sendEmptyMessage(UPDATE_PLAY);
    }

    @Override
    public void stopRecord() {
        mHandler.sendEmptyMessage(UPDATE_STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (IVideoControl != null) {
            IVideoControl.onStop();
        }
        stopService(recordService);
    }
}
