package com.wonders.xlab.cardbag.test.ui.home;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.test.OrientationUtil;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyActivity;
import com.wonders.xlab.cardbag.ui.cardshow.CardShowActivity;
import com.wonders.xlab.cardbag.ui.home.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by hua on 16/9/29.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestHomeActivity {
    @Rule
    public IntentsTestRule<HomeActivity> mRule = new IntentsTestRule<>(HomeActivity.class);

    @Test
    public void initialCheck() {
        onView(withId(R.id.btn_use_card)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_manage_card)).check(matches(isDisplayed()));
    }

    @Test
    public void clickUseCard_goCardShow() {
        onView(withId(R.id.btn_use_card)).perform(click());
        intended(hasComponent(CardShowActivity.class.getName()));
    }

    @Test
    public void clickUseManage_goCardMy() {
        onView(withId(R.id.btn_manage_card)).perform(click());
        intended(hasComponent(CardMyActivity.class.getName()));
    }

    @Test
    public void orientationChange_showHorizontalView() {
        OrientationUtil.rotateToLandscape(mRule);
        onView(withId(R.id.btn_use_card)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_manage_card)).check(matches(isDisplayed()));
//        LinearLayout parent = (LinearLayout) mRule.getActivity().findViewById(R.id.btn_use_card).getParent();
//        assertEquals(LinearLayout.HORIZONTAL, parent.getOrientation());
        OrientationUtil.rotateToPortrait(mRule);
    }

}
