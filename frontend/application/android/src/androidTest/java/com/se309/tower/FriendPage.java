package com.se309.tower;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;



import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.StringEndsWith.endsWith;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

// Mock the RequestServerForService class
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class FriendPage {

    private static final int SIMULATED_DELAY_MS = 1000;
    @Rule   // needed to launch the activity
    public ActivityScenarioRule<LoginPage> activityRule = new ActivityScenarioRule<>(LoginPage.class);



    @Test
    public void AsendFriendRequest(){
        String testString = "test";
        String resultString = "test";
        // Type in testString and send request
        onView(withId(R.id.editTextTextPersonName))
                .perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword))
                .perform(typeText(resultString), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.Social)).perform(click());


        String friendString = "test2";
        onView(withId(R.id.addFriendText))
                .perform(typeText(friendString), closeSoftKeyboard());


        onView(withId(R.id.addFriendButton)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("sentRequest")).inRoot(isDialog())
                .check(matches(isDisplayed()));
        onView(withText("Ok")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.backSocial)).perform(click());
        onView(withId(R.id.logout)).perform(click());

    }

    @Test
    public void BDeclineFriendRequest(){
        String testString = "test2";
        String resultString = "test";
        // Type in testString and send request
        onView(withId(R.id.editTextTextPersonName))
                .perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword))
                .perform(typeText(resultString), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.Social)).perform(click());





        onView(withText("decline")).perform(click());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("Declined User")).inRoot(isDialog())
                .check(matches(isDisplayed()));
        onView(withText("Ok")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.backSocial)).perform(click());
        onView(withId(R.id.logout)).perform(click());

    }







}
