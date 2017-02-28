package com.originalandtest.tx.rxoperation.fragmentutils.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.originalandtest.tx.rxoperation.R;

/**
 * Created by Taxi on 2017/2/16.
 */

public abstract class BaseActivity extends SupportActivity {

    private Toolbar toolbar;

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
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        if (toolbar != null) {
            toolbar.setTitle(setToolBarTitle());
        }

        //透明状态栏


        //状态栏间隔高度

        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            if (isShowBackArrow()) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            if (isHideActionBar()) {
                actionBar.hide();
            }
            /*在使用v7包时显示icon和标题需要制定一下属性*/
            actionBar.setDisplayHomeAsUpEnabled(true);

            /*设置actionbar logo*/
            actionBar.setLogo(setLeftCornerLogo());

            /*是否显示actionBarlog*/
            actionBar.setDisplayUseLogoEnabled(true);

            /*左侧图标点击事件使能*/
            actionBar.setHomeButtonEnabled(true);
        }
    }

    /**
     * 设置左上角logo
     *
     * @return
     */
    protected abstract int setLeftCornerLogo();

    /**
     * 隐藏actionBar
     *
     * @return
     */
    protected abstract boolean isHideActionBar();


    /**
     * 是否显示返回箭头
     *
     * @return
     */
    protected abstract boolean isShowBackArrow();


    /**
     * 设置标题
     *
     * @return
     */
    protected abstract String setToolBarTitle();


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
