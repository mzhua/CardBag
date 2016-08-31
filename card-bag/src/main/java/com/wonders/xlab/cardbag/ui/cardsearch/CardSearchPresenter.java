package com.wonders.xlab.cardbag.ui.cardsearch;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardSearchEntity;

/**
 * Created by hua on 16/8/23.
 */

public class CardSearchPresenter extends BasePresenter implements CardSearchContract.Presenter{
    private CardSearchContract.View mView;
    private CardSearchContract.Model mModel;

    public CardSearchPresenter(CardSearchContract.View view,CardSearchContract.Model model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void searchByCardName(String cardName) {
        mModel.searchByCardName(cardName, new BaseContract.Model.Callback<CardSearchEntity>() {
            @Override
            public void onSuccess(CardSearchEntity cardSearchEntity) {
                mView.showSearchResult(cardSearchEntity.getResults());
            }

            @Override
            public void onFail(DefaultException e) {
                mView.showToastMessage(e.getMessage());
            }
        });
    }
}
