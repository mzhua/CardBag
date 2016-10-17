package com.wonders.xlab.cardbag;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchContract;
import com.wonders.xlab.cardbag.ui.home.HomeActivity;
import com.wonders.xlab.cardbag.util.LogUtil;

/**
 * Created by hua on 16/8/16.
 */
public class CBag {
    private static String CARD_IMG_URL_DEFAULT = "http://ocg8s5zv8.bkt.clouddn.com/pic_vip_card.png";

    private static CBag cBag;
    private Intent mCBagIntent;

    private CardSearchContract.Model mCardSearchModel;

    private CBag() {
        mCBagIntent = new Intent();
    }


    /**
     * get an instance of CBag
     */
    public static CBag get() {
        if (cBag == null) {
            cBag = new CBag();
        }
        return cBag;
    }

    public void setCardImgUrlDefault(String cardImgUrlDefault) {
        CARD_IMG_URL_DEFAULT = cardImgUrlDefault;
    }

    public String getCardImgUrlDefault() {
        return CARD_IMG_URL_DEFAULT;
    }

    public CardSearchContract.Model getCardSearchModel() {
        if (mCardSearchModel == null) {
            LogUtil.error("CBag", "please call CBag.setCardSearchModel() first before card search function");
        }
        return mCardSearchModel;
    }

    /**
     * if you want to use your own card search api, then create a class implements CardSearchContract.Model
     * and use the cardName for search, when finish the searching, call callback.onSuccess or callback.onFail to notify CBag to update the results
     *
     * @param cardSearchModel
     * @return
     */
    public CBag setCardSearchModel(CardSearchContract.Model cardSearchModel) {
        mCardSearchModel = cardSearchModel;
        return this;
    }

    /**
     * start @{@link HomeActivity}
     *
     * @param activity
     */
    public void start(@NonNull Activity activity) {
        activity.startActivity(getIntent(activity));
    }

    /**
     * start @{@link HomeActivity}
     *
     * @param fragment
     */
    public void start(@NonNull Context context, @NonNull Fragment fragment) {
        fragment.startActivity(getIntent(context));
    }

    public void start(@NonNull Context context, @NonNull android.support.v4.app.Fragment fragment) {
        fragment.startActivity(getIntent(context));
    }

    private Intent getIntent(Context context) {
        mCBagIntent = new Intent(context, HomeActivity.class);
        return mCBagIntent;
    }

}
