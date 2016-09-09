package com.wonders.xlab.cardbag.ui.cardshow;

import com.wonders.xlab.cardbag.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by hua on 16/9/9.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestCardShowActivity {

    @InjectMocks
    CardShowActivity mCardShowActivity;

    ShadowActivity mShadowActivity;

    @Before
    public void setup() {
        ActivityController<CardShowActivity> activityController = Robolectric.buildActivity(CardShowActivity.class);
        mCardShowActivity = activityController.get();
        mShadowActivity = Shadows.shadowOf(mCardShowActivity);
        initMocks(this);
//        when(mCardShowActivity.getPresenter()).thenReturn(mCardModel);
        activityController.setup();
    }

    @Test
    public void testNoDate() {
        verify(mCardShowActivity, times(1)).getPresenter();
    }
}
