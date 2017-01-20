package com.tx.practice.Utils;

import android.content.Context;
import android.graphics.RectF;
import android.view.View;

import com.tx.practice.dialog.GameDialog;

/**
 * Created by Taxi on 2017/1/19.
 */

public class Utils {

    private static GameDialog gameDialog;
    private Utils() {

    }

    public static RectF getTranslationRect(View view) {
        RectF rect = new RectF(view.getTranslationX(), view.getTranslationY()
                , view.getTranslationX() + view.getWidth(), view.getTranslationY() + view.getHeight());
        return rect;
    }

    public static void showGameDialog(Context context, GameDialog.onButtonClickListener listener) {
        gameDialog = new GameDialog(context);
        gameDialog.setOnButtonClickListener(listener);
        gameDialog.show();
    }
}
