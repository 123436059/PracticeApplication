package com.tx.practice.animator;

import android.view.View;

/**
 * Created by Taxi on 2017/1/19.
 */

public class FlyAnimator extends BaseAnimator {
    public FlyAnimator(View target, float starValue, float endValue) {
        super(target, starValue, endValue);
    }

    @Override
    protected void doAnim(float animateValue) {
        target.setTranslationY(animateValue);
        if (listener != null) {
            listener.onFly(animateValue);
        }
    }


    private OnEnemyFlyListener listener;

    public void setOnEnemyFlyListener(OnEnemyFlyListener listener) {
        this.listener = listener;
    }

    public interface OnEnemyFlyListener {
        void onFly(float translationY);
    }

}
