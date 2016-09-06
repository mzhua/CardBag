package com.wonders.xlab.cardbag.db;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.List;

/**
 * Created by hua on 16/9/2.
 */

public class CBDataSyncHelper {
    private boolean mHasSyncCardData = true;

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

    public void updateCardsInfo(@NonNull List<CardEntity> cardEntities) {
        mCBCardBagDB.insertOrReplaceWithBatchData(cardEntities);
    }

    @Nullable
    public List<CardEntity> getAllCardsInfo() {
        return !hasSyncCardData() ? mCBCardBagDB.queryAllOrderByCreateDateDesc() : null;
    }

    /**
     *
     * @return 是否已经同步好数据
     */
    public boolean hasSyncCardData() {
        return mHasSyncCardData;
    }

    /**
     * @param hasSyncCardData 是否已经同步好数据
     */
    public void hasSyncCardData(boolean hasSyncCardData) {
        mHasSyncCardData = hasSyncCardData;
    }
}
