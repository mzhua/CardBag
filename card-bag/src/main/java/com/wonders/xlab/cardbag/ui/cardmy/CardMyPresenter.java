package com.wonders.xlab.cardbag.ui.cardmy;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.CardContract;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.HashSet;
import java.util.List;

/**
 * Created by hua on 16/8/18.
 */
public class CardMyPresenter extends BasePresenter implements CardMyContract.Presenter {
    private CardContract.Model mModel;
    private CardMyContract.View mView;

    public CardMyPresenter(CardContract.Model model, CardMyContract.View view) {
        mModel = model;
        mView = view;
        attachModels(mModel);
    }

    @Override
    public void getMyCards() {
        mModel.getAllCards(new BaseContract.Model.Callback<List<CardEntity>>() {
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

    @Override
    public void deleteCards(HashSet<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            mView.showToastMessage("数据为空");
            return;
        }
        mModel.deleteCards(ids, new BaseContract.Model.Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mView.showToastMessage(s);
                mView.deleteSuccess();
            }

            @Override
            public void onFail(DefaultException e) {
                mView.showToastMessage(e.getMessage());
            }
        });
    }
}
