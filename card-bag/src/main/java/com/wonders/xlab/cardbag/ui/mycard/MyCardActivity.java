package com.wonders.xlab.cardbag.ui.mycard;

import android.os.Bundle;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.MVPActivity;

public class MyCardActivity extends MVPActivity implements MyCardContract.View{

    private MyCardContract.Presenter mPresenter;
    private MyCardContract.Model mModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_my_card_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter == null) {
            mModel = new MyCardsModel();
            mPresenter = new MyCardPresenter(mModel,this);
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
