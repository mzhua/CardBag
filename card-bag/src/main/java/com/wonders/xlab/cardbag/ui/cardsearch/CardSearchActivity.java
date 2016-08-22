package com.wonders.xlab.cardbag.ui.cardsearch;

import android.app.Activity;
import android.os.Bundle;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.MVPActivity;

public class CardSearchActivity extends MVPActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_search_activity);
    }

    @Override
    protected BaseContract.Presenter getPresenter() {
        return null;
    }
}
