package com.wonders.xlab.cardbag.base;

import com.wonders.xlab.cardbag.util.LogUtil;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hua on 16/8/19.
 */
public class BasePresenter implements BaseContract.Presenter {
    private ArrayList<BaseContract.Model> mModels;

    /**
     * if you want the model's life circle sync with presenter, then you must call this method to attach the models with presenter
     * @param models
     */
    protected void attachModels(BaseContract.Model... models) {
        if (mModels == null) {
            mModels = new ArrayList<>();
        }
        Collections.addAll(mModels, models);
    }

    @Override
    public void onDestroy() {
        if (mModels != null) {
            for (BaseContract.Model model : mModels) {
                if (null != model) {
                    model.onDestroy();
                    model = null;
                }
            }
        } else {
            LogUtil.warning("BasePresenter","did you forgot to call attachModels in your presenter?");
        }
    }
}
