package com.wonders.xlab.cardbag.test.ui.cardmy;

import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.db.CBCardBagDB;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditActivity;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyActivity;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyIconRVAdapter;
import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.wonders.xlab.cardbag.test.CustomMatcher.childAtPosition;
import static com.wonders.xlab.cardbag.test.CustomMatcher.linearLayoutParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by hua on 16/9/27.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestCardMyActivity {
    @Rule
    public IntentsTestRule<CardMyActivity> mRule = new IntentsTestRule<>(CardMyActivity.class, true, false);

    @Mock
    public CBCardBagDB mDb;

    @Before
    public void setup() {
        initMocks(this);
        CBCardBagDB.setInstance(mDb);
    }

    private void launchActivity() {
        mRule.launchActivity(null);
    }

    @Test
    public void testDefaultView() {
        launchActivity();

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
    public void testClickListMenu_showListRecyclerView_showSideBar() {
        launchActivity();

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
    public void testClickAddFAB_goToCardSearch() {
        launchActivity();

        onView(withId(R.id.iv_add))
                .perform(click());
        intended(hasComponent(CardSearchActivity.class.getName()));
    }

    @Test
    public void testShowCardsPendingOverScreen() {
        int cardCounts = setupCardListAndLaunchActivity(25);

        //remember to reverse the index
        for (int i = 0; i < cardCounts; i++) {
            int positionInAdapter = cardCounts - i - 1;
            onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(positionInAdapter));//must scroll to the item first, make sure the item is displayed on screen
            onView(allOf(withId(R.id.tv_name), withText(getCardName(positionInAdapter)))).check(matches(isDisplayed()));
        }
    }

    @Test
    public void testShowCardsInfoInBothListAndIconModeCorrect() {
        int cardCounts = setupCardListAndLaunchActivity(2);

        int itemToCheckPosition = cardCounts - 1;
        String nameOfItemToCheck = getCardName(itemToCheckPosition);

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(itemToCheckPosition));
        onView(withText(nameOfItemToCheck))
                .check(matches(isDisplayed()));

        //switch to show the list type RecyclerView
        //and to check if the child show correct
        onView(withId(R.id.menu_card_my_list))
                .perform(click());

        onView(withId(R.id.recycler_view_list))
                .perform(RecyclerViewActions.scrollToPosition(itemToCheckPosition));
        //the item of list type RecyclerView 's type is LinearLayout
        onView(allOf(withText(nameOfItemToCheck),
                linearLayoutParent()))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickItem_goToCardEditInIconMode() {
        setupCardListAndLaunchActivity(1);

        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, click()));
        intended(hasComponent(CardEditActivity.class.getName()));
    }

    @Test
    public void testClickItem_goToCardEditInListMode() {
        setupCardListAndLaunchActivity(1);

        onView(withId(R.id.menu_card_my_list))
                .perform(click());

        onView(withId(R.id.recycler_view_list))
                .perform(actionOnItemAtPosition(0, click()));
        intended(hasComponent(CardEditActivity.class.getName()));
    }

    @Test
    public void testLongClickToSelectThenPressBackToExitTheSelectMode() {
        setupCardListAndLaunchActivity(1);

        ViewInteraction viewInteraction = onView(withId(R.id.recycler_view));
        viewInteraction.perform(actionOnItemAtPosition(0, longClick()));

        onView(withId(R.id.menu_card_my_delete)).check(matches(isDisplayed()));
        onView(withId(R.id.cb_card)).check(matches(isChecked()));
        onView(withContentDescription(R.string.cb_action_bar_up_description)).perform(click());
        onView(withId(R.id.menu_card_my_delete)).check(doesNotExist());
        onView(withId(R.id.cb_card)).check(matches(not(isChecked())));
    }

    @Test
    public void testLongClickToSelectAndThenDeleteOnCardMode() {

    }

    private int setupCardListAndLaunchActivity(int counts) {
        when(mDb.queryAllOrderByCreateDateDesc()).thenReturn(setupCardList(counts));
        launchActivity();
        return counts;
    }

    @NonNull
    private List<CardEntity> setupCardList(int counts) {
        List<CardEntity> cardEntities = new ArrayList<>();
        for (int i = 0; i < counts; i++) {
            CardEntity entity = new CardEntity();
            entity.setId(String.valueOf(i));
            entity.setCardName(getCardName(i));
            entity.setImgUrl("http://ocg8s5zv8.bkt.clouddn.com/pic_vip_card.png");
            entity.setBarCode("12138" + i);

            cardEntities.add(entity);
        }
        return cardEntities;
    }

    private static Matcher<CardMyIconRVAdapter.ItemViewHolder> hasSameName(final String name) {
        return new TypeSafeMatcher<CardMyIconRVAdapter.ItemViewHolder>() {
            @Override
            protected boolean matchesSafely(CardMyIconRVAdapter.ItemViewHolder item) {
                return item.hasSameName(name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(name);
            }
        };
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

}
