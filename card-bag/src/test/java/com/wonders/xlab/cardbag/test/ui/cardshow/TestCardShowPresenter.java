package com.wonders.xlab.cardbag.test.ui.cardshow;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.CardContract;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.ui.cardshow.CardShowContract;
import com.wonders.xlab.cardbag.ui.cardshow.CardShowPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by hua on 16/9/9.
 */
@RunWith(JUnit4.class)
public class TestCardShowPresenter {

    @Mock
    CardContract.Model mCardModel;

    @Mock
    CardShowContract.View mView;

    CardShowPresenter mCardShowPresenter;

    @Before
    public void setup() {
        initMocks(this);
        mCardShowPresenter = new CardShowPresenter(mView, mCardModel);
    }

    @Test
    public void testModelGetAllCardsCalled() {
        mCardShowPresenter.getAllCards();
        verify(mCardModel, times(1)).getAllCards(ArgumentMatchers.<BaseContract.Model.Callback<List<CardEntity>>>any());
    }

    @Test
    public void testCallbackReturnSuccessWithNoData() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                BaseContract.Model.Callback<List<CardEntity>> callback = (BaseContract.Model.Callback<List<CardEntity>>) arguments[0];
                List<CardEntity> list = new ArrayList<>();
                callback.onSuccess(list);
                return 200;
            }
        }).when(mCardModel).getAllCards(ArgumentMatchers.<BaseContract.Model.Callback<List<CardEntity>>>any());
        mCardShowPresenter.getAllCards();
        verify(mView, times(1)).noCardData();
        verify(mView, times(1)).showMenu(anyBoolean());
    }

    @Test
    public void testCallbackReturnSuccessWithNull() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                BaseContract.Model.Callback<List<CardEntity>> callback = (BaseContract.Model.Callback<List<CardEntity>>) arguments[0];
                callback.onSuccess(null);
                return 200;
            }
        }).when(mCardModel).getAllCards(ArgumentMatchers.<BaseContract.Model.Callback<List<CardEntity>>>any());
        mCardShowPresenter.getAllCards();
        verify(mView, times(1)).noCardData();
        verify(mView, times(1)).showMenu(anyBoolean());
    }

    @Test
    public void testCallbackReturnSuccessWithData() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                BaseContract.Model.Callback<List<CardEntity>> callback = (BaseContract.Model.Callback<List<CardEntity>>) arguments[0];
                List<CardEntity> list = new ArrayList<>();
                list.add(new CardEntity());
                callback.onSuccess(list);
                return 200;
            }
        }).when(mCardModel).getAllCards(ArgumentMatchers.<BaseContract.Model.Callback<List<CardEntity>>>any());
        mCardShowPresenter.getAllCards();
        verify(mView, times(1)).showCardViewPager(ArgumentMatchers.<CardEntity>anyList());
        verify(mView, times(1)).showMenu(anyBoolean());
    }

    @Test
    public void testCallbackReturnFailed() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                BaseContract.Model.Callback callback = (BaseContract.Model.Callback) arguments[0];
                callback.onFail(any(DefaultException.class));
                return 500;
            }
        }).when(mCardModel).getAllCards(ArgumentMatchers.<BaseContract.Model.Callback<List<CardEntity>>>any());
        mCardShowPresenter.getAllCards();
        verify(mView, times(1)).showToastMessage(anyString());
    }
}
