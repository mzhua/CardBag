package com.wonders.xlab.cardbag.ui.mycard;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.entity.CardEntity;

/**
 * Created by hua on 16/8/18.
 */
public class MyCardPresenter extends BasePresenter implements MyCardContract.Presenter{
    private MyCardContract.Model mModel;
    private MyCardContract.View mView;

    public MyCardPresenter(MyCardContract.Model model, MyCardContract.View view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void getMyCards() {
        mModel.getMyCards(new BaseContract.Model.Callback<CardEntity>() {
            @Override
            public void onSuccess(CardEntity cardEntity) {
                mView.showMyCards();
            }

            @Override
            public void onFail(DefaultException e) {
                mView.showToastMessage(e.getMessage());
            }
        });
    }
}
