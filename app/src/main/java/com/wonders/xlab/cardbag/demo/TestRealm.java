package com.wonders.xlab.cardbag.demo;

import io.realm.RealmObject;

/**
 * Created by hua on 16/8/31.
 */

public class TestRealm extends RealmObject {
    private String id;

    public TestRealm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
