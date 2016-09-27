package com.wonders.xlab.cardbag.ui.cardmy;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.db.CBCardBagDB;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditActivity;
import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.wonders.xlab.cardbag.CustomMatcher.childAtPosition;
import static com.wonders.xlab.cardbag.CustomMatcher.linearLayoutParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
    public void testShowCardsInfoInBothListAndIconModeCorrect() {
        loadCards(20);

        launchActivity(false);

        int positionOfItemToCheck = 11;
        String nameOfItemToCheck = getCardName(positionOfItemToCheck - 1);

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(positionOfItemToCheck));
        onView(withText(nameOfItemToCheck))
                .check(matches(isDisplayed()));

        //switch to show the list type RecyclerView
        //and to check if the child show correct
        onView(withId(R.id.menu_card_my_list))
                .perform(click());

        onView(withId(R.id.recycler_view_list))
                .perform(RecyclerViewActions.scrollToPosition(positionOfItemToCheck));
        //the item of list type RecyclerView 's type is LinearLayout
        onView(allOf(withText(nameOfItemToCheck),
                linearLayoutParent()))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickItemThenGoToCardEditInIconMode() {
        loadCards(1);

        launchActivity(false);

        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, click()));
        intended(hasComponent(CardEditActivity.class.getName()));
    }

    @Test
    public void testClickItemThenGoToCardEditInListMode() {
        loadCards(1);

        launchActivity(false);

        onView(withId(R.id.menu_card_my_list))
                .perform(click());

        onView(withId(R.id.recycler_view_list))
                .perform(actionOnItemAtPosition(0, click()));
        intended(hasComponent(CardEditActivity.class.getName()));
    }

    @Test
    public void testLongClickToSelectThenPressBackToExitTheSelectMode() {
        loadCards(1);

        launchActivity(false);
        ViewInteraction viewInteraction = onView(withId(R.id.recycler_view));
        viewInteraction.perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withId(R.id.menu_card_my_delete)).check(matches(isDisplayed()));
        onView(withId(R.id.cb_card)).check(matches(isChecked()));
//        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        pressBack();
        try {
            onView(withId(R.id.menu_card_my_delete)).check(matches(not(isDisplayed())));
            fail("the delete menu should not be displayed");
        } catch (NoMatchingViewException ignored) {

        }
        onView(withId(R.id.cb_card)).check(matches(not(isChecked())));
    }

    @Test
    public void testLongClickToSelectAndThenDeleteOnCardMode() {
        int[] positionsToSelect = new int[]{0, 1, 5, 18};

        loadCards(20);
        launchActivity(false);

        ViewInteraction viewInteraction = onView(withId(R.id.recycler_view));
        viewInteraction.perform(RecyclerViewActions.actionOnItemAtPosition(positionsToSelect[0], longClick()));

        onView(withId(R.id.menu_card_my_delete)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_add)).check(matches(not(isDisplayed())));

        //except the first one
        for (int i = 1; i < positionsToSelect.length; i++) {
            viewInteraction.perform(RecyclerViewActions.actionOnItemAtPosition(positionsToSelect[i], click()));
        }
        for (int position : positionsToSelect) {
            viewInteraction.perform(RecyclerViewActions.scrollToPosition(position));
            onView(allOf(withId(R.id.cb_card), hasSibling(withText(getCardName(position)))))
                    .check(matches(isChecked()));
        }

        //delete all selected cards
        onView(withId(R.id.menu_card_my_delete))
                .perform(click());

        //check the toast
        onView(withText("删除" + positionsToSelect.length + "张卡片")).inRoot(withDecorView(not(is(mIntentsTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        //check if all selected card have been deleted
        for (int position : positionsToSelect) {
            try {
                onView(withId(R.id.recycler_view))
                        .perform(RecyclerViewActions.scrollToHolder(hasSameName(getCardName(position))));
                fail("all selected cards should be deleted");
            } catch (PerformException ignored) {

            }

        }
    }

    private static Matcher<CardMyIconRVAdapter.ItemViewHolder> hasSameName(final String name) {
        return new TypeSafeMatcher<CardMyIconRVAdapter.ItemViewHolder>() {
            @Override
            protected boolean matchesSafely(CardMyIconRVAdapter.ItemViewHolder item) {
                return item.hasSameName(name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has the same name");
            }
        };
    }

    @After
    public void cleanUp() {
        mCBCardBagDB.deleteAll();
    }

    private void loadCards(int counts) {
        mCBCardBagDB.deleteAll();

        for (int i = 0; i < counts; i++) {
            CardEntity cardEntity = new CardEntity();
            cardEntity.setId(getId(i));
            cardEntity.setCardName(getCardName(i));
            cardEntity.setImgUrl(IMAGE_URL);
            mCBCardBagDB.insertOrReplace(cardEntity);
        }
    }

    @NonNull
    private String getId(int i) {
        return String.valueOf(i);
    }

    /**
     * get the name of card
     *
     * @param position
     * @return
     */
    @NonNull
    private String getCardName(int position) {
        return "name" + position;
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
