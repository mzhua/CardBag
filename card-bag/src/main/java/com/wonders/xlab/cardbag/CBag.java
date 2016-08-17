package com.wonders.xlab.cardbag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.wonders.xlab.cardbag.ui.CBHomeActivity;

/**
 * Created by hua on 16/8/16.
 */
public class CBag {
    public static final int REQUEST_CODE_SCAN = 1234;

    private static final String EXTRA_PREFIX = BuildConfig.APPLICATION_ID;
    private static final Object object = new Object();
    private static CBag cBag;
    private Bundle mCBagOptionsBundle;

    private CBag() {
        mCBagOptionsBundle = new Bundle();
    }

    /**
     * have to call the method before call start
     *
     */
    public static CBag getInstance() {
        if (cBag == null) {
            synchronized (object) {
                if (cBag == null) {
                    cBag = new CBag();
                }
            }
        }

        return cBag;
    }

    /**
     * start @{@link CBHomeActivity}
     *
     * @param activity
     */
    public CBag start(Activity activity) {
        activity.startActivity(new Intent(activity, CBHomeActivity.class));
        return this;
    }

    /**
     * start @{@link CBHomeActivity}
     *
     * @param fragment
     */
    public CBag start(Fragment fragment) {
        fragment.startActivity(new Intent(fragment.getActivity(), CBHomeActivity.class));
        return this;
    }

    public CBag withOption(Options option) {
        mCBagOptionsBundle.putAll(option.getOptionBundle());
        return this;
    }

    /**
     *
     */
    public static class Options{
        private static final String EXTRA_COLOR_PRIMARY = EXTRA_PREFIX + ".ColorPrimary";
        private static final String EXTRA_COLOR_PRIMARY_DARK = EXTRA_PREFIX + ".ColorPrimaryDark";

        private final Bundle mOptionBundle;

        public Options() {
            mOptionBundle = new Bundle();
        }

        @NonNull
        public Bundle getOptionBundle() {
            return mOptionBundle;
        }

        public void setColorPrimary(@ColorInt int colorPrimary) {
            mOptionBundle.putInt(EXTRA_COLOR_PRIMARY,colorPrimary);
        }

        public void setColorPrimaryDark(@ColorInt int colorPrimaryDark) {
            mOptionBundle.putInt(EXTRA_COLOR_PRIMARY_DARK,colorPrimaryDark);
        }
    }
}
