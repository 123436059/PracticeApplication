package com.practice.videorecord.view;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.utill.tx.txlibrary.Log.L;

import java.io.IOException;

/**
 * Created by Taxi on 2017/2/10.
 */

public class PreView extends SurfaceView implements SurfaceHolder.Callback {

    private final SurfaceHolder mHolder;
    private Camera mCamera;

    public PreView(Context context, Camera camera) {
        super(context);
        setCamera(camera);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public void setCamera(Camera mCamera) {
        this.mCamera = mCamera;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
            L.d("tried to stop a non-existent preview");
        }

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
            L.d("Error starting camera preview: " + e.getMessage());
        }
    }

    public void changeCamera(Camera camera) {
        mCamera.stopPreview();
        setCamera(camera);
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
