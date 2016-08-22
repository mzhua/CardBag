package com.wonders.xlab.cardbag.base;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hua on 16/8/19.
 */
public abstract class MVPActivity extends BaseActivity {

    protected abstract BaseContract.Presenter getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().onDestroy();
        }
    }
}
