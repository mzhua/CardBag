package com.wonders.xlab.cardbag.view;


import com.wonders.xlab.cardbag.BuildConfig;
import com.wonders.xlab.cardbag.ui.home.CBHomeActivity;
import com.wonders.xlab.cardbag.util.LogUtil;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

/**
 * Created by hua on 16/8/19.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21,constants = BuildConfig.class)
public class TestCBTopBar {
    CBHomeActivity homeActivity;
    ShadowActivity shadowActivity;

    @BeforeClass
    public static void init() {
        LogUtil.setForTest(true);
    }

    @Before
    public void setup() {
        homeActivity = Robolectric.setupActivity(CBHomeActivity.class);
        shadowActivity = Shadows.shadowOf(homeActivity);
    }

    @Test
    public void testCBTopBarTitleViewPosition() {
    }
}
