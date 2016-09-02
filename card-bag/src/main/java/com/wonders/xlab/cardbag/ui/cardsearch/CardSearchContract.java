package com.wonders.xlab.cardbag.ui.cardsearch;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.data.entity.CardSearchEntity;

import java.util.List;

/**
 * Created by hua on 16/8/23.
 */

public interface CardSearchContract {
    interface View extends BaseContract.View {
        void showSearchResult(List<CardSearchEntity.ResultsEntity> cardEntityList);
    }

    interface Presenter extends BaseContract.Presenter {
        void searchByCardName(String cardName);
    }

    interface Model extends BaseContract.Model {
        void searchByCardName(String cardName, Callback<List<CardSearchEntity.ResultsEntity>> callback);
    }
}
