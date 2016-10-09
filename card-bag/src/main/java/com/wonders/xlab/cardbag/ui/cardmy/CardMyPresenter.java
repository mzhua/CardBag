package com.wonders.xlab.cardbag.ui.cardmy;

import android.support.annotation.NonNull;

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

    public CardMyPresenter(@NonNull CardContract.Model model,@NonNull CardMyContract.View view) {
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
    public void deleteCards(HashSet<String> ids) {
        if (ids == null || ids.isEmpty()) {
            mView.noCardWillBeDeleted();
            return;
        }
        mModel.deleteCards(ids, new BaseContract.Model.Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mView.deleteSuccess(s);
            }

            @Override
            public void onFail(DefaultException e) {
                mView.showToastMessage(e.getMessage());
            }
        });
    }
}
