package com.wonders.xlab.cardbag.ui.cardmy;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.List;

/**
 * Created by hua on 16/8/18.
 */
public class CardMyPresenter extends BasePresenter implements CardMyContract.Presenter {
    private CardMyContract.Model mModel;
    private CardMyContract.View mView;

    public CardMyPresenter(CardMyContract.Model model, CardMyContract.View view) {
        mModel = model;
        mView = view;
        attachModels(mModel);
    }

    @Override
    public void getMyCards() {
        mModel.getMyCards(new BaseContract.Model.Callback<List<CardEntity>>() {
            @Override
            public void onSuccess(List<CardEntity> cardEntityList) {
                mView.showMyCards(cardEntityList);
            }

            @Override
            public void onFail(DefaultException e) {
                mView.showToastMessage(e.getMessage());
            }
        });
    }
}
