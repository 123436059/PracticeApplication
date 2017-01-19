package com.tx.practice;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tx.practice.entity.Bullet;
import com.tx.practice.entity.Enemy;
import com.tx.practice.entity.Hero;
import com.tx.practice.listener.AddBoomListener;
import com.tx.practice.listener.AddBulletListener;
import com.tx.practice.listener.AddEnemyListener;
import com.tx.practice.listener.AddHeroListener;

/**
 * Created by admin on 2017/1/18.
 */

public class PlaneWar extends FrameLayout {

    private Paint paint;
    private int textHeight = 0;
    private String str = "";
    private Hero mHero;

    public PlaneWar(Context context) {
        this(context, null);
    }

    public PlaneWar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlaneWar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setTextSize(16);
        paint.setColor(Color.WHITE);
        textHeight = getTextHeight(str);
    }

    private int getTextHeight(String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }

    public void start() {
        generateEnemy();

        generateHero();

        generateBullet();
    }

    private void generateBullet() {
        Bullet bullet = new Bullet(getContext());
        bullet.setHero(mHero);
        bullet.getViewTreeObserver().addOnGlobalLayoutListener(new AddBulletListener(bullet));
        addView(bullet);
        handler.sendEmptyMessageDelayed(MSG_BULLET, CREATE_BULLET_DURATION);
    }

    private void generateHero() {
        mHero = new Hero(getContext());
        mHero.setVisibility(View.INVISIBLE);

        /*这个监听用于获取view的宽高，用于初始化view的位置，
          由于在onCreate里得不到控件的宽高,所以设置这样的一个监听。
          在布局完成时回调*/
        mHero.getViewTreeObserver().addOnGlobalLayoutListener(new AddHeroListener(mHero));
        mHero.setVisibility(View.VISIBLE);
        addView(mHero);

    }

    private void generateEnemy() {
        Enemy enemy = new Enemy(getContext());
        enemy.setPlaneWar(this);
        enemy.getViewTreeObserver().addOnGlobalLayoutListener(new AddEnemyListener(enemy));
        addView(enemy);
        handler.sendEmptyMessageDelayed(MSG_ENEMY, CREATE_ENEMY_DURATION);
    }


    private float downX, downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mHero.getVisibility() != View.VISIBLE) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();

                //会修正到中心点。
                moveHeroTo(downX, downY);
                break;

            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();

                float deltaX = moveX - downX;
                float deltaY = moveY - downY;

                //down的时候已经偏移了。这里就直接使用，不要再修正了。
                moveHeroBy(deltaX, deltaY);
//                moveHeroTo(moveX, moveY);
                downX = moveX;
                downY = moveY;
                break;

            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    private void moveHeroTo(float x, float y) {
        float xOffset = x - mHero.getTranslationX() - mHero.getWidth() / 2;
        float yOffset = y - mHero.getTranslationY() - mHero.getHeight() / 2;
        moveHeroBy(xOffset, yOffset);
    }

    private void moveHeroBy(float xOffset, float yOffset) {
        float newTranslationX = mHero.getTranslationX() + xOffset;
        float newTranslationY = mHero.getTranslationY() + yOffset;

        //以触点为中心点，为了使画面处于中心点，所以讲view向左和向上移动。
//        float newTranslationX = downX - mHero.getWidth() / 2;
//        float newTranslationY = downY - mHero.getHeight() / 2;
        //控制四边坐标
        if (newTranslationX < 0) {
            newTranslationX = 0;
        }
        if (newTranslationX > (getWidth() - mHero.getWidth())) {
            newTranslationX = getWidth() - mHero.getWidth();
        }
        if (newTranslationY < 0)
            newTranslationY = 0;
        if (newTranslationY > (getHeight() - mHero.getHeight())) {
            newTranslationY = getHeight() - mHero.getHeight();
        }
        mHero.setTranslationX(newTranslationX);
        mHero.setTranslationY(newTranslationY);
    }

    public Hero getHero() {
        return mHero;
    }

    public void boomEnemy(float translationX, float translationY) {
        View view = new View(getContext());
        Drawable drawable = getResources().getDrawable(R.drawable.enemy_boom);
        view.setBackgroundDrawable(drawable);
        view.setLayoutParams(new ViewGroup.LayoutParams(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()));
        view.setTranslationX(translationX);
        view.setTranslationY(translationY);

        /*控制动画效果，一次，加入时触发*/
        view.getViewTreeObserver().addOnGlobalLayoutListener(new AddBoomListener(view));
        addView(view);
    }

    public void boomHero() {
        View v = new View(getContext());
        Drawable drawable = getResources().getDrawable(R.drawable.hero_boom);
        v.setBackgroundDrawable(drawable);
        v.setLayoutParams(new ViewGroup.LayoutParams(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()));
        v.setTranslationX(mHero.getTranslationX());
        v.setTranslationY(mHero.getTranslationY());

        v.getViewTreeObserver().addOnGlobalLayoutListener(new AddBoomListener(v));
        addView(v);
    }

    public void removeBullet() {
        handler.removeMessages(MSG_BULLET);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view instanceof Bullet) {
                Bullet bullet = (Bullet) view;
                bullet.stopAnimation();
                removeView(bullet);
            }
        }
    }

    private static final int CREATE_ENEMY_DURATION = 1000;
    private static final int CREATE_BULLET_DURATION = 1000;
    private static final int MSG_ENEMY = 1;
    private static final int MSG_BULLET = 2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ENEMY:
                    generateEnemy();
                    break;

                case MSG_BULLET:

                    break;


            }
        }
    };

    public void hideHero() {
        mHero.setVisibility(View.INVISIBLE);
    }

    public void saveScore() {
        //TODO save score

    }
}
