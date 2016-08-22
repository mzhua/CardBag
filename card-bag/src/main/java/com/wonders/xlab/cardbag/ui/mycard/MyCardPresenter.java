package com.wonders.xlab.cardbag.ui.mycard;

import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.base.DefaultException;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 16/8/18.
 */
public class MyCardPresenter extends BasePresenter implements MyCardContract.Presenter{
    private MyCardContract.Model mModel;
    private MyCardContract.View mView;

    public MyCardPresenter(MyCardContract.Model model, MyCardContract.View view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void getMyCards() {
        mModel.getMyCards(new BaseContract.Model.Callback<CardEntity>() {
            @Override
            public void onSuccess(CardEntity cardEntity) {
                List<CardEntity> cardEntityList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    CardEntity entity = new CardEntity();
                    entity.setCardName("Card Name " + i);
                    entity.setImgUrl("http://upload-images.jianshu.io/upload_images/598650-71ec1d3457194419.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240");
                    cardEntityList.add(entity);
                }
                mView.showMyCards(cardEntityList);
            }

            @Override
            public void onFail(DefaultException e) {
                mView.showToastMessage(e.getMessage());
            }
        });
    }
}
