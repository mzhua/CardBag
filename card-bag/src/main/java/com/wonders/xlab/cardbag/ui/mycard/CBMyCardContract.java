package com.wonders.xlab.cardbag.ui.mycard;

import com.wonders.xlab.cardbag.base.BaseContract;

/**
 * Created by hua on 16/8/21.
 */

public interface CBMyCardContract {
    interface View extends BaseContract.View{
        void showMyCards();
    }

    interface Presenter extends BaseContract.Presenter{
        void getMyCards();
    }

    interface Model extends BaseContract.Model {
        void getMyCards(BaseContract.Model.Callback callback);
    }

}
