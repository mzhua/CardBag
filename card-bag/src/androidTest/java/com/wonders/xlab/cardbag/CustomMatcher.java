package com.wonders.xlab.cardbag;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by hua on 16/9/27.
 */

public class CustomMatcher {
    public static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ViewParent parent = item.getParent();

                return parent instanceof ViewGroup && parentMatcher.matches(parent) && item.equals(((ViewGroup) parent).getChildAt(position));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("child at position " + position + " in parent");
                parentMatcher.describeTo(description);
            }
        };
    }

    public static Matcher<View> linearLayoutParent() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ViewParent parent = item.getParent();
                return parent instanceof LinearLayout;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("the child of parent is LinearLayout");
            }
        };
    }

}
