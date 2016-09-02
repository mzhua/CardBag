package com.wonders.xlab.cardbag.data;

import com.wonders.xlab.cardbag.base.BaseModel;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.db.CBCardBagDB;
import com.wonders.xlab.cardbag.manager.RealmManager;
import com.wonders.xlab.cardbag.util.FileUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by hua on 16/8/21.
 */

public class CardModel extends BaseModel implements CardContract.Model {
    private CBCardBagDB mCBCardBagDB;

    private Realm realm = RealmManager.getRealm();

    public CardModel(CBCardBagDB cbCardBagDB) {
        mCBCardBagDB = cbCardBagDB;
    }

    @Override
    public void saveCard(final CardEntity cardEntity, final Callback<String> callback) {
        long l = mCBCardBagDB.insertOrReplace(cardEntity);
        if (l != -1) {
            callback.onSuccess("保存成功");
        } else {
            callback.onFail(new DefaultException("保存失败"));
        }

    }

    @Deprecated
    private void saveWithRealm(final CardEntity cardEntity, final Callback<String> callback) {
        Realm realm = RealmManager.getRealm();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                realm.copyToRealmOrUpdate(cardEntity);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess("保存成功");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onFail(new DefaultException(error));
            }
        });
    }

    @Override
    public void getAllCards(final Callback<List<CardEntity>> callback) {
        try {
            callback.onSuccess(mCBCardBagDB.queryAllOrderByCreateDateDesc());
        } catch (IllegalArgumentException e) {
            callback.onFail(new DefaultException(e));
        }
    }

    @Deprecated
    private void getAllCardsWithRealm(Callback<List<CardEntity>> callback) {
        RealmResults<CardEntity> allAsync = realm.where(CardEntity.class).findAllSorted("mCreateDate", Sort.DESCENDING);
        List<CardEntity> cardEntityList = new ArrayList<>();
        cardEntityList.addAll(allAsync);
        callback.onSuccess(cardEntityList);
    }

    @Override
    public synchronized void deleteCards(HashSet<Long> ids, Callback<String> callback) {
        int counts = mCBCardBagDB.deleteByIds(ids);
        callback.onSuccess("删除" + counts + "条记录");
    }

    @Deprecated
    private void deleteCardsWithRealm(HashSet<Long> ids, Callback<String> callback) {
        if (ids != null && ids.size() > 0) {
            try {
                for (Long id : ids) {
                    realm.beginTransaction();
                    CardEntity cardEntity = realm.where(CardEntity.class).equalTo("mId", id).findFirst();
                    if (cardEntity != null) {
                        FileUtil.deleteFile(cardEntity.getFrontImgFilePath());
                        FileUtil.deleteFile(cardEntity.getBackImgFilePath());
                        cardEntity.deleteFromRealm();
                    }
                    realm.commitTransaction();
                }
                callback.onSuccess("删除成功");
            } catch (Exception e) {
                callback.onFail(new DefaultException(e));
            }
        }
    }
}
