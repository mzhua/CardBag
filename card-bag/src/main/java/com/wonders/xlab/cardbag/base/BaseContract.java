package com.wonders.xlab.cardbag.base;

/**
 * Created by hua on 16/8/19.
 */
public interface BaseContract {
    interface View{
        void showToastMessage();
    }

    interface Presenter{
        void onDestroy();
    }

    interface Model{
        void onDestroy();
    }
}
