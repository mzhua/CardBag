package com.wonders.xlab.cardbag.ui.cardsearch;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.List;

/**
 * Created by hua on 16/8/23.
 */

public interface CardSearchContract {
    interface View extends BaseContract.View{
        void showSearchResult(List<CardEntity> cardEntityList);
    }

    interface Presenter extends BaseContract.Presenter{
        void searchByCardName(String cardName);
    }
}
