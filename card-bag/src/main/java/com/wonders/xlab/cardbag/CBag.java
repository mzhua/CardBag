package com.wonders.xlab.cardbag;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.wonders.xlab.cardbag.ui.home.HomeActivity;
import com.wonders.xlab.cardbag.widget.TopBar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
        public static final String EXTRA_TOP_BAR_COLOR = EXTRA_PREFIX + ".TopBarColor";
        public static final String EXTRA_TOP_BAR_WIDGET_COLOR = EXTRA_PREFIX + ".TopBarWidgetColor";
        public static final String EXTRA_TOP_BAR_TITLE_GRAVITY = EXTRA_PREFIX + ".TopBarTitleGravity";
        public static final String EXTRA_TOP_BAR_TEXT_COLOR = EXTRA_PREFIX + ".TopBarTextColor";

        private final Bundle mOptionBundle;

        private final int GRAVITY_TITLE_MASK = 1;
        private final int GRAVITY_TITLE_LEFT = GRAVITY_TITLE_MASK << 1;
        private final int GRAVITY_TITLE_CENTER = GRAVITY_TITLE_MASK << 2;

        @Retention(RetentionPolicy.SOURCE)
        @IntDef({GRAVITY_TITLE_LEFT, GRAVITY_TITLE_CENTER})
        public @interface TitleGravity {
        }

        public Options() {
            mOptionBundle = new Bundle();
        }

        @NonNull
        public Bundle getOptionBundle() {
            return mOptionBundle;
        }

        public void setTopBarColor(@ColorInt int topBarColor) {
            mOptionBundle.putInt(EXTRA_TOP_BAR_COLOR, topBarColor);
        }

        /**
         * color of menu and navigation icon
         * @param topBarWidgetColor
         */
        public void setTopBarWidgetColor(@ColorInt int topBarWidgetColor) {
            mOptionBundle.putInt(EXTRA_TOP_BAR_WIDGET_COLOR, topBarWidgetColor);
        }

        public void setTopBarTitleGravity(@TitleGravity int gravity) {
            mOptionBundle.putInt(EXTRA_TOP_BAR_TITLE_GRAVITY, gravity);
        }

        public void setTopBarTextColor(@ColorInt int color) {
            mOptionBundle.putInt(EXTRA_TOP_BAR_TEXT_COLOR, color);
        }
    }
}
