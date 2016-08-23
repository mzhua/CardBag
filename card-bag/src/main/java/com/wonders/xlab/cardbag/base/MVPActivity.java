package com.wonders.xlab.cardbag.base;

/**
 * Created by hua on 16/8/19.
 */
public abstract class MVPActivity extends BaseActivity {

    protected abstract BaseContract.Presenter getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseContract.Presenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }
    }
}
