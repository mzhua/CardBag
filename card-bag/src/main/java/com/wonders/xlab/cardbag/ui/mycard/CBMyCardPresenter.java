package com.wonders.xlab.cardbag.ui.mycard;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.base.DefaultException;

/**
 * Created by hua on 16/8/18.
 */
public class CBMyCardPresenter extends BasePresenter implements CBMyCardContract.Presenter{
    private CBMyCardContract.Model mModel;
    private CBMyCardContract.View mView;

    public CBMyCardPresenter(CBMyCardContract.Model model,CBMyCardContract.View view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void getMyCards() {
        mModel.getMyCards(new BaseContract.Model.Callback<CBMyCardEntity>() {
            @Override
            public void onSuccess(CBMyCardEntity cbMyCardEntity) {
                mView.showMyCards();
            }

            @Override
            public void onFail(DefaultException e) {
                mView.showToastMessage(e.getMessage());
            }
        });
    }
}
