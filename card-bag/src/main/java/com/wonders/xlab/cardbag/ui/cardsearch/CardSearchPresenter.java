package com.wonders.xlab.cardbag.ui.cardsearch;

import com.wonders.xlab.cardbag.base.BasePresenter;
import com.wonders.xlab.cardbag.data.entity.CardEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 16/8/23.
 */

public class CardSearchPresenter extends BasePresenter implements CardSearchContract.Presenter{
    private CardSearchContract.View mView;

    public CardSearchPresenter(CardSearchContract.View view) {
        mView = view;
    }

    @Override
    public void searchByCardName(String cardName) {
        List<CardEntity> cardEntityList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            CardEntity entity = new CardEntity();
            if (i > 8) {
                entity.setCardName("浙江" + i);
            } else {
                switch (i) {
                    case 0:
                        entity.setCardName("你好");
                        break;
                    case 1:
                        entity.setCardName("啊啊啊");
                        break;
                    case 4:
                        entity.setCardName("建宁");
                        break;
                    case 5:
                        entity.setCardName("b好cd");
                        break;
                    case 8:
                        entity.setCardName("吉安");
                        break;
                    default:
                        entity.setCardName("Card Name " + i);

                }
            }

            entity.setImgUrl("http://upload-images.jianshu.io/upload_images/598650-71ec1d3457194419.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240");
            cardEntityList.add(entity);
        }
        mView.showSearchResult(cardEntityList);
    }
}
