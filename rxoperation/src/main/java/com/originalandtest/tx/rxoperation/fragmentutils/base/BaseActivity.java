package com.originalandtest.tx.rxoperation.fragmentutils.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.SupportActivity;
import android.view.WindowManager;

/**
 * Created by Taxi on 2017/2/16.
 */

public abstract class BaseActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());

        if (isSetTransparentBar()) {
            if (Build.VERSION.SDK_INT >= 19) {
                /*透明状态栏*/
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                /*透明导航栏*/
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        }
        /*处理业务逻辑*/
        doBusiness(savedInstanceState);



    }


    /**
     * 处理业务逻辑
     *
     * @param savedInstanceState
     */
    protected abstract void doBusiness(Bundle savedInstanceState);


    /**
     * 是否设置透明状态栏
     *
     * @return
     */
    protected abstract boolean isSetTransparentBar();

    /**
     * 绑定布局
     *
     * @return
     */
    protected abstract int bindLayout();
}
