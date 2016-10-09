package com.wonders.xlab.cardbag.ui.cardsearch;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardSearchEntity;

import java.util.List;

/**
 * Created by hua on 16/8/23.
 */

public class CardSearchPresenter extends BasePresenter implements CardSearchContract.Presenter {
    private CardSearchContract.View mView;
    private CardSearchContract.Model mModel;

    /**
     * @param view
     * @param model if model is null, then search card from owe own server
     */
    public CardSearchPresenter(@NonNull CardSearchContract.View view,@Nullable CardSearchContract.Model model) {
        mView = view;
        mModel = model;
        attachModels(mModel);
    }

    @Override
    public void searchByCardName(String cardName) {
        if (null == mModel) {
            mView.showSearchResult(null);
        } else {
            mModel.searchByCardName(cardName, new BaseContract.Model.Callback<List<CardSearchEntity.ResultsEntity>>() {
                @Override
                public void onSuccess(List<CardSearchEntity.ResultsEntity> resultsEntities) {
                    mView.showSearchResult(resultsEntities);
                }

                @Override
                public void onFail(DefaultException e) {
                    mView.showToastMessage(e.getMessage());
                }
            });
        }
    }
}
