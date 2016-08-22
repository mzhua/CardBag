package com.wonders.xlab.cardbag.ui.mycard;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.MVPActivity;
import com.wonders.xlab.cardbag.data.MyCardModel;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.List;

public class MyCardActivity extends MVPActivity implements MyCardContract.View {

    private ImageView mIvAdd;
    private RecyclerView mRecyclerView;
    private MyCardRVAdapter mAdapter;

    private MyCardContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_my_card_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mIvAdd = (ImageView) findViewById(R.id.iv_add);
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShortToast("add new card");
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter == null) {
            mPresenter = new MyCardPresenter(new MyCardModel(), this);
//            attachPresenter(mPresenter);
        }
        mPresenter.getMyCards();
    }

    @Override
    public void showMyCards(List<CardEntity> cardEntityList) {
        if (mAdapter == null) {
            mAdapter = new MyCardRVAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.setDatas(cardEntityList);
    }

    @Override
    public void showToastMessage(String message) {
        showShortToast(message);
    }
}
