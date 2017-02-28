package com.tx.practice.listener;

import android.view.View;
import android.view.ViewGroup;

import com.tx.practice.entity.Hero;
import com.utill.tx.txlibrary.Log.L;

/**
 * Created by Taxi on 2017/1/19.
 */

public class AddHeroListener extends AddEntityListener<Hero> {
    public AddHeroListener(Hero t) {
        super(t);
    }

    @Override
    protected void onLayoutFinish() {
        Hero hero = t;
        //将hero设置到底部中心
        L.d("设置中心");
        int parentWidth = ((ViewGroup) hero.getParent()).getWidth();
        int parentHeight = ((ViewGroup) hero.getParent()).getHeight();
        L.d("设置hero center");
        float translationX = (parentWidth - hero.getWidth()) / 2;
        hero.setTranslationX(translationX);
        hero.setTranslationY(parentHeight - hero.getHeight());
        hero.setVisibility(View.VISIBLE);
    }
}
