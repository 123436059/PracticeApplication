package com.practice.videorecord.record;

import android.content.Context;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Handler;
import android.view.Surface;
import android.widget.Toast;

import com.practice.videorecord.util.PathUtil;

import java.io.IOException;

/**
 * Created by Taxi on 2017/2/14.
 */

public class RecordManager {
    private static Context mContext;
    private Surface surface;
    private MediaRecorder mMediaRecorder;
    private Camera camera;
    private boolean isRecording = false;
    private final Handler mHandler;
    //计时任务放在这里进行。


    public interface UpdateListener {
        void updateSeconds(int seconds);

        void startRecord();

        void stopRecord();
    }

    private UpdateListener updateListener;

    public void setUpdateListener(UpdateListener listener) {
        this.updateListener = listener;
    }

    private static RecordManager instance;

    public static RecordManager getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new RecordManager();
        }
        return instance;
    }

    private RecordManager() {
        mHandler = new Handler();
    }

    public void initSurface(Surface surface) {
        this.surface = surface;
    }


    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if (updateListener != null) {
                updateListener.updateSeconds(currentSeconds++);
            }
            if (isRecording) {
                mHandler.postDelayed(this, 1000);
            }
        }
    };

    private int currentSeconds = 0;

    public void start() {
        if (!isRecording) {
            if (prepareRecord()) {
                mMediaRecorder.start();
                isRecording = true;
                mHandler.removeCallbacks(updateTimeRunnable);
                mHandler.post(updateTimeRunnable);
                if (updateListener != null) {
                    updateListener.startRecord();
                }
            } else {
                releaseRecorder();
            }
        }
    }

    public void stop() {
        if (mMediaRecorder != null) {
            if (isRecording) {
                mMediaRecorder.stop();
                releaseRecorder();
            }
        }
    }

    private boolean prepareRecord() {
        if (surface == null) {
            Toast.makeText(mContext, "请先初始化Surface", Toast.LENGTH_SHORT).show();
            return false;
        }
        mMediaRecorder = new MediaRecorder();
        camera = CameraManager.getCamera(mContext);
        camera.unlock();
        mMediaRecorder.setCamera(camera); //Camera进行统一管理好了
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        mMediaRecorder.setProfile(CamcorderProfile.get(0, CamcorderProfile.QUALITY_480P));
        mMediaRecorder.setOutputFile(PathUtil.getVideoPath());
        mMediaRecorder.setPreviewDisplay(surface);

        try {
            mMediaRecorder.prepare();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            releaseRecorder();
            return false;
        }
    }

    private void releaseRecorder() {
        isRecording = false;
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            camera.lock();
            camera = null;
            currentSeconds = 0;
            if (updateListener != null) {
                updateListener.stopRecord();
            }
            mHandler.removeCallbacks(updateTimeRunnable);
        }
    }

    public boolean getIsRecoding() {
        return isRecording;
    }
}
