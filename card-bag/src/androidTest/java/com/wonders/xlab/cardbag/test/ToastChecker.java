package com.wonders.xlab.cardbag.test;

import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by hua on 16/9/29.
 */

public class ToastChecker {
    public static void checkToast(String message, ActivityTestRule rule) {
        onView(withText(message)).inRoot(withDecorView(not(is(rule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    public static void checkToast(int resourceId, ActivityTestRule rule) {
        onView(withText(resourceId)).inRoot(withDecorView(not(is(rule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
