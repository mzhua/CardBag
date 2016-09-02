package com.wonders.xlab.cardbag.db;

import android.content.Context;

import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.List;

/**
 * Created by hua on 16/9/2.
 */

public class CBDataSyncHelper {
    private static CBDataSyncHelper instance = null;
    private CBCardBagDB mCBCardBagDB;

    private CBDataSyncHelper() {
    }

    private CBDataSyncHelper(Context context) {
        this.mCBCardBagDB = CBCardBagDB.getInstance(context);
    }

    public static CBDataSyncHelper getInstance(Context context) {
        synchronized (CBDataSyncHelper.class) {
            if (instance == null) {
                instance = new CBDataSyncHelper(context);
            }
        }
        return instance;
    }

    public void updateCardsInfo(List<CardEntity> cardEntities) {
        mCBCardBagDB.insertOrReplaceWithBatchData(cardEntities);
    }

    public void getAllCardsInfo() {
        mCBCardBagDB.queryAllOrderByCreateDateDesc();
    }
}
