package com.tx.practice.entity;

import android.content.Context;

import com.tx.practice.R;

/**
 * Created by Taxi on 2017/1/19.
 */

public class Hero extends BaseEntity {
    public Hero(Context context) {
        super(context);
    }

    @Override
    protected int getImageRes() {
        return R.drawable.hero1;
    }
}
