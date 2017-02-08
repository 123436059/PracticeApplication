package com.tx.practice.listener;

import android.view.View;

import com.tx.practice.entity.Bullet;
import com.tx.practice.entity.Hero;

/**
 * Created by Taxi on 2017/1/19.
 */

public class AddBulletListener extends AddEntityListener<View> {


    public AddBulletListener(View view) {
        super(view);
    }

    @Override
    protected void onLayoutFinish() {
        Bullet bullet = (Bullet) t;
        Hero hero = bullet.getHero();
        //TODO check getWidth()
        bullet.setTranslationX(hero.getTranslationX() + hero.getWidth() / 2);
        bullet.setTranslationY(hero.getTranslationY() - bullet.getHeight());

        float targetY = -bullet.getHeight();
        float distance = targetY - bullet.getTranslationY();
        long duration = (long) (Math.abs(distance) * 3);
        bullet.shot(targetY, duration);

    }
}
