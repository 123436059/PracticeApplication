package com.tx.practice.listener;

import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Taxi on 2017/1/19.
 */

public abstract class AddEntityListener<T extends View> implements ViewTreeObserver.OnGlobalLayoutListener {
    T t;

    public AddEntityListener(T t) {
        this.t = t;
    }

    @Override
    public void onGlobalLayout() {
        //TODO 之前这里搞错了，没有移除，导致一直重复添加监听，导致内存飙升，导致OOM。
//        t.getViewTreeObserver().addOnGlobalLayoutListener(this);
        t.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        onLayoutFinish();
    }

    protected abstract void onLayoutFinish();
}
