package com.wonders.xlab.cardbag.ui.cardedit;


import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.CardContract;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.util.TextUtils;

import java.util.UUID;

/**
 * Created by hua on 16/8/26.
 */

public class CardEditPresenter extends BasePresenter implements CardEditContract.Presenter {
    private CardEditContract.View mView;
    private CardContract.Model mModel;

    public CardEditPresenter(CardEditContract.View view, CardContract.Model model) {
        mView = view;
        mModel = model;
        attachModels(mModel);
    }

    @Override
    public void saveCard(CardEntity cardEntity) {
        if (TextUtils.isEmpty(cardEntity.getCardName())) {
            mView.showCardNameEmptyMessage();
            return;
        }
        if (TextUtils.isEmpty(cardEntity.getBarCode())) {
            mView.showBarCodeNonMessage();
            return;
        }
        long timeMillis = System.currentTimeMillis();
        cardEntity.setId(TextUtils.isEmpty(cardEntity.getId()) ? UUID.randomUUID().toString() : cardEntity.getId());
        cardEntity.setCreateDate(timeMillis);
        mModel.saveCard(cardEntity, new BaseContract.Model.Callback<String>() {
            @Override
            public void onSuccess(String s) {
                mView.saveSuccess();
            }

            @Override
            public void onFail(DefaultException e) {
                mView.showToastMessage(e.getMessage());
            }
        });
    }
}
