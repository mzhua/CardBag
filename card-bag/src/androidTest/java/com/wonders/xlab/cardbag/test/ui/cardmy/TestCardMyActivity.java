package com.wonders.xlab.cardbag.test.ui.cardmy;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.data.CardModel;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.db.CBCardBagDB;
import com.wonders.xlab.cardbag.test.ToastChecker;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditActivity;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyActivity;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyContract;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyIconRVAdapter;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyPresenter;
import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchActivity;
import com.wonders.xlab.qrscanner.XQrScanner;
import com.wonders.xlab.qrscanner.XQrScannerActivity;
import com.yalantis.ucrop.UCropActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.VerificationModes.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.wonders.xlab.cardbag.test.CustomMatcher.childAtPosition;
import static com.wonders.xlab.cardbag.test.CustomMatcher.linearLayoutParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

/**
 * Created by hua on 16/9/27.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestCardMyActivity {
    @Rule
    public IntentsTestRule<CardMyActivity> mRule = new IntentsTestRule<>(CardMyActivity.class,true,false);

    private CBCardBagDB mCBCardBagDB;

    @Before
    public void setup() {
        mCBCardBagDB = CBCardBagDB.getInstance(InstrumentationRegistry.getTargetContext());
//        CardMyPresenter presenter = new CardMyPresenter(mock(CardModel.class), mock(CardMyContract.View.class));
//        mRule.getActivity().setPresenter(presenter);
    }

    @Test
    public void testEmptyData_ShowTips() {
        startActivity();
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

    @NonNull
    private List<CardEntity> getCardEntities() {
        List<CardEntity> cardEntities = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CardEntity entity = new CardEntity();
            entity.setId(String.valueOf(i));
            entity.setCardName(getCardName(i));
            entity.setImgUrl("http://ocg8s5zv8.bkt.clouddn.com/pic_vip_card.png");
            entity.setBarCode("12138" + i);

            cardEntities.add(entity);
        }
        return cardEntities;
    }

    @Test
    public void testClickBackNavigator_finishActivity() {
        startActivity();
        onView(withContentDescription(R.string.cb_action_bar_up_description)).perform(click());
        assertTrue("CardMyActivity should be finished after click navigation icon", mRule.getActivity().isFinishing());
    }

    @Test
    public void testDefaultShowCardViewAndSideBarStayHidden() {
        startActivity();
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
        startActivity();
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
        startActivity();
        onView(withId(R.id.iv_add))
                .perform(click());
        intended(hasComponent(CardSearchActivity.class.getName()));
    }

    @Test
    public void testShowCardsInfoInBothListAndIconModeCorrect() {
        for (int i = 0; i < 2; i++) {
            addCard(getCardName(i));
        }
        startActivity();
        int itemToCheckPosition = 1;
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

    private void startActivity() {
        mRule.launchActivity(null);
    }

    @Test
    public void testClickItem_goToCardEditInIconMode() {
        addCard("CardName");
        startActivity();
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, click()));
        intended(hasComponent(CardEditActivity.class.getName()));
    }

    @Test
    public void testClickItem_goToCardEditInListMode() {
        addCard("CardName");
        startActivity();
        onView(withId(R.id.menu_card_my_list))
                .perform(click());

        onView(withId(R.id.recycler_view_list))
                .perform(actionOnItemAtPosition(0, click()));
        intended(hasComponent(CardEditActivity.class.getName()));
    }

    @Test
    public void testLongClickToSelectThenPressBackToExitTheSelectMode() {
        addCard("CardName");
        startActivity();
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
        int[] positionsToSelect = new int[]{0, 1, 5, 8};
        int deleteCounts = positionsToSelect.length;
        int originCounts = 10;
        for (int i = 0; i < originCounts; i++) {
            addCard(getCardName(i));
        }
        startActivity();
        ViewInteraction viewInteraction = onView(withId(R.id.recycler_view));
        viewInteraction.perform(RecyclerViewActions.actionOnHolderItem(hasSameName(getCardName(positionsToSelect[0])), longClick()));

        onView(withId(R.id.menu_card_my_delete)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_add)).check(matches(not(isDisplayed())));

        //except the first one
        for (int i = 1; i < positionsToSelect.length; i++) {
            viewInteraction.perform(RecyclerViewActions.actionOnHolderItem(hasSameName(getCardName(positionsToSelect[i])), click()));
        }
        for (int position : positionsToSelect) {
            viewInteraction.perform(RecyclerViewActions.scrollToHolder(hasSameName(getCardName(position))));
            onView(allOf(withId(R.id.cb_card), hasSibling(withText(getCardName(position)))))
                    .check(matches(isChecked()));
        }

        //delete all selected cards
        onView(withId(R.id.menu_card_my_delete))
                .perform(click());

        //check the toast
        ToastChecker.checkToast("删除" + deleteCounts + "张卡片", mRule);

        //check if all selected card have been deleted
        RecyclerView recyclerView = (RecyclerView) mRule.getActivity().findViewById(R.id.recycler_view);
        assertEquals("the remain child counts of RecyclerView is incorrect", originCounts - deleteCounts, recyclerView.getChildCount());
        for (int position : positionsToSelect) {
            try {
                onView(withId(R.id.recycler_view))
                        .perform(RecyclerViewActions.scrollToHolder(hasSameName(getCardName(position))));
                fail("all selected cards should be deleted");
            } catch (PerformException ignored) {

            }

        }
    }

    @After
    public void cleanUp() {
        mCBCardBagDB.deleteAll();
    }

    private void addCard(String cardName) {
        CardEntity entity = new CardEntity();
        entity.setId(cardName);
        entity.setCardName(cardName);
        entity.setImgUrl("http://ocg8s5zv8.bkt.clouddn.com/pic_vip_card.png");
        entity.setBarCode(cardName);

        mCBCardBagDB.insertOrReplace(entity);

        /*onView(withId(R.id.iv_add)).perform(click());
        onView(withId(R.id.et_card_name)).perform(typeText(cardName), pressImeActionButton());
        onView(withText(R.string.cb_card_not_found_notice)).perform(click());
        if (TextUtils.isEmpty(cardName)) {
            onView(withId(R.id.et_card_name)).perform(typeText("CardName"), closeSoftKeyboard());
        }

        scanBarCode();

        takeCardFrontImg();

        onView(withId(R.id.menu_card_edit_save)).perform(click());*/
    }

    private void takeCardFrontImg() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, takePictureIntent));
        Intent cropIntent = new Intent();
        intending(hasComponent(UCropActivity.class.getName())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, cropIntent));

        onView(withId(R.id.iv_card_front)).perform(click());
    }

    private void scanBarCode() {
        String barCode = "12138";

        Intent scanIntent = new Intent();
        scanIntent.putExtra(XQrScanner.EXTRA_RESULT_BAR_OR_CODE_STRING, barCode);
        intending(hasComponent(XQrScannerActivity.class.getName())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, scanIntent));

        onView(withId(R.id.iv_bar_code)).perform(click());
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
