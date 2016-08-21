package com.wonders.xlab.cardbag.ui.mycard;

import android.os.Bundle;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.BaseActivity;

public class CBMyCardActivity extends BaseActivity implements CBMyCardContract.View{

    private CBMyCardContract.Presenter mPresenter;
    private CBMyCardContract.Model mModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_my_card_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter == null) {
            mModel = new CBMyCardsModel();
            mPresenter = new CBMyCardPresenter(mModel);
        }
        mPresenter.getMyCards();
    }

    @Override
    public void showMyCards() {
        showShortToast("my cards");
    }

    @Override
    public void showToastMessage(String message) {
        showShortToast(message);
    }
}
