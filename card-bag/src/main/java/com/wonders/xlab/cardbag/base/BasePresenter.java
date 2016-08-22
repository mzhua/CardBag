package com.wonders.xlab.cardbag.base;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hua on 16/8/19.
 */
public class BasePresenter implements BaseContract.Presenter {
    private ArrayList<BaseContract.Model> mModels;


    public void attachModels(BaseContract.Model... models) {
        if (mModels == null) {
            mModels = new ArrayList<>();
        }
        Collections.addAll(mModels, models);
    }

    @Override
    public void onDestroy() {
        if (mModels != null) {
            for (BaseContract.Model model : mModels) {
                model.onDestroy();
                model = null;
            }
        }
    }
}
