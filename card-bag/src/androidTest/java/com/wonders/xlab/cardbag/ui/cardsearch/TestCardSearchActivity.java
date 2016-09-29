package com.wonders.xlab.cardbag.ui.cardsearch;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by hua on 16/9/29.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestCardSearchActivity {
    @Rule
    public IntentsTestRule<CardSearchActivity> mRule = new IntentsTestRule<>(CardSearchActivity.class);

    @Test
    public void testInitialViewStatus() {

    }
}