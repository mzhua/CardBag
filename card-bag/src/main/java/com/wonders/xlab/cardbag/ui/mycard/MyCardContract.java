package com.wonders.xlab.cardbag.ui.mycard;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.List;

/**
 * Created by hua on 16/8/21.
 */

public interface MyCardContract {
    interface View extends BaseContract.View{
        void showMyCards(List<CardEntity> cardEntityList);
    }

    interface Presenter extends BaseContract.Presenter{
        void getMyCards();
    }

    interface Model extends BaseContract.Model {
        void getMyCards(BaseContract.Model.Callback<CardEntity> callback);
    }

}
