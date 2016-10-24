package com.wonders.xlab.cardbag.test.ui.cardedit;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.wonders.xlab.cardbag.CBag;
import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.test.ToastChecker;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditActivity;
import com.wonders.xlab.cardbag.ui.scanner.CBScannerActivity;
import com.yalantis.ucrop.UCropActivity;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.yalantis.ucrop.UCrop.EXTRA_INPUT_URI;
import static com.yalantis.ucrop.UCrop.EXTRA_OUTPUT_URI;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

/**
 * Created by hua on 16/9/29.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestCardEditActivity {
    @Rule
    public IntentsTestRule<CardEditActivity> mRule = new IntentsTestRule<>(CardEditActivity.class, true, false);

    private void launchActivity(Intent intent) {

        mRule.launchActivity(intent);
    }

    @NonNull
    private Intent getIntent(String cardName, String barCode) {
        Intent intent = new Intent();
        CardEntity bean = new CardEntity();
        bean.setImgUrl(CBag.get().getCardImgUrlDefault());
        bean.setCardName(cardName);
        bean.setBarCode(barCode);
        intent.putExtra("data", bean);
        return intent;
    }

    @Test
    public void testClickBackNavigator_finishActivity() {
        launchActivity(null);
        onView(withContentDescription(R.string.cb_action_bar_up_description)).perform(click());
        assertTrue("CardEditActivity should be finished after click navigation icon", mRule.getActivity().isFinishing());
    }

    @Test
    public void launchWithoutCardName_addMode() {
        launchActivity(getIntent(null, null));
        checkAllViewInitialStatus();
    }

    @Test
    public void launchWithoutExtra_addMod() {
        launchActivity(null);
        checkAllViewInitialStatus();
    }

    @Test
    public void launchWithFullExtra_editMod() {
        String cardName = "CardName";
        String barCode = "12138";

        mRule.launchActivity(getIntent(cardName, barCode));

        onView(withId(R.id.et_card_name)).check(matches(allOf(withText(cardName), withHint(R.string.cb_please_input_card_name_hint), isDisplayed()))).perform(closeSoftKeyboard());
        onView(withId(R.id.iv_card)).check(matches(allOf(withContentDescription(R.string.cb_card_edit_img_content_desc), isDisplayed())));
        onView(withId(R.id.iv_bar_code)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_card_front)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_card_back)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_bar_code)).check(matches(allOf(withText(barCode), withHint(R.string.cb_bar_code_place_holder), isDisplayed())));
        onView(withText(R.string.cb_title_card_edit_edit)).check(matches(isDisplayed()));
        onView(withId(R.id.menu_card_edit_save)).check(matches(allOf(isDisplayed(), withText(R.string.cb_menu_text_card_edit_finish))));
    }

    @Test
    public void clickIvBarCode_goScannerActivity() {
        launchActivity(getIntent(null, null));
        String barCode = scanBarCode();
        onView(withId(R.id.iv_bar_code)).check(matches(withContentDescription(barCode)));
        onView(withId(R.id.tv_bar_code)).check(matches(withText(barCode)));
    }

    @NonNull
    private String scanBarCode() {
        String barCode = "12138";

        Intent scanIntent = new Intent();
        scanIntent.putExtra(CBScannerActivity.EXTRA_RESULT_BAR_OR_CODE_STRING, barCode);
        intending(hasComponent(CBScannerActivity.class.getName())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, scanIntent));

        onView(withId(R.id.iv_bar_code)).perform(click());
        return barCode;
    }

    @Test
    public void clickIvCardFront_goTakePicture() throws IOException {
        launchActivity(getIntent(null, null));
        onView(withId(R.id.et_card_name)).perform(closeSoftKeyboard());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, takePictureIntent));
        Intent cropIntent = new Intent();
        intending(hasComponent(UCropActivity.class.getName())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, cropIntent));

        onView(withId(R.id.iv_card_front)).perform(click());

        intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE));

        intended(allOf(
                hasExtra(equalTo(EXTRA_INPUT_URI), any(Uri.class)),
                hasExtra(equalTo(EXTRA_OUTPUT_URI), any(Uri.class)),
                hasComponent(UCropActivity.class.getName())));
    }

    @Test
    public void clickIvCardBack_goTakePicture() throws IOException {
        launchActivity(getIntent(null, null));
        onView(withId(R.id.et_card_name)).perform(closeSoftKeyboard());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, takePictureIntent));
        Intent cropIntent = new Intent();
        intending(hasComponent(UCropActivity.class.getName())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, cropIntent));

        onView(withId(R.id.iv_card_back)).perform(click());

        intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE));

        intended(allOf(
                hasExtra(equalTo(EXTRA_INPUT_URI), any(Uri.class)),
                hasExtra(equalTo(EXTRA_OUTPUT_URI), any(Uri.class)),
                hasComponent(UCropActivity.class.getName())));
    }

    @Test
    public void barCodeEmpty_showToast() {
        launchActivity(getIntent(null, null));
        onView(withId(R.id.et_card_name)).check(matches(withText("")));
        onView(withId(R.id.menu_card_edit_save)).perform(click());
        ToastChecker.checkToast(R.string.cb_toast_card_edit_card_name_empty, mRule);
        assertThat(mRule.getActivity().isFinishing(), equalTo(false));
    }

    @Test
    @Ignore
    public void nameEmpty_showToast() {
        launchActivity(getIntent(null, null));
        onView(withId(R.id.et_card_name)).perform(typeText("cardName"));
        onView(withId(R.id.tv_bar_code)).check(matches(withText("")));
        onView(withId(R.id.menu_card_edit_save)).perform(click());
        ToastChecker.checkToast(R.string.cb_toast_card_edit_bar_code_non, mRule);
        assertThat(mRule.getActivity().isFinishing(), equalTo(false));
    }

    @Test
    @Ignore
    public void saveSuccess_finishActivity() {
        launchActivity(getIntent(null, null));
        scanBarCode();
        onView(withId(R.id.et_card_name)).perform(typeText("cardName"));
        onView(withId(R.id.menu_card_edit_save)).perform(click());
        assertThat(mRule.getActivity().isFinishing(), equalTo(true));
    }

    private void checkAllViewInitialStatus() {
        onView(withId(R.id.iv_card)).check(matches(allOf(withContentDescription(R.string.cb_card_edit_img_content_desc), isDisplayed())));
        onView(withId(R.id.iv_bar_code)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_card_front)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_card_back)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_bar_code)).check(matches(allOf(withText(""), withHint(R.string.cb_bar_code_place_holder), isDisplayed())));
        onView(withText(R.string.cb_title_card_edit_add)).check(matches(isDisplayed()));
        onView(withId(R.id.menu_card_edit_save)).check(matches(allOf(isDisplayed(), withText(R.string.cb_menu_text_card_edit_finish))));
        onView(withId(R.id.et_card_name)).check(matches(allOf(withText(""), withHint(R.string.cb_please_input_card_name_hint), isDisplayed())));
    }
}
