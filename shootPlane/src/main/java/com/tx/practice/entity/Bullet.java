package com.tx.practice.entity;

import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.tx.practice.PlaneWar;
import com.tx.practice.R;
import com.utill.tx.txlibrary.Log.L;

/**
 * Created by Taxi on 2017/1/19.
 */

public class Bullet extends BaseEntity {

    private Hero hero;
    private boolean isCanShot =true;

    public Bullet(Context context) {
        super(context);
    }

    @Override
    protected int getImageRes() {
        return R.drawable.bullet;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }

    private ViewPropertyAnimator animator;

    public void shot(float targetY, long duration) {
        if (animator == null) {
            animator = ViewPropertyAnimator.animate(this).translationY(targetY)
                    .setDuration(duration)
                    .setInterpolator(new LinearInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationCancel(Animator animation) {
                            bitmap.recycle();
                            ViewGroup war = (ViewGroup) getParent();
                            if (war != null) {
                                L.d("移除bullet");
                                war.removeView(Bullet.this);
                            }
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            bitmap.recycle();
                            ViewGroup war = (ViewGroup) getParent();
                            if (war != null) {
                                L.d("移除bullet");
                                war.removeView(Bullet.this);
                            }
                        }
                    });
        }
        animator.start();
    }

    @Override
    public void stopAnimation() {
        if (animator != null) {
            animator.cancel();
        }
    }

    public boolean isCanShot() {
        return isCanShot;
    }

    public void setCanShot(boolean canShot) {
        this.isCanShot = canShot;
    }
}
