package com.wonders.xlab.cardbag.manager;

import android.content.Context;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.util.MyMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by hua on 16/8/26.
 */
public class RealmManager {
    private static RealmManager realmManager;
    private static Realm realm;

    private RealmManager() {

    }

    public static RealmManager init(Context context) {
        if (realmManager == null) {
            realmManager = new RealmManager();
            RealmConfiguration realmConfig = new RealmConfiguration
                    .Builder(context)
                    .schemaVersion(context.getResources().getInteger(R.integer.schemaVersion))
                    .migration(new MyMigration())
                    .build();
            Realm.setDefaultConfiguration(realmConfig);
            realm = Realm.getDefaultInstance();
        }

        return realmManager;
    }

    public static Realm getRealm() {
        if (realm == null) {
            throw new NullPointerException("please call init first");
        }
        return realm;
    }
}
