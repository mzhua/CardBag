package com.wonders.xlab.cardbag.ui.cardsearch;

import com.wonders.xlab.cardbag.base.BasePresenter;

/**
 * Created by hua on 16/8/23.
 */

public class CardSearchPresenter extends BasePresenter implements CardSearchContract.Presenter{
    private CardSearchContract.View mView;

    public CardSearchPresenter(CardSearchContract.View view) {
        mView = view;
    }

    @Override
    public void searchByCardName(String cardName) {
        mView.showSearchResult(null);
    }
}
