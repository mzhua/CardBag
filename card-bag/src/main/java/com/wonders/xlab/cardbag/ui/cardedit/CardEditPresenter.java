package com.wonders.xlab.cardbag.ui.cardedit;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

/**
 * Created by hua on 16/8/26.
 */

public class CardEditPresenter extends BasePresenter implements CardEditContract.Presenter {
    private CardEditContract.View mView;
    private CardEditContract.Model mModel;

    public CardEditPresenter(CardEditContract.View view, CardEditContract.Model model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void saveCard(CardEntity cardEntity) {
        /*if (TextUtils.isEmpty(cardEntity.getBarCode())) {
            mView.showToastMessage("请先扫描条形码");
            return;
        }
        if (TextUtils.isEmpty(cardEntity.getCardName())) {
            mView.showToastMessage("请输入卡片名称");
            return;
        }*/
        long timeMillis = System.currentTimeMillis();
        cardEntity.setId(cardEntity.getId() == 0 ? timeMillis : cardEntity.getId());
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
