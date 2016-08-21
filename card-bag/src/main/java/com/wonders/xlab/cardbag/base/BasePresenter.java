package com.wonders.xlab.cardbag.base;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hua on 16/8/19.
 */
public class BasePresenter implements BaseContract.Presenter {
    private ArrayList<BaseModel> mModels;


    public <M extends BaseModel> void attachModels(M... models) {
        if (mModels == null) {
            mModels = new ArrayList<>();
        }
        Collections.addAll(mModels, models);
    }

    @Override
    public void onDestroy() {
        if (mModels != null) {
            for (BaseModel model : mModels) {
                model.onDestroy();
                model = null;
            }
        }
    }
}
