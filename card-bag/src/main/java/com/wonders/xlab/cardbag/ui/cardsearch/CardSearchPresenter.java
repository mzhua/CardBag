package com.wonders.xlab.cardbag.ui.cardsearch;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardSearchEntity;

/**
 * Created by hua on 16/8/23.
 */

class CardSearchPresenter extends BasePresenter implements CardSearchContract.Presenter {
    private CardSearchContract.View mView;
    private CardSearchContract.Model mModel;

    /**
     * @param view
     * @param model if model is null, then search card from owe own server
     */
    CardSearchPresenter(CardSearchContract.View view, CardSearchContract.Model model) {
        mView = view;
        if (model == null) {
            model = new CardSearchModel();
        }
        mModel = model;
        attachModels(mModel);
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
