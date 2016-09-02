package com.wonders.xlab.cardbag.data;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.HashSet;
import java.util.List;

/**
 * Created by hua on 16/9/2.
 */

public interface CardContract {
    interface Model extends BaseContract.Model {
        void saveCard(CardEntity cardEntity, BaseContract.Model.Callback<String> callback);

        void getAllCards(BaseContract.Model.Callback<List<CardEntity>> callback);

        void deleteCards(HashSet<Long> ids, BaseContract.Model.Callback<String> callback);
    }
}
