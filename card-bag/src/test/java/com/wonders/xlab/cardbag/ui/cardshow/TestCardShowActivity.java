package com.wonders.xlab.cardbag.ui.cardshow;

import android.support.v4.view.ViewPager;

import com.wonders.xlab.cardbag.BuildConfig;
import com.wonders.xlab.cardbag.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by hua on 16/9/9.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestCardShowActivity {

    CardShowActivity mCardShowActivity;

    ShadowActivity mShadowActivity;

    @Before
    public void setup() {
        initMocks(this);
        mCardShowActivity = Robolectric.setupActivity(CardShowActivity.class);
        mShadowActivity = Shadows.shadowOf(mCardShowActivity);
    }

    @Test
    public void testNoDate() {
        ViewPager viewPager = (ViewPager) mCardShowActivity.findViewById(R.id.viewPager);
        assertNotNull(viewPager);
        assertEquals("the viewpager should be empty", viewPager.getChildCount(), 0);
        assertEquals(ShadowToast.getTextOfLatestToast(), ShadowApplication.getInstance().getApplicationContext().getResources().getString(R.string.cb_card_show_no_card_data_notice));
        assertNull(mShadowActivity.getOptionsMenu());
    }
}
