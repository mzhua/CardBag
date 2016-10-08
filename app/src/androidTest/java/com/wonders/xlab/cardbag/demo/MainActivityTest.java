package com.wonders.xlab.cardbag.demo;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn1), withText("卡包"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.btn_use_card), withContentDescription("使用卡片"),
                        withParent(allOf(withId(R.id.cb_home_activity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("转到上一层级"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.xtbl)))),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.btn_manage_card), withContentDescription("管理卡片"),
                        withParent(allOf(withId(R.id.cb_home_activity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_card_my_list), withContentDescription("列表"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.menu_card_my_icon), withContentDescription("图标"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.iv_add), isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.et_card_name), isDisplayed()));
        appCompatEditText.perform(pressImeActionButton());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view),
                        withParent(allOf(withId(R.id.card_search_activity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.menu_card_edit_save), withText("完成"), isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction ratioImageView = onView(
                allOf(withId(R.id.iv_bar_code), isDisplayed()));
        ratioImageView.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.et_card_name), isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.et_card_name), isDisplayed()));
        appCompatEditText3.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.menu_card_edit_save), withText("完成"), isDisplayed()));
        actionMenuItemView4.perform(click());

        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.menu_card_my_list), withContentDescription("列表"), isDisplayed()));
        actionMenuItemView5.perform(click());

        ViewInteraction actionMenuItemView6 = onView(
                allOf(withId(R.id.menu_card_my_icon), withContentDescription("图标"), isDisplayed()));
        actionMenuItemView6.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("转到上一层级"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.xtbl)))),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.btn_use_card), withContentDescription("使用卡片"),
                        withParent(allOf(withId(R.id.cb_home_activity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction actionMenuItemView7 = onView(
                allOf(withId(R.id.menu_card_show_edit), withContentDescription("编辑"), isDisplayed()));
        actionMenuItemView7.perform(click());

        ViewInteraction ratioImageView3 = onView(
                allOf(withId(R.id.iv_card_back), isDisplayed()));
        ratioImageView3.perform(click());

        ViewInteraction actionMenuItemView8 = onView(
                allOf(withId(R.id.menu_crop), withContentDescription("裁剪"), isDisplayed()));
        actionMenuItemView8.perform(click());

        ViewInteraction ratioImageView4 = onView(
                allOf(withId(R.id.iv_card_front), isDisplayed()));
        ratioImageView4.perform(click());

        ViewInteraction actionMenuItemView9 = onView(
                allOf(withId(R.id.menu_crop), withContentDescription("裁剪"), isDisplayed()));
        actionMenuItemView9.perform(click());

        ViewInteraction actionMenuItemView10 = onView(
                allOf(withId(R.id.menu_card_edit_save), withText("完成"), isDisplayed()));
        actionMenuItemView10.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withId(R.id.btn_manage_card), withContentDescription("管理卡片"),
                        withParent(allOf(withId(R.id.cb_home_activity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.iv_add), isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.et_card_name), isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recycler_view),
                        withParent(allOf(withId(R.id.card_search_activity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.et_card_name), isDisplayed()));
        appCompatEditText5.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.et_card_name), isDisplayed()));
        appCompatEditText6.perform(replaceText("ok"), closeSoftKeyboard());

        ViewInteraction ratioImageView5 = onView(
                allOf(withId(R.id.iv_bar_code), isDisplayed()));
        ratioImageView5.perform(click());

        ViewInteraction ratioImageView6 = onView(
                allOf(withId(R.id.iv_card_front), isDisplayed()));
        ratioImageView6.perform(click());

        ViewInteraction actionMenuItemView11 = onView(
                allOf(withId(R.id.menu_crop), withContentDescription("裁剪"), isDisplayed()));
        actionMenuItemView11.perform(click());

        ViewInteraction actionMenuItemView12 = onView(
                allOf(withId(R.id.menu_card_edit_save), withText("完成"), isDisplayed()));
        actionMenuItemView12.perform(click());

        ViewInteraction actionMenuItemView13 = onView(
                allOf(withId(R.id.menu_card_my_list), withContentDescription("列表"), isDisplayed()));
        actionMenuItemView13.perform(click());

        ViewInteraction actionMenuItemView14 = onView(
                allOf(withId(R.id.menu_card_my_icon), withContentDescription("图标"), isDisplayed()));
        actionMenuItemView14.perform(longClick());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.recycler_view_list), isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withContentDescription("转到上一层级"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.xtbl)))),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withContentDescription("转到上一层级"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.xtbl)))),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        ViewInteraction appCompatImageButton9 = onView(
                allOf(withId(R.id.btn_use_card), withContentDescription("使用卡片"),
                        withParent(allOf(withId(R.id.cb_home_activity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatImageButton9.perform(click());

        ViewInteraction actionMenuItemView15 = onView(
                allOf(withId(R.id.menu_card_show_edit), withContentDescription("编辑"), isDisplayed()));
        actionMenuItemView15.perform(click());

        ViewInteraction appCompatImageButton10 = onView(
                allOf(withContentDescription("转到上一层级"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.xtbl)))),
                        isDisplayed()));
        appCompatImageButton10.perform(click());

        ViewInteraction appCompatImageButton11 = onView(
                allOf(withContentDescription("转到上一层级"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.xtbl)))),
                        isDisplayed()));
        appCompatImageButton11.perform(click());

        ViewInteraction appCompatImageButton12 = onView(
                allOf(withId(R.id.btn_manage_card), withContentDescription("管理卡片"),
                        withParent(allOf(withId(R.id.cb_home_activity),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatImageButton12.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.tv_name), withText("test"), isDisplayed()));
        appCompatTextView.perform(longClick());

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.recycler_view), isDisplayed()));
        recyclerView4.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction actionMenuItemView16 = onView(
                allOf(withId(R.id.menu_card_my_delete), withContentDescription("删除"), isDisplayed()));
        actionMenuItemView16.perform(click());

        pressBack();

    }

}
