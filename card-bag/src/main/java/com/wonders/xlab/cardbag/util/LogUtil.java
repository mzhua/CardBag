package com.wonders.xlab.cardbag.util;

import android.util.Log;

import com.wonders.xlab.cardbag.BuildConfig;

/**
 * Created by hua on 16/8/19.
 */
public class LogUtil {
    private static boolean mIsForTest = false;

    public static void setForTest(boolean mIsForTest) {
        LogUtil.mIsForTest = mIsForTest;
    }

    public static void debug(String tag, String message) {
        if (BuildConfig.DEBUG) {
            if (mIsForTest) {
                System.out.print(message);
            } else {
                Log.d(tag, message);
            }
        }
    }

    public static void warning(String tag, String message) {
        if (BuildConfig.DEBUG) {
            if (mIsForTest) {
                System.err.print(message);
            } else {
                Log.w(tag, message);
            }
        }
    }

    public static void error(String tag, String message) {
        if (BuildConfig.DEBUG) {
            if (mIsForTest) {
                System.err.print(message);
            } else {
                Log.e(tag, message);
            }
        }
    }

    public static void wtf(String tag, String message) {
        if (BuildConfig.DEBUG) {
            if (mIsForTest) {
                System.out.print(message);
            } else {
                Log.wtf(tag, message);
            }
        }
    }
}
