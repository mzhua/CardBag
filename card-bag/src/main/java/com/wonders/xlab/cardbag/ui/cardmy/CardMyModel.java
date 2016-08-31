package com.wonders.xlab.cardbag.ui.cardmy;

import com.wonders.xlab.cardbag.base.BaseModel;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
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

public class CardMyModel extends BaseModel implements CardMyContract.Model {

    private Realm realm = RealmManager.getRealm();

    @Override
    public void getMyCards(final Callback<List<CardEntity>> callback) {
        RealmResults<CardEntity> allAsync = realm.where(CardEntity.class).findAllSorted("mCreateDate", Sort.DESCENDING);
        List<CardEntity> cardEntityList = new ArrayList<>();
        cardEntityList.addAll(allAsync);
        callback.onSuccess(cardEntityList);
    }

    @Override
    public synchronized void deleteCards(HashSet<Long> ids, Callback<String> callback) {
        if (ids != null && ids.size() > 0) {
           /* Long[] idl = new Long[ids.size()];
            int i = 0;
            for (Long id : ids) {
                idl[i] = id;
                i++;
            }*/
            try {
                for (Long id : ids) {
                    realm.beginTransaction();
//                    realm.where(CardEntity.class).in("mId", idl).findAll().deleteAllFromRealm();
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
