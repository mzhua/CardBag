package com.wonders.xlab.cardbag.data;

import android.content.Context;

import com.wonders.xlab.cardbag.base.BaseModel;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.db.CBCardBagDB;
import com.wonders.xlab.cardbag.util.FileUtil;

import java.util.HashSet;
import java.util.List;

/**
 * Created by hua on 16/8/21.
 */

public class CardModel extends BaseModel implements CardContract.Model {
    private CBCardBagDB mCBCardBagDB;


    public CardModel(Context context) {
        mCBCardBagDB = CBCardBagDB.getInstance(context);
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

    @Override
    public void getAllCards(final Callback<List<CardEntity>> callback) {
        try {
            callback.onSuccess(mCBCardBagDB.queryAllOrderByCreateDateDesc());
        } catch (IllegalArgumentException e) {
            callback.onFail(new DefaultException(e));
        }
    }

    @Override
    public synchronized void deleteCards(HashSet<String> ids, Callback<String> callback) {
        List<CardEntity> cardEntities = mCBCardBagDB.queryByIds(ids);
        if (cardEntities != null) {
            for (CardEntity cardEntity : cardEntities) {
                if (cardEntity != null) {
                    FileUtil.deleteFile(cardEntity.getFrontImgFilePath());
                    FileUtil.deleteFile(cardEntity.getBackImgFilePath());
                }
            }
        }
        int counts = mCBCardBagDB.deleteByIds(ids);
        callback.onSuccess("删除" + counts + "张卡片");
    }
}
