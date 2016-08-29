package com.wonders.xlab.cardbag.data;

import com.wonders.xlab.cardbag.base.BaseModel;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.manager.RealmManager;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyContract;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by hua on 16/8/21.
 */

public class CardMyModel extends BaseModel implements CardMyContract.Model {

    private Realm realm = RealmManager.getRealm();

    @Override
    public void getMyCards(final Callback<List<CardEntity>> callback) {
        RealmResults<CardEntity> allAsync = realm.where(CardEntity.class).findAllSortedAsync("mCreateDate", Sort.DESCENDING);
        allAsync.addChangeListener(new RealmChangeListener<RealmResults<CardEntity>>() {
            @Override
            public void onChange(RealmResults<CardEntity> element) {
                List<CardEntity> cardEntityList = new ArrayList<>();
                for (CardEntity cardEntity : element) {
                    cardEntityList.add(cardEntity);
                }
                callback.onSuccess(cardEntityList);
            }
        });
    }

    @Override
    public void deleteCards(HashSet<CardEntity> cardEntities, Callback<String> callback) {
        if (cardEntities != null) {
            realm.beginTransaction();
            for (CardEntity cardEntity : cardEntities) {
                cardEntity.deleteFromRealm();
            }
            realm.commitTransaction();
        }

    }
}
