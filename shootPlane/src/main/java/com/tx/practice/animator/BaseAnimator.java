package com.tx.practice.animator;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by Taxi on 2017/1/19.
 */

public abstract class BaseAnimator {
    private ValueAnimator animator;
    protected View target;
    public BaseAnimator(View target, float starValue, float endValue) {
        this.target = target;

        /*处理移动*/
        this.animator = ValueAnimator.ofFloat(starValue, endValue);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animateValue = (float) animator.getAnimatedValue();
                doAnim(animateValue);
            }
        });
    }

    public void start(long duration) {
        animator.setDuration(duration);
        animator.start();
    }

    public void setAnimationListener(AnimatorListenerAdapter listener) {
        animator.addListener(listener);
    }

    public void setInterpolator(Interpolator value) {
        animator.setInterpolator(value);
    }

    public void cancel() {
        animator.cancel();
    }

    protected abstract void doAnim(float animateValue);
}
