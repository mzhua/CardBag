package com.wonders.xlab.cardbag.util;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by hua on 16/8/25.
 */

public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
       /* if (oldVersion == 0) {
            schema.get("CardEntity")
                    .addField("mCreateDate", long.class);
            oldVersion++;
        }*/
    }
}
