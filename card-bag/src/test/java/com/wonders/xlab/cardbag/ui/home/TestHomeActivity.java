package com.wonders.xlab.cardbag.ui.home;

import android.content.Intent;
import android.view.View;

import com.wonders.xlab.cardbag.BuildConfig;
import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyActivity;
import com.wonders.xlab.cardbag.ui.cardshow.CardShowActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import io.realm.Realm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hua on 16/8/18.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestHomeActivity {
    HomeActivity homeActivity;
    ShadowActivity shadowActivity;

    @Mock
    Realm mRealm;

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


}
