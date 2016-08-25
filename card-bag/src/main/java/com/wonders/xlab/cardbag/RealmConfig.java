package com.wonders.xlab.cardbag;

import android.content.Context;

import com.wonders.xlab.cardbag.util.MyMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by hua on 16/8/25.
 */

public class RealmConfig {
    private static Realm mRealm;

    public static Realm initRealm(Context context, long schemaVersion) {
        if (null == mRealm) {
            RealmConfiguration realmConfig = new RealmConfiguration
                    .Builder(context)
                    .schemaVersion(schemaVersion)
                    .migration(new MyMigration())
                    .build();
            Realm.setDefaultConfiguration(realmConfig);
            mRealm = Realm.getDefaultInstance();
        }

        return mRealm;
    }

    public static Realm getRealm() {
        if (mRealm == null) {
            throw new IllegalArgumentException("must call initRealm first");
        }
        return mRealm;
    }
}
