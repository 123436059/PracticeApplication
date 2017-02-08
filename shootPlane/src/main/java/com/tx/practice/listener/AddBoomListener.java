package com.tx.practice.listener;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Taxi on 2017/1/19.
 */

public class AddBoomListener extends AddEntityListener<View> {
    public AddBoomListener(View view) {
        super(view);
    }

    @Override
    protected void onLayoutFinish() {
        Drawable background = t.getBackground();
        if (background instanceof AnimationDrawable) {
            AnimationDrawable bg = (AnimationDrawable) background;
            bg.start();

            /*显示一次，移除自身*/
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ViewGroup parent = (ViewGroup) t.getParent();
                    if (parent != null) {
                        parent.removeView(t);
                    }
                }
            }, 601);
        }
    }
}
