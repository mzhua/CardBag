package com.wonders.xlab.cardbag.test.ui.cardmy;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.CardContract;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyContract;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by hua on 16/10/9.
 */
@RunWith(JUnit4.class)
public class TestCardMyPresenter {

    @Mock
    public CardContract.Model mModel;

    @Mock
    public CardMyContract.View mView;

    @Captor
    public ArgumentCaptor<BaseContract.Model.Callback<List<CardEntity>>> mCallbackGetMyCards;

    @Captor
    public ArgumentCaptor<BaseContract.Model.Callback<String>> mCallbackDelete;

    private CardMyPresenter mPresenter;

    @Before
    public void setup() {
        initMocks(this);
        mPresenter = new CardMyPresenter(mModel, mView);
    }

    @Test
    public void testLoadCardsResult_ShowEmptyCardListView() {
        mPresenter.getMyCards();
        verify(mModel).getAllCards(mCallbackGetMyCards.capture());
        ArrayList<CardEntity> result = new ArrayList<>();
        mCallbackGetMyCards.getValue().onSuccess(result);
        verify(mView).showMyCards(eq(result));
    }

    @Test
    public void testLoadFailed_ShowErrorToast() {
        mPresenter.getMyCards();
        verify(mModel).getAllCards(mCallbackGetMyCards.capture());
        String errorMsg = "error";
        mCallbackGetMyCards.getValue().onFail(new DefaultException(errorMsg));
        verify(mView).showToastMessage(eq(errorMsg));
    }

    @Test
    public void testDeleteWithNonIds_ShowToast() {
        mPresenter.deleteCards(null);
        verify(mView).noCardWillBeDeleted();
        verify(mModel, never()).deleteCards(ArgumentMatchers.<HashSet<String>>any(), mCallbackDelete.capture());
    }

    @Test
    public void testDeleteWithEmptyIds_ShowToast() {
        mPresenter.deleteCards(new HashSet<String>());
        verify(mView).noCardWillBeDeleted();
        verify(mModel, never()).deleteCards(ArgumentMatchers.<HashSet<String>>any(), mCallbackDelete.capture());
    }

    @Test
    public void testDeleteSuccess_ShowSuccessMessageView() {
        HashSet<String> ids = new HashSet<>();
        String id = "1";
        ids.add(id);
        mPresenter.deleteCards(ids);
        verify(mModel).deleteCards(ArgumentMatchers.<HashSet<String>>any(), mCallbackDelete.capture());
        String message = "message";
        mCallbackDelete.getValue().onSuccess(message);
        verify(mView).deleteSuccess(eq(message));
    }

    @Test
    public void testDeleteFailed_ShowFailedMessageView() {
        HashSet<String> ids = new HashSet<>();
        ids.add("1");
        mPresenter.deleteCards(ids);
        verify(mModel).deleteCards(ArgumentMatchers.<HashSet<String>>any(), mCallbackDelete.capture());
        String message = "message";
        mCallbackDelete.getValue().onFail(new DefaultException(message));
        verify(mView).showToastMessage(eq(message));
    }
}
