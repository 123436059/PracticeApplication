package com.tx.practice.Utils;

import android.graphics.RectF;
import android.view.View;

/**
 * Created by Taxi on 2017/1/19.
 */

public class Utils {

    private Utils() {

    }


    public static RectF getTranslationRect(View view) {
        RectF rect = new RectF(view.getTranslationX(), view.getTranslationY()
                , view.getTranslationX() + view.getWidth(), view.getTranslationY() + view.getHeight());

        return rect;
    }
}
