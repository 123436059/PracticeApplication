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
        t.getViewTreeObserver().addOnGlobalLayoutListener(this);
        onLayoutFinish();
    }

    protected abstract void onLayoutFinish();
}
