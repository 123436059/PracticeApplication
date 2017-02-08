package com.tx.practice.listener;

import android.view.View;

import com.tx.practice.PlaneWar;
import com.tx.practice.entity.Enemy;

import java.util.Random;

/**
 * Created by Taxi on 2017/1/19.
 */

public class AddEnemyListener extends AddEntityListener<View> {
    public AddEnemyListener(View view) {
        super(view);
    }

    @Override
    protected void onLayoutFinish() {
        Enemy enemy = (Enemy) t;
        PlaneWar war = (PlaneWar) enemy.getParent();
        if (war != null) {
            /*hide enemy*/
            Random random = new Random();
            enemy.setTranslationY(-1 * enemy.getHeight());
            float targetX = random.nextInt((war.getWidth() - enemy.getWidth()));
            enemy.setTranslationX(targetX);
            enemy.setDuration(4000 + new Random().nextInt(2000));
            //fly through all screen
            enemy.setTargetY(war.getHeight() + enemy.getHeight());
            enemy.fly();
        }
    }
}
