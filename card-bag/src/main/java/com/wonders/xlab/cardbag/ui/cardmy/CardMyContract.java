package com.wonders.xlab.cardbag.ui.cardmy;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.HashSet;
import java.util.List;

/**
 * Created by hua on 16/8/21.
 */

public interface CardMyContract {
    interface View extends BaseContract.View{
        void showMyCards(List<CardEntity> cardEntityList);
    }

    interface Presenter extends BaseContract.Presenter{
        void getMyCards();

        void deleteCards(HashSet<CardEntity> cardEntities);
    }

    interface Model extends BaseContract.Model {
        void getMyCards(BaseContract.Model.Callback<List<CardEntity>> callback);

        void deleteCards(HashSet<CardEntity> cardEntities,BaseContract.Model.Callback<String> callback);
    }

}
