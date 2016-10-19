package com.wonders.xlab.cardbag.test.ui.cardshow;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;

import com.wonders.xlab.cardbag.CBag;
import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.db.CBCardBagDB;
import com.wonders.xlab.cardbag.test.ToastChecker;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditActivity;
import com.wonders.xlab.cardbag.ui.cardshow.CardShowActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hua on 2016/10/8.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestCardShowActivity {

    private static final String DEFAULT_PREFIX_BAR_CODE = "12138";
    private static final String DEFAULT_PREFIX_CARD_NAME = "CardName";

    @Rule
    public IntentsTestRule<CardShowActivity> mRule = new IntentsTestRule<>(CardShowActivity.class, true, false);

    private CBCardBagDB mDB;

    @Before
    public void setup() {
        mDB = CBCardBagDB.getInstance(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void testNoData_showToast_emptyViewPager() {
        mRule.launchActivity(null);
        onView(withText(R.string.cb_title_card_show)).check(matches(isDisplayed()));
        String emptyStr = "";
        onView(withId(R.id.tv_card_name)).check(matches(allOf(isDisplayed(), withText(emptyStr))));
        onView(withId(R.id.menu_card_show_edit)).check(doesNotExist());
        ToastChecker.checkToast(R.string.cb_card_show_no_card_data_notice, mRule);
        checkViewPagerChildCount(0);
    }

    private void checkViewPagerChildCount(int expected) {
        ViewPager vp = (ViewPager) mRule.getActivity().findViewById(R.id.viewPager);
        assertEquals("the child counts of ViewPager should be " + expected, expected, vp.getChildCount());
    }

    @Test
    public void testShowSingleCardInfo_clickEditMenu_goToEditActivity() {
        int cardCounts = 1;
        addCardsAndThenStartActivity(cardCounts);

        int positionToCheck = 0;
        onView(withId(R.id.tv_card_name)).check(matches(allOf(isDisplayed(), withText(getCardNameByIndex(positionToCheck)))));
        onView(allOf(withId(R.id.tv_bar_code),withText(getBarCodeByIndex(positionToCheck)))).check(matches(isDisplayed()));

        checkViewPagerChildCount(cardCounts);

        onView(allOf(withId(R.id.menu_card_show_edit),withContentDescription(R.string.cb_menu_card_show_edit)))
                .perform(click());
        intended(allOf(hasComponent(CardEditActivity.class.getName()),
                hasExtra(equalTo("data"), equalTo(addNewCardData(0)))));
    }

    @Test
    public void testSwipeToShowMore() throws InterruptedException {
        int cardCounts = 3;
        addCardsAndThenStartActivity(cardCounts);

        onView(withId(R.id.viewPager)).perform(swipeLeft());

        int positionToCheck = 1;
        onView(withId(R.id.tv_card_name)).check(matches(allOf(isDisplayed(), withText(getCardNameByIndex(positionToCheck)))));
        onView(allOf(withId(R.id.tv_bar_code),withText(getBarCodeByIndex(positionToCheck)))).check(matches(isDisplayed()));

        checkViewPagerChildCount(cardCounts);
    }

    private void addCardsAndThenStartActivity(int counts) {
        for (int i = 0; i < counts; i++) {
            addNewCardData(i);
        }
        mRule.launchActivity(null);
    }

    private CardEntity addNewCardData(int index) {
        String id = String.valueOf(index);
        String cardName = getCardNameByIndex(index);
        String barCode = getBarCodeByIndex(index);

        assertNotNull("you must set card id", id);

        CardEntity entity = new CardEntity();
        entity.setId(id);
        entity.setCardName(cardName);
        entity.setImgUrl(CBag.get().getCardImgUrlDefault());
        entity.setBarCode(barCode);
        mDB.insertOrReplace(entity);

        return entity;
    }

    @NonNull
    private String getBarCodeByIndex(int index) {
        return DEFAULT_PREFIX_BAR_CODE + index;
    }

    @NonNull
    private String getCardNameByIndex(int index) {
        return DEFAULT_PREFIX_CARD_NAME + index;
    }

    @After
    public void cleanUp() {
        mDB.deleteAll();
    }
}
