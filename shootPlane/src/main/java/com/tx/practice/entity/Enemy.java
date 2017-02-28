package com.tx.practice.entity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.RectF;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.tx.practice.PlaneWar;
import com.tx.practice.R;
import com.tx.practice.Utils.Utils;
import com.tx.practice.animator.FlyAnimator;
import com.tx.practice.dialog.GameDialog;

/**
 * Created by Taxi on 2017/1/19.
 */

public class Enemy extends BaseEntity implements FlyAnimator.OnEnemyFlyListener {

    private PlaneWar planeWar;

    private long duration;
    private int targetY;
    private int shotCount;

    protected static final int MAX_SHOT_COUNT =4;
    private FlyAnimator flyAnimator;

    public Enemy(Context context) {
        super(context);
    }

    @Override
    protected int getImageRes() {
        return R.drawable.enemy1;
    }

    public void setPlaneWar(PlaneWar planeWar) {
        this.planeWar = planeWar;
    }

    public PlaneWar getPlaneWar() {
        return planeWar;
    }

    public void fly() {
        flyAnimator = new FlyAnimator(this, getTranslationY(), targetY);
        flyAnimator.setOnEnemyFlyListener(this);
        flyAnimator.setAnimationListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                PlaneWar war = (PlaneWar) getParent();
                if (war != null) {
                    war.removeView(Enemy.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                PlaneWar war = (PlaneWar) getParent();
                if (war != null) {
                    war.removeView(Enemy.this);
                }
            }
        });

        flyAnimator.setInterpolator(new LinearInterpolator());
        flyAnimator.start(duration);
    }

    @Override
    public void onFly(float translationY) {
        dealWithPlane();
        dealWithBullet();
    }

    private void dealWithBullet() {
        for (int i = 0; i < planeWar.getChildCount(); i++) {
            View view = planeWar.getChildAt(i);
            if (view instanceof Bullet) {
                Bullet bullet = (Bullet) view;
                if (bullet.isCanShot()) {
                    if (isShareRect(bullet, this) || isInRect(bullet, this)) {
                        setShotCount(getShotCount() + 1);
                        bullet.setCanShot(false);
                        bullet.setVisibility(View.INVISIBLE);
                        bullet.stopAnimation();
                    }

                    if (getShotCount() >= MAX_SHOT_COUNT) {
                        stopAnimation();
                        planeWar.boomEnemy(getTranslationX(), getTranslationY());
                        planeWar.increaseScore();
                        break;
                    }
                }
            }
        }
    }

    private void dealWithPlane() {
        Hero hero = planeWar.getHero();
        if (hero != null && hero.getVisibility() == View.VISIBLE && getParent() != null) {
            if (isShareRect(this, hero) || isInRect(this, hero)) {
                planeWar.boomEnemy(getTranslationX(), getTranslationY());
                stopAnimation();
                planeWar.boomHero();
                planeWar.removeBullet();

                planeWar.hideHero();
                planeWar.saveScore();
                showDialog();
            }
        }
    }

    private void showDialog() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.showGameDialog(getContext(), new GameDialog.onButtonClickListener() {
                    @Override
                    public void onExitClick() {
                        planeWar.end();
                    }

                    @Override
                    public void onRestartClick() {
                        planeWar.clearAll();
                        planeWar.start();
                    }
                });
            }
        }, 1500);
    }

    private boolean isInRect(View v1, View v2) {
        RectF rect1 = Utils.getTranslationRect(v1);
        RectF rect2 = Utils.getTranslationRect(v2);

        return rect1.left >= rect2.left && rect1.top >= rect2.top && rect1.right <= rect2.right
                && rect1.bottom <= rect2.bottom;
    }

    private boolean isShareRect(View v1, View v2) {
        RectF rect1 = Utils.getTranslationRect(v1);
        RectF rect2 = Utils.getTranslationRect(v2);

        //TODO check booleanValue
        boolean isLeftIn = rect1.left >= rect2.left && rect1.left <= rect2.right;
        boolean isTopIn = rect1.top >= rect2.top && rect1.top <= rect2.bottom;
        boolean isRightIn = rect1.right >= rect2.left && rect1.right <= rect2.right;
        boolean isBottomIn = rect1.bottom >= rect2.top && rect1.bottom <= rect2.bottom;
//        L.d("isLeftIn=" + isLeftIn);
//        L.d("isTopIn=" + isTopIn);
//        L.d("isRightIn=" + isRightIn);
//        L.d("isBottomIn=" + isBottomIn);
//        L.d("isTopIn && isLeftIn=" + (isTopIn && isLeftIn));
//        L.d("isTopIn && isRightIn=" + (isTopIn && isRightIn));
//        L.d("isLeftIn && isTopIn=" + (isLeftIn && isTopIn));
//        L.d("isLeftIn && isBottomIn=" + (isLeftIn && isBottomIn));

        return (isLeftIn && isTopIn) || (isLeftIn && isBottomIn)
                || (isRightIn && isTopIn) || (isRightIn && isBottomIn)
                || (isTopIn && isLeftIn) || (isTopIn && isRightIn)
                || (isBottomIn && isLeftIn) || (isBottomIn && isRightIn);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    /**
     * 对于enemy来说，停止动画就移除自己。这个变量来控制。
     */
    @Override
    public void stopAnimation() {
        if (flyAnimator != null) {
            flyAnimator.cancel();
        }
    }

    public int getShotCount() {
        return shotCount;
    }

    public void setShotCount(int shotCount) {
        this.shotCount = shotCount;
    }
}
