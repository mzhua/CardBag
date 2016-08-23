package com.wonders.xlab.cardbag.data.entity;

import com.wonders.xlab.cardbag.base.BaseEntity;

/**
 * Created by hua on 16/8/21.
 */

public class CardEntity extends BaseEntity {
    private String id;
    private String mImgUrl;
    private String mCardName;

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        mImgUrl = imgUrl;
    }

    public String getCardName() {
        return mCardName;
    }

    public void setCardName(String cardName) {
        mCardName = cardName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
