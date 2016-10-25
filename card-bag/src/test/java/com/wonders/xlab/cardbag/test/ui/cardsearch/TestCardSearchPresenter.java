package com.wonders.xlab.cardbag.test.ui.cardsearch;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardSearchEntity;
import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchContract;
import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by hua on 16/10/9.
 */
@RunWith(JUnit4.class)
public class TestCardSearchPresenter {

    private final String CARD_NAME = "card_name";

    @Mock
    public CardSearchContract.Model mModel;

    @Mock
    public CardSearchContract.View mView;

    @Captor
    public ArgumentCaptor<BaseContract.Model.Callback<List<CardSearchEntity.ResultsEntity>>> mCallback;

    private CardSearchPresenter mPresenter;

    @Before
    public void setup() {
        initMocks(this);
        mPresenter = new CardSearchPresenter(mView, mModel);
    }

    @Test
    public void testNullModel_ShowEmptyListWithNoticeCardView() {
        mPresenter = new CardSearchPresenter(mView, null);
        mPresenter.searchByCardName(CARD_NAME);
        verify(mView).showSearchResult(null);
    }

    @Test
    public void testLoadEmptyCardsFromModel_ShowEmptyListWithNoticeCardView() {
        mPresenter.searchByCardName(CARD_NAME);
        verify(mModel).searchByCardName(anyString(), mCallback.capture());
        mCallback.getValue().onSuccess(null);
        verify(mView).showSearchResult(null);
    }

    @Test
    public void testLoadCardsFromModel_ShowCardsView() {
        mPresenter.searchByCardName(CARD_NAME);
        verify(mModel).searchByCardName(anyString(), mCallback.capture());
        List<CardSearchEntity.ResultsEntity> result = new ArrayList<>();
        mCallback.getValue().onSuccess(result);
        verify(mView).showSearchResult(eq(result));
    }

    @Test
    public void testSearchFailed_ShowErrorToast() {
        mPresenter.searchByCardName(CARD_NAME);
        verify(mModel).searchByCardName(anyString(), mCallback.capture());
        String errorMsg = "error";
        mCallback.getValue().onFail(new DefaultException(errorMsg));
        verify(mView).showToastMessage(eq(errorMsg));
    }
}
