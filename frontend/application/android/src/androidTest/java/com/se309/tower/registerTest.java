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


public class registerTest {
    private static final int SIMULATED_DELAY_MS = 500;
    @Rule   // needed to launch the activity
    public ActivityScenarioRule<CreateLoginPage> activityRule = new ActivityScenarioRule<>(CreateLoginPage.class);

    @Test
    public void EmailRegisterInvalidEmail(){
        String userString = "hello";
        String passString = "test";
        String emailString = "hello";

        // Type in testString and send request
        onView(withId(R.id.UsernameText))
                .perform(typeText(userString), closeSoftKeyboard());
        onView(withId(R.id.Passowrdtext))
                .perform(typeText(passString), closeSoftKeyboard());
        onView(withId(R.id.Email))
                .perform(typeText(emailString), closeSoftKeyboard());
        onView(withId(R.id.CreateLogin)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Invalid Email")).inRoot(isDialog())
                .check(matches(isDisplayed()));
        onView(withText("OK")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        // Verify that volley returned the correct value


    }
    @Test
    public void RegisterTakenUser(){
        String userString = "jeremy";
        String passString = "test";
        String emailString = "jalewis1@iastate.edu";

        // Type in testString and send request
        onView(withId(R.id.UsernameText))
                .perform(typeText(userString), closeSoftKeyboard());
        onView(withId(R.id.Passowrdtext))
                .perform(typeText(passString), closeSoftKeyboard());
        onView(withId(R.id.Email))
                .perform(typeText(emailString), closeSoftKeyboard());
        onView(withId(R.id.CreateLogin)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withText("Issue completing the request \nusername or email may be already be in use")).inRoot(isDialog())
                .check(matches(isDisplayed()));
        onView(withText("OK")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        // Verify that volley returned the correct value


    }









}
