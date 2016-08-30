package com.wonders.xlab.cardbag.ui.cardshow;

import com.wonders.xlab.cardbag.base.BaseModel;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.manager.RealmManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import io.realm.Sort;

public class CardShowModel extends BaseModel implements CardShowContract.Model {

    @Override
    public void getAllCards(Callback<List<CardEntity>> callback) {
        RealmResults<CardEntity> allAsync = RealmManager.getRealm().where(CardEntity.class).findAllSorted("mCreateDate", Sort.DESCENDING);
        List<CardEntity> cardEntityList = new ArrayList<>();
        cardEntityList.addAll(allAsync);
        callback.onSuccess(cardEntityList);
    }
}
