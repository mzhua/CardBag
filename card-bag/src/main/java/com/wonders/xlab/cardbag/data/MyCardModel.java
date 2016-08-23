package com.wonders.xlab.cardbag.data;

import com.wonders.xlab.cardbag.base.BaseModel;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.data.entity.MyCardEntity;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 16/8/21.
 */

public class MyCardModel extends BaseModel implements CardMyContract.Model {

    @Override
    public void getMyCards(Callback<MyCardEntity> callback) {
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
        callback.onSuccess(new MyCardEntity(cardEntityList));
    }
}
