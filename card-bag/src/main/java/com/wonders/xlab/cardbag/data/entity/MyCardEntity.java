package com.wonders.xlab.cardbag.data.entity;

import java.util.List;

/**
 * Created by hua on 16/8/22.
 */

public class MyCardEntity{
    private List<CardEntity> mCardList;

    public MyCardEntity(List<CardEntity> cardList) {
        mCardList = cardList;
    }

    public List<CardEntity> getCardList() {
        return mCardList;
    }

    public void setCardList(List<CardEntity> cardList) {
        mCardList = cardList;
    }
}
