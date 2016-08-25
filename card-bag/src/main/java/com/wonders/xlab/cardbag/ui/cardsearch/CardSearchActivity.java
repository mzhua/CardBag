package com.wonders.xlab.cardbag.ui.cardsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BaseRecyclerViewAdapter;
import com.wonders.xlab.cardbag.base.MVPActivity;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditActivity;

import java.util.List;

public class CardSearchActivity extends MVPActivity implements CardSearchContract.View {
    private final int REQUEST_CODE_CARD_EDIT = 1234;

    private RecyclerView mRecyclerView;
    private EditText mEtCardName;

    private CardSearchRVAdapter mCardSearchRVAdapter;

    private CardSearchContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_card_search_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mEtCardName = (EditText) findViewById(R.id.et_card_name);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        mPresenter = new CardSearchPresenter(this);

        mEtCardName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mPresenter.searchByCardName(v.getText().toString());
                    hideKeyboardForce(v.getWindowToken());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected BaseContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showSearchResult(List<CardEntity> cardEntityList) {
        if (null == mCardSearchRVAdapter) {
            mCardSearchRVAdapter = new CardSearchRVAdapter();
            mCardSearchRVAdapter.setOnClickListener(new BaseRecyclerViewAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(CardSearchActivity.this, CardEditActivity.class);
                    CardEntity bean = mCardSearchRVAdapter.getBean(position);
                    bean.setCardName(mEtCardName.getText().toString());
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
}
