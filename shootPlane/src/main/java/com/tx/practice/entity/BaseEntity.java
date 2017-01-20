package com.tx.practice.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.view.View;

/**
 * Created by MJS on 2017/1/19.
 */

public abstract class BaseEntity extends View {
    Bitmap bitmap;

    public BaseEntity(Context context) {
        super(context);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), getImageRes());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @DrawableRes
    protected abstract int getImageRes();

    public void stopAnimation() {

    }
}
