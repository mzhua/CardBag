package com.wonders.xlab.cardbag.ui.cardmy;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.db.CBCardBagDB;
import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by hua on 16/9/27.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestCardMyActivity {
    public static final String IMAGE_URL = "http://img.ivsky.com/img/bizhi/img/201105/15/lamborghini_reventon-001.jpg";
    @Rule
    public IntentsTestRule<CardMyActivity> mIntentsTestRule = new IntentsTestRule<>(CardMyActivity.class);

    private CBCardBagDB mCBCardBagDB;

    @Before
    public void setup() {
        mCBCardBagDB = CBCardBagDB.getInstance(InstrumentationRegistry.getContext());
    }

    @Test
    public void testDefaultShowCardViewAndSideBarStayHidden() {
        onView(withId(R.id.menu_card_my_list))
                .check(matches(isDisplayed()));
        onView(withId(R.id.side_bar))
                .check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_view))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view_list))
                .check(matches(not(isDisplayed())));
        onView(withText(R.string.cb_tip_no_card))
                .check(matches(isDisplayed()));
        onView(allOf(withText(R.string.cb_tip_no_card),
                childAtPosition(childAtPosition(withId(R.id.recycler_view), 0), 0)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickListMenuThenShowListRecyclerViewAndShowSideBar() {
        onView(withId(R.id.menu_card_my_list))
                .perform(click());
        onView(withId(R.id.side_bar))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view))
                .check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_view_list))
                .check(matches(isDisplayed()));
        onView(allOf(withText(R.string.cb_tip_no_card),
                childAtPosition(childAtPosition(withId(R.id.recycler_view_list), 0), 0)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickAddFABThenGoToCardSearch() {
        onView(withId(R.id.iv_add))
                .perform(click());
        intended(hasComponent(CardSearchActivity.class.getName()));
    }

    @Ignore
    @Test
    public void testClickItemThenGoToCardEdit() {
        CardEntity cardEntity = new CardEntity();
        cardEntity.setId("1");
        cardEntity.setCardName("name");
        cardEntity.setImgUrl(IMAGE_URL);
        mCBCardBagDB.insertOrReplace(cardEntity);

        onView(withId(R.id.iv_add))
                .perform(click());

        pressBack();

        onView(withText("name"))
                .check(matches(isDisplayed()));
    }

    @After
    public void cleanUp() {
        mCBCardBagDB.deleteAll();
    }

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
}
