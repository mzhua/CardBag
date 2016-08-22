package com.wonders.xlab.cardbag.data;

import com.wonders.xlab.cardbag.base.BaseModel;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.ui.mycard.MyCardContract;

/**
 * Created by hua on 16/8/21.
 */

public class MyCardModel extends BaseModel implements MyCardContract.Model {

    @Override
    public void getMyCards(Callback<CardEntity> callback) {
        callback.onSuccess(null);
    }
}
