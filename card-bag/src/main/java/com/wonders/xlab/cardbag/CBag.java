package com.wonders.xlab.cardbag;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.wonders.xlab.cardbag.ui.home.HomeActivity;

/**
 * Created by hua on 16/8/16.
 */
public class CBag {
    private static final String EXTRA_PREFIX = BuildConfig.APPLICATION_ID;
    private static CBag cBag;

    private Intent mCBagIntent;
    private Bundle mCBagOptionsBundle;

    private CBag() {
        mCBagOptionsBundle = new Bundle();
        mCBagIntent = new Intent();
    }

    /**
     * have to call the method before call start
     */
    public static CBag get() {
        if (cBag == null) {
            cBag = new CBag();
        }
        return cBag;
    }

    /**
     * start @{@link HomeActivity}
     *
     * @param activity
     */
    public CBag start(@NonNull Activity activity) {
        activity.startActivity(getIntent(activity));
        return this;
    }

    /**
     * start @{@link HomeActivity}
     *
     * @param fragment
     */
    public CBag start(@NonNull Context context, @NonNull Fragment fragment) {
        fragment.startActivity(getIntent(context));
        return this;
    }

    public CBag start(@NonNull Context context, @NonNull android.support.v4.app.Fragment fragment) {
        fragment.startActivity(getIntent(context));
        return this;
    }

    public CBag withOption(@NonNull Options option) {
        mCBagOptionsBundle.putAll(option.getOptionBundle());
        return this;
    }

    private Intent getIntent(Context context) {
        mCBagIntent = new Intent(context, HomeActivity.class);
        mCBagIntent.putExtras(mCBagOptionsBundle);
        return mCBagIntent;
    }

    /**
     *
     */
    public static class Options {
        private static final String EXTRA_COLOR_PRIMARY = EXTRA_PREFIX + ".ColorPrimary";
        private static final String EXTRA_COLOR_PRIMARY_DARK = EXTRA_PREFIX + ".ColorPrimaryDark";
        private static final String EXTRA_TOP_BAR_COLOR = EXTRA_PREFIX + ".TopBarColor";
        private static final String EXTRA_TOP_BAR_TITLE_GRAVITY = EXTRA_PREFIX + ".TopBarTitleGravity";
        private static final String EXTRA_TOP_BAR_TEXT_COLOR = EXTRA_PREFIX + ".TopBarTextColor";

        private final Bundle mOptionBundle;

        public Options() {
            mOptionBundle = new Bundle();
        }

        @NonNull
        public Bundle getOptionBundle() {
            return mOptionBundle;
        }

        public void setColorPrimary(@ColorInt int colorPrimary) {
            mOptionBundle.putInt(EXTRA_COLOR_PRIMARY, colorPrimary);
        }

        public void setColorPrimaryDark(@ColorInt int colorPrimaryDark) {
            mOptionBundle.putInt(EXTRA_COLOR_PRIMARY_DARK, colorPrimaryDark);
        }
    }
}
