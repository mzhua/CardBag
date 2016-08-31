package com.wonders.xlab.cardbag.ui.cardedit;

import com.wonders.xlab.cardbag.base.BaseModel;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.manager.RealmManager;

import io.realm.Realm;

/**
 * Created by hua on 16/8/26.
 */

public class CardEditModel extends BaseModel implements CardEditContract.Model {

    @Override
    public void saveCard(final CardEntity cardEntity, final Callback<String> callback) {
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
}
