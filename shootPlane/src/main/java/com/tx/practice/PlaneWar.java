package com.tx.practice;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by admin on 2017/1/18.
 */

public class PlaneWar extends FrameLayout {

    private Paint paint;
    private int textHight = 0;
    private String str = "";

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

        textHight = getTextHeight(str);
    }

    private int getTextHeight(String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);

        return 0;
    }


}
