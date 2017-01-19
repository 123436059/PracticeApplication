package com.tx.practice.entity;

import android.content.Context;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.tx.practice.PlaneWar;
import com.tx.practice.R;

/**
 * Created by Taxi on 2017/1/19.
 */

public class Bullet extends BaseEntity {

    private Hero hero;

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
                            PlaneWar war = (PlaneWar) getParent();
                            if (war != null) {
                                war.removeView(Bullet.this);
                            }
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            bitmap.recycle();
                            PlaneWar war = (PlaneWar) getParent();
                            if (war != null) {
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
}
