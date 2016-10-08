package com.wonders.xlab.cardbag.test.ui.cardsearch;

import android.content.Context;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.inputmethod.InputMethodManager;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertTrue;

/**
 * Created by hua on 16/9/29.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestCardSearchActivity {
    @Rule
    public IntentsTestRule<CardSearchActivity> mRule = new IntentsTestRule<>(CardSearchActivity.class);

    @Test
    public void testInitialViewStatus() {
        onView(withId(R.id.toolbar_title)).check(matches(allOf(isDisplayed(), withText(mRule.getActivity().getResources().getString(R.string.cb_title_card_search)))));
        onView(withId(R.id.et_card_name)).check(matches(allOf(isDisplayed(), withHint(mRule.getActivity().getResources().getString(R.string.hint_et_search_card_name)))));
        InputMethodManager im = (InputMethodManager) mRule.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        assertTrue(im.isActive());
    }

    @Test
    public void testClickBackNavigator_finishActivity() {
        onView(withContentDescription(R.string.cb_action_bar_up_description)).perform(click());
        assertTrue("CardSearchActivity should be finished after click navigation icon",mRule.getActivity().isFinishing());
    }

    @Test
    public void testClickImeAction_showTipCard() {
        onView(withId(R.id.et_card_name)).perform(pressImeActionButton());
        onView(withText(R.string.cb_card_not_found_notice)).check(matches(isDisplayed()));
    }
}