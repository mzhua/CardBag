package com.wonders.xlab.cardbag.ui.cardshow;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.List;

public class CardShowPresenter extends BasePresenter implements CardShowContract.Presenter {
    private CardShowContract.View mView;
    private CardShowContract.Model mModel;

    public CardShowPresenter(CardShowContract.View view, CardShowContract.Model model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void getAllCards() {
        mModel.getAllCards(new BaseContract.Model.Callback<List<CardEntity>>() {
            @Override
            public void onSuccess(List<CardEntity> cardEntityList) {
                mView.showCardViewPager(cardEntityList);
            }

            @Override
            public void onFail(DefaultException e) {
                mView.showToastMessage(e.getMessage());
            }
        });
    }
}
