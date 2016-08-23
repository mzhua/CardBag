package com.wonders.xlab.cardbag.base;

/**
 * Created by hua on 16/8/19.
 */
public abstract class MVPFragment extends BaseFragment {

    protected abstract BaseContract.Presenter getPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseContract.Presenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }
    }
}
