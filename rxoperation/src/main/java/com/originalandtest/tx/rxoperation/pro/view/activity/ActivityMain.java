package com.originalandtest.tx.rxoperation.pro.view.activity;

import android.os.Bundle;

import com.originalandtest.tx.rxoperation.R;
import com.originalandtest.tx.rxoperation.fragmentutils.base.BaseActivity;

public class ActivityMain extends BaseActivity {

    @Override
    protected int setLeftCornerLogo() {
        return R.layout.activity_main2;
    }

    @Override
    protected void doBusiness(Bundle savedInstanceState) {

    }

    @Override
    protected boolean isHideActionBar() {
        return false;
    }

    @Override
    protected boolean isShowBackArrow() {
        return false;
    }

    @Override
    protected String setToolBarTitle() {
        return null;
    }


    @Override
    protected boolean isSetTransparentBar() {
        return false;
    }

    @Override
    protected int bindLayout() {
        return 0;
    }
}
