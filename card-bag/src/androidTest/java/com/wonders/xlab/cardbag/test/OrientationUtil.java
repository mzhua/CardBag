package com.wonders.xlab.cardbag.test;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.rule.ActivityTestRule;

/**
 * Created by hua on 16/9/29.
 */

public class OrientationUtil {
    private static void rotateToLandscape(ActivityTestRule<? extends Activity> activityTestRule) {
        activityTestRule.getActivity()
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private static void rotateToPortrait(ActivityTestRule<? extends Activity> activityTestRule) {
        activityTestRule.getActivity()
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static void rotateOrientation(ActivityTestRule<? extends Activity> activityTestRule) {
        int currentOrientation =
                activityTestRule.getActivity().getResources().getConfiguration().orientation;

        switch (currentOrientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                rotateToPortrait(activityTestRule);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                rotateToLandscape(activityTestRule);
                break;
            default:
                rotateToLandscape(activityTestRule);
        }
    }
}
