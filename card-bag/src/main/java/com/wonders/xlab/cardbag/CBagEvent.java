package com.wonders.xlab.cardbag;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by hua on 16/9/21.
 */

public class CBagEvent {

    private static CBagEvent instance = null;

    private CBagEvent() {
    }

    public static CBagEvent getInstance() {
        synchronized (CBagEvent.class) {
            if (instance == null) {
                instance = new CBagEvent();
            }
        }
        return instance;
    }

    public String getActionOfEventBroadcast(Context context) {
        return context.getPackageName() + CBagEventConstant.EVENT_BROADCAST_SUFFIX;
    }

    /**
     * convert intent to event data
     *
     * @param intent
     * @return
     */
    public CBagEventDataBean getEventData(Context context,@NonNull Intent intent) {
        if (!intent.getAction().equals(context.getPackageName() + CBagEventConstant.EVENT_BROADCAST_SUFFIX)) {
            throw new IllegalArgumentException("please pass the intent with action (packagename + '.cb.event')");
        }
        CBagEventDataBean bean = new CBagEventDataBean();
        bean.setEvent(intent.getStringExtra(CBagEventConstant.EXTRA_KEY_EVENT));
        bean.setName(intent.getStringExtra(CBagEventConstant.EXTRA_KEY_NAME));
        bean.setTimeInMill(intent.getLongExtra(CBagEventConstant.EXTRA_KEY_TIME_IN_MILL, System.currentTimeMillis()));
        return bean;
    }
}
