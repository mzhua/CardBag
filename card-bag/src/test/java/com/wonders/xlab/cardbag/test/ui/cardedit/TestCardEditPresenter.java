package com.wonders.xlab.cardbag.test.ui.cardedit;

import android.support.annotation.NonNull;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.CardContract;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditContract;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by hua on 16/10/9.
 */
@RunWith(JUnit4.class)
public class TestCardEditPresenter {
    private final String CARD_NAME = "CardName";
    private final String BAR_CODE = "12138";

    @Mock
    public CardEditContract.View mView;

    @Mock
    public CardContract.Model mModel;

    @Captor
    public ArgumentCaptor<BaseContract.Model.Callback<String>> mCallback;

    private CardEditPresenter mPresenter;

    @Before
    public void setup() {
        initMocks(this);
        mPresenter = new CardEditPresenter(mView, mModel);
    }

    @Test
    public void testSaveCardWithEmptyCardName_ShowCardNameEmptyMessage() {
        CardEntity entity = getCardEntity("", "");
        mPresenter.saveCard(entity);
        verify(mView).showCardNameEmptyMessage();
        verify(mView, never()).showBarCodeNonMessage();
        verify(mModel, never()).saveCard(eq(entity), mCallback.capture());
    }

    @Test
    public void testSaveCardWithEmptyBarCode_ShowBarCodeNonMessage() {
        CardEntity entity = getCardEntity(CARD_NAME, "");
        mPresenter.saveCard(entity);
        verify(mView).showBarCodeNonMessage();
        verify(mView, never()).showCardNameEmptyMessage();
        verify(mModel, never()).saveCard(eq(entity), mCallback.capture());
    }

    @Test
    public void testSaveCardSuccess_ShowSaveSuccessView() {
        CardEntity entity = getCardEntity(CARD_NAME, BAR_CODE);
        mPresenter.saveCard(entity);
        verify(mModel).saveCard(eq(entity), mCallback.capture());
        mCallback.getValue().onSuccess("success message");
        verify(mView).saveSuccess();
    }

    @Test
    public void testSaveCardFailed_ShowFailedToastMessage() {
        CardEntity entity = getCardEntity(CARD_NAME, BAR_CODE);
        mPresenter.saveCard(entity);
        verify(mModel).saveCard(eq(entity), mCallback.capture());
        String errorMsg = "error";
        mCallback.getValue().onFail(new DefaultException(errorMsg));
        verify(mView).showToastMessage(eq(errorMsg));
    }

    @NonNull
    private CardEntity getCardEntity(String cardName, String barCode) {
        CardEntity entity = new CardEntity();
        entity.setCardName(cardName);
        entity.setBarCode(barCode);
        return entity;
    }
}
