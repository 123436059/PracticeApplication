package com.practice.videorecord.record;

import android.content.Context;
import android.hardware.Camera;
import android.widget.Toast;

import com.utill.tx.txlibrary.Log.L;

/**
 * Created by Taxi on 2017/2/14.
 */

public class CameraManager {

    private static Camera camera;

    public static Camera getCamera(Context context) {
        camera = null;
        try {
            camera = Camera.open(0);
        } catch (Exception e) {
            L.d("相机打开失败:" + e.getMessage());
            Toast.makeText(context, "相机打开失败，请重试", Toast.LENGTH_SHORT).show();
        }
        return camera;
    }

    public void releaseCamera() {
        if (camera != null) {
            camera.lock();
            camera.release();
            camera = null;
        }
    }

    public void unLockCamera() {
        if (camera != null) {
            camera.unlock();
        }
    }

}
