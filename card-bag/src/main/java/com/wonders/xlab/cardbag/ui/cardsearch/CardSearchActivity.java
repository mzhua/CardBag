package com.wonders.xlab.cardbag.ui.cardsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.wonders.xlab.cardbag.CBag;
import com.wonders.xlab.cardbag.CBagEventConstant;
import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.MVPActivity;
import com.wonders.xlab.cardbag.base.adapter.BaseRecyclerViewAdapter;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.data.entity.CardSearchEntity;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditActivity;
import com.wonders.xlab.cardbag.widget.XToolBarLayout;

import java.util.List;

public class CardSearchActivity extends MVPActivity<CardSearchContract.Presenter> implements CardSearchContract.View {
    private final int REQUEST_CODE_CARD_EDIT = 1234;

    private XToolBarLayout mToolBarLayout;
    private RecyclerView mRecyclerView;
    private EditText mEtCardName;

    private CardSearchRVAdapter mCardSearchRVAdapter;

    private CardSearchContract.Presenter mPresenter;

    @Override
    public CardSearchContract.Presenter getPresenter() {
        if (mPresenter == null) {
            mPresenter = new CardSearchPresenter(this, CBag.get().getCardSearchModel());
        }
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_card_search_activity);
        mToolBarLayout = (XToolBarLayout) findViewById(R.id.xtbl);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mEtCardName = (EditText) findViewById(R.id.et_card_name);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        mEtCardName.requestFocus();
        mEtCardName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getPresenter().searchByCardName(v.getText().toString());
                    hideKeyboardForce(v.getWindowToken());
                    return true;
                }
                return false;
            }
        });
        mToolBarLayout.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sendBroadcast(CBagEventConstant.EVENT_PAGE_CREATE_CARD_SEARCH, getResources().getString(R.string.cb_title_card_search));
    }

    @Override
    public void showSearchResult(List<CardSearchEntity.ResultsEntity> cardEntityList) {
        if (null == mCardSearchRVAdapter) {
            mCardSearchRVAdapter = new CardSearchRVAdapter();
            mCardSearchRVAdapter.setOnClickListener(new BaseRecyclerViewAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(CardSearchActivity.this, CardEditActivity.class);

                    CardSearchEntity.ResultsEntity entity = mCardSearchRVAdapter.getBean(position);

                    CardEntity bean = new CardEntity();
                    bean.setImgUrl(entity.getCard_img_url());
                    bean.setCardName(TextUtils.isEmpty(entity.getCard_name()) ? mEtCardName.getText().toString() : entity.getCard_name());
                    intent.putExtra("data", bean);
                    startActivityForResult(intent, REQUEST_CODE_CARD_EDIT);
                }
            });
            mRecyclerView.setAdapter(mCardSearchRVAdapter);
        }
        mCardSearchRVAdapter.setDatas(cardEntityList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CARD_EDIT) {
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendBroadcast(CBagEventConstant.EVENT_PAGE_DESTROY_CARD_SEARCH, getResources().getString(R.string.cb_title_card_search));
    }
}
