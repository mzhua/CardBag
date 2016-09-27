package com.wonders.xlab.cardbag.ui.cardmy;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.db.CBCardBagDB;
import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.wonders.xlab.cardbag.CustomMatcher.childAtPosition;
import static com.wonders.xlab.cardbag.CustomMatcher.linearLayoutParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hua on 16/9/27.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestCardMyActivity {
    public static final String IMAGE_URL = "http://img.ivsky.com/img/bizhi/img/201105/15/lamborghini_reventon-001.jpg";
    @Rule
    public IntentsTestRule<CardMyActivity> mIntentsTestRule = new IntentsTestRule<>(CardMyActivity.class, true, false);

    private CBCardBagDB mCBCardBagDB;

    @Before
    public void setup() {
        mCBCardBagDB = CBCardBagDB.getInstance(InstrumentationRegistry.getContext());
    }

    @Test
    public void testDefaultShowCardViewAndSideBarStayHidden() {
        launchActivity(true);
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
        launchActivity(true);
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
        launchActivity(true);
        onView(withId(R.id.iv_add))
                .perform(click());
        intended(hasComponent(CardSearchActivity.class.getName()));
    }

    @Test
    public void testShowCardsInfoCorrect() {
        loadCards(20);

        launchActivity(false);

        int positionOfItemToCheck = 11;
        String nameOfIemToCheck = "name" + (positionOfItemToCheck - 1);

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(positionOfItemToCheck));
        onView(withText(nameOfIemToCheck))
                .check(matches(isDisplayed()));

        //switch to show the list type RecyclerView
        //and to check if the child show correct
        onView(withId(R.id.menu_card_my_list))
                .perform(click());

        onView(withId(R.id.recycler_view_list))
                .perform(RecyclerViewActions.scrollToPosition(positionOfItemToCheck));
        //the item of list type RecyclerView 's type is LinearLayout
        onView(allOf(withText(nameOfIemToCheck),
                linearLayoutParent()))
                .check(matches(isDisplayed()));
    }

    private void loadCards(int counts) {
        mCBCardBagDB.deleteAll();

        for (int i = 0; i < counts; i++) {
            CardEntity cardEntity = new CardEntity();
            cardEntity.setId("" + i);
            cardEntity.setCardName("name" + i);
            cardEntity.setImgUrl(IMAGE_URL);
            mCBCardBagDB.insertOrReplace(cardEntity);
        }
    }

    @Test
    public void testLongClickToSelectAndThenDelete() {
        int[] positionsToSelect = new int[]{0, 1, 5,18};

        loadCards(20);
        launchActivity(false);

        ViewInteraction viewInteraction = onView(withId(R.id.recycler_view));
        viewInteraction.perform(RecyclerViewActions.actionOnItemAtPosition(positionsToSelect[0], longClick()));

        onView(withId(R.id.menu_card_my_delete)).check(matches(isDisplayed()));

        //except the first one
        for (int i = 1; i < positionsToSelect.length; i++) {
            viewInteraction.perform(RecyclerViewActions.actionOnItemAtPosition(positionsToSelect[i], click()));
        }

    }

    @After
    public void cleanUp() {
        mCBCardBagDB.deleteAll();
    }

    private void launchActivity(boolean shouldCleanDataBeforeLaunch) {
        if (shouldCleanDataBeforeLaunch) {
            assertNotNull(mCBCardBagDB);
            mCBCardBagDB.deleteAll();
        }
        assertNotNull(mIntentsTestRule);
        mIntentsTestRule.launchActivity(null);
    }
}
