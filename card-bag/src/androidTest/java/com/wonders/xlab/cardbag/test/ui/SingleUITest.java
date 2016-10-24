package com.wonders.xlab.cardbag.test.ui;

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
import com.wonders.xlab.cardbag.ui.cardedit.CardEditActivity;
import com.yalantis.ucrop.UCropActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.yalantis.ucrop.UCrop.EXTRA_INPUT_URI;
import static com.yalantis.ucrop.UCrop.EXTRA_OUTPUT_URI;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by hua on 2016/10/24.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SingleUITest {
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
}
