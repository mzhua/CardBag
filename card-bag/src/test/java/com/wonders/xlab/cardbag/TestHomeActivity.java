/*
package com.wonders.xlab.cardbag;

import com.wonders.xlab.cardbag.ui.home.HomeActivity;
import com.wonders.xlab.cardbag.util.LogUtil;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

*/
/**
 * Created by hua on 16/8/18.
 *//*

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestHomeActivity {
    HomeActivity homeActivity;
    ShadowActivity shadowActivity;

    @Before
    public void setup() {
        LogUtil.setForTest(true);
        homeActivity = Robolectric.setupActivity(HomeActivity.class);
        shadowActivity = Shadows.shadowOf(homeActivity);
    }
    */
/*@Test
    public void testContactsBtnClick() {
        View view = homeActivity.findViewById(R.id.btn_contacts);
        assertNotNull(view);
        view.performClick();

        Intent expectedIntent = new Intent(homeActivity, XContactsActivity.class);
        Intent nextStartedActivity = shadowActivity.getNextStartedActivity();
        assertEquals("start wrong activity",expectedIntent.getComponent(), nextStartedActivity.getComponent());
    }

    @Test
    public void testQrScannerBtnClick() {
        View view = homeActivity.findViewById(R.id.btn_scan);
        assertNotNull(view);
        view.performClick();

        Intent expectedIntent = new Intent(homeActivity, XQrScannerActivity.class);
        Intent nextStartedActivity = shadowActivity.getNextStartedActivity();
        assertEquals("start wrong activity",expectedIntent.getComponent(), nextStartedActivity.getComponent());
    }*//*


}
*/
