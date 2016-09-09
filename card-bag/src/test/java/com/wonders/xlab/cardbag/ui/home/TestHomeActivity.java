package com.wonders.xlab.cardbag.ui.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.wonders.xlab.cardbag.BuildConfig;
import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyActivity;
import com.wonders.xlab.cardbag.ui.cardshow.CardShowActivity;
import com.wonders.xlab.cardbag.widget.XToolBarLayout;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by hua on 16/8/18.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestHomeActivity {
    HomeActivity homeActivity;
    ShadowActivity shadowActivity;

    @Before
    public void setup() {
        homeActivity = Robolectric.setupActivity(HomeActivity.class);
        shadowActivity = Shadows.shadowOf(homeActivity);
    }

    @Test
    public void testUseCardClick() {
        View view = homeActivity.findViewById(R.id.btn_use_card);
        assertNotNull(view);
        view.performClick();

        Intent expectedIntent = new Intent(homeActivity, CardShowActivity.class);
        Intent nextStartedActivity = shadowActivity.getNextStartedActivity();
        assertEquals("start wrong activity", expectedIntent.getComponent(), nextStartedActivity.getComponent());
    }

    @Test
    public void testManageCardClick() {
        View view = homeActivity.findViewById(R.id.btn_manage_card);
        assertNotNull(view);
        view.performClick();

        Intent expectedIntent = new Intent(homeActivity, CardMyActivity.class);
        Intent nextStartedActivity = shadowActivity.getNextStartedActivity();
        assertEquals("start wrong activity", expectedIntent.getComponent(), nextStartedActivity.getComponent());
    }

    @Test
    public void testClickNavigationThenFinishActivity() {
        Toolbar toolbar = ((XToolBarLayout) homeActivity.findViewById(R.id.xtbl)).getToolbar();
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View childAt = toolbar.getChildAt(i);
            if (childAt instanceof AppCompatImageButton) {
                childAt.performClick();
                break;
            }
        }
        assertTrue(shadowActivity.isFinishing());
    }

    @Test
    @Ignore
    public void testChangeOrientationToLandscape() {
        // toggle orientation
        homeActivity.getResources().getConfiguration().orientation = Configuration.ORIENTATION_LANDSCAPE;
        LinearLayout linearLayout = (LinearLayout) homeActivity.findViewById(R.id.cb_home_activity);
        assertNotNull(linearLayout);

        assertEquals(linearLayout.getOrientation(), LinearLayout.HORIZONTAL);
    }
}
