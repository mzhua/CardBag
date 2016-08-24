package com.wonders.xlab.cardbag.ui.cardmy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.MVPActivity;
import com.wonders.xlab.cardbag.data.MyCardModel;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchActivity;
import com.wonders.xlab.cardbag.util.DensityUtil;
import com.wonders.xlab.cardbag.widget.SideBar;
import com.wonders.xlab.cardbag.widget.TopBar;
import com.wonders.xlab.cardbag.widget.decoration.HorizontalDividerItemDecoration;

import java.util.List;

public class CardMyActivity extends MVPActivity implements CardMyContract.View {

    private TopBar mTopBar;
    private ImageView mIvAdd;
    private RecyclerView mIconRecyclerView;
    private RecyclerView mListRecyclerView;
    private SideBar mSideBar;
    private CardMyIconRVAdapter mIconRVAdapter;
    private CardMyListRVAdapter mListRVAdapter;

    private CardMyContract.Presenter mPresenter;


    private boolean mIsIconMode = true;

    @Override
    protected BaseContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_my_card_activity);
        findViews();
        setupListener();
        initRecyclerView();

    }

    private void findViews() {
        mTopBar = (TopBar) findViewById(R.id.top_bar);
        mSideBar = (SideBar) findViewById(R.id.side_bar);
        mIconRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mListRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_list);
        mIvAdd = (ImageView) findViewById(R.id.iv_add);
    }

    private void initRecyclerView() {
        mIconRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        mIconRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        int decoMargin = DensityUtil.dp2px(this, 24);
        mListRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(getResources().getColor(R.color.cbDivider)).size(1).margin(decoMargin,decoMargin).build());
    }

    private void setupListener() {
        mTopBar.setOnRightMenuClickListener(new TopBar.OnRightMenuClickListener() {
            @Override
            public void onClick(View view) {
                mSideBar.setVisibility(mIsIconMode ? View.VISIBLE : View.INVISIBLE);
                ((ImageView) view).setImageResource(mIsIconMode ? R.drawable.ic_menu_icon : R.drawable.ic_menu_list);
                mIsIconMode = !mIsIconMode;
                switchRecyclerView();
            }
        });
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CardMyActivity.this, CardSearchActivity.class));
            }
        });
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                mListRVAdapter.scrollTo(mListRecyclerView,s);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter == null) {
            mPresenter = new CardMyPresenter(new MyCardModel(), this);
        }
        mPresenter.getMyCards();
    }

    @Override
    public void showMyCards(List<CardEntity> cardEntityList) {
        if (mIconRVAdapter == null) {
            mIconRVAdapter = new CardMyIconRVAdapter();
            mIconRecyclerView.setAdapter(mIconRVAdapter);
        }
        mIconRVAdapter.setDatas(cardEntityList);
        if (mListRVAdapter == null) {
            mListRVAdapter = new CardMyListRVAdapter();
            mListRecyclerView.setAdapter(mListRVAdapter);
        }
        mListRVAdapter.setDatas(cardEntityList);
    }

    private void switchRecyclerView() {
        if (mIsIconMode) {
            mListRecyclerView.setVisibility(View.INVISIBLE);
            mIconRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mIconRecyclerView.setVisibility(View.INVISIBLE);
            mListRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
