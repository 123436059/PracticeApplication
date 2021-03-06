package com.practice.videorecord;

import android.Manifest;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.videorecord.record.RecordManager;
import com.practice.videorecord.util.PathUtil;
import com.practice.videorecord.view.PreView;
import com.utill.tx.txlibrary.Log.L;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends AppCompatActivity {

    private static final int TYPE_VIDEO = 0x01;
    private static final int TYPE_PIC = 0x02;
    private static final int MSG_RECORD_TIME_INTERVAL = 0x03;

    private int record_time_interval = 5 * 1000;

    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    @BindView(R.id.ivPicture)
    ImageView ivPicture;
    @BindView(R.id.ivVideo)
    ImageView ivVideo;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvCapacity)
    TextView tvCapacity;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;

    private boolean isRecording = false;

    private Camera mCamera;
    private PreView mPreView;
    private MediaRecorder mMediaRecorder;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_RECORD_TIME_INTERVAL:
                    if (!hasMessages(MSG_RECORD_TIME_INTERVAL)) {
                        postDelayed(recordInterval, record_time_interval);
                    }
                    break;
            }
        }
    };
    private int timeSeconds = 0;
    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            tvTime.setText(getTimeFormat(timeSeconds++));
            if (isRecording) {
                mHandler.postDelayed(this, 1000);
            }
        }
    };

    private String getTimeFormat(int time) {
        int min = time / 60;
        int sec = time % 60;
        return String.format(Locale.CHINESE, "%02d:%02d", min, sec);
    }

    int tempI = 0;

    private Runnable recordInterval = new Runnable() {
        @Override
        public void run() {
            if (isRecording) {
                Toast.makeText(MainActivity.this, "重新开始录制" + (tempI++), Toast.LENGTH_SHORT).show();
                stopRecord();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!isRecording) {
                                    if (prepareMediaRecorder()) {
                                        startRecord();
                                    }
                                }
                            }
                        });
                    }
                }).start();
                mHandler.postDelayed(this, record_time_interval);
            }
        }
    };

    private void stopRecord() {
        mMediaRecorder.stop();
        timeSeconds = 0;
        //updateUI
        ivVideo.setImageResource(R.drawable.ic_video_white);
        releaseMediaRecorder();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPermission();
        initCamera();
        initSurfaceView();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO
            , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private void initPermission() {
        PermissionGen.with(this).addRequestCode(100)
                .permissions(permissions)
                .request();
    }

    @PermissionSuccess(requestCode = 100)
    public void onPRSuc() {

    }

    @PermissionFail(requestCode = 100)
    public void onPRFail() {
        Toast.makeText(this, "权限请求失败", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void initCamera() {
        int cameraId = getCameraId();
        try {
            mCamera = Camera.open(cameraId);
        } catch (Exception e) {
            //TODO dialog处理
            Toast.makeText(this, "打开相机失败，请稍后重试", Toast.LENGTH_SHORT).show();
            L.d("打开相机失败" + e.getMessage());
        }
    }

    private int getCameraId() {
        return 0;
    }

    private void initSurfaceView() {
        //添加自定义surfaceview
        if (mCamera == null) {
            return;
        }
        mPreView = new PreView(this, mCamera);
        mPreView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT));
        frameContent.addView(mPreView);
    }

    @OnClick({R.id.ivPicture, R.id.ivVideo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivPicture:

                break;
            case R.id.ivVideo:
                if (isRecording) {
                    stopRecord();
                } else {
                    if (prepareMediaRecorder()) {
                        startRecord();
//                        mHandler.postDelayed(recordInterval, record_time_interval);
                    } else {
                        releaseMediaRecorder();
                    }
                }
                break;
        }
    }

    private void startRecord() {
        if (mMediaRecorder == null) {
            return;
        }
        mMediaRecorder.start();
        isRecording = true;
        mHandler.post(updateTimeRunnable);
        ivVideo.setImageResource(R.drawable.ic_video_white_on);
    }

    private boolean prepareMediaRecorder() {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        //TODO 增加模式选择
        mMediaRecorder.setProfile(CamcorderProfile.get(getCameraId(), CamcorderProfile.QUALITY_720P));
        mMediaRecorder.setOutputFile(getMediaPath(TYPE_VIDEO));

        mMediaRecorder.setMaxDuration(record_time_interval);

        mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    //录制完成，开始下一段录制
                    stopRecord();
                    if (prepareMediaRecorder()) {
                        startRecord();
                    }
                }
            }
        });

        mMediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                //TODO deal with error info.

            }
        });

        mMediaRecorder.setPreviewDisplay(mPreView.getHolder().getSurface());
        try {
            mMediaRecorder.prepare();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            releaseMediaRecorder();
            return false;
        }
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            isRecording = false;
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mHandler.removeCallbacks(updateTimeRunnable);
            L.d("release");
        }
    }

    private String getMediaPath(int typeVideo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE);
        String path = "";
        if (typeVideo == TYPE_VIDEO) {
            path = PathUtil.getMediaPath() + File.separator + "VID_" + simpleDateFormat.format(new Date()) + ".mp4";
        } else if (typeVideo == TYPE_PIC) {
            path = PathUtil.getMediaPath() + File.separator + "PIC_" + simpleDateFormat.format(new Date()) + ".jpg";
        }
        return path;
    }
}
