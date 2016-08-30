package com.wonders.xlab.cardbag.ui.cardshow;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.List;

/**
 * Created by hua on 16/8/26.
 */

public interface CardShowContract {
    interface View extends BaseContract.View {
        void showCardViewPager(List<CardEntity> cardEntityList);

        void showMenu(boolean show);
    }

    interface Presenter extends BaseContract.Presenter {
        void getAllCards();
    }

    interface Model extends BaseContract.Model {
        void getAllCards(Callback<List<CardEntity>> callback);
    }
}
