package com.wonders.xlab.cardbag.base;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hua on 16/8/19.
 */
public class MVP1Fragment extends BaseFragment {
    private ArrayList<BasePresenter> mPresenters;

    protected <P extends BasePresenter> void attachPresenter(P... presenters) {
        if (null == mPresenters) {
            mPresenters = new ArrayList<>();
        }
        Collections.addAll(mPresenters, presenters);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenters != null) {
            for (BasePresenter presenter : mPresenters) {
                presenter.onDestroy();
                presenter = null;
            }
        }
    }
}
