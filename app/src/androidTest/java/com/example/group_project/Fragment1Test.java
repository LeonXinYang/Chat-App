package com.example.group_project;

import android.view.View;
import android.widget.TextView;
import android.content.Context;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import static org.hamcrest.core.StringContains.containsString;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.LinkedList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagKey;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class Fragment1Test {

    @Rule
    public ActivityTestRule<MainActivity> myRule = new ActivityTestRule<>(MainActivity.class);

    // Ensure in the correct activity
    @Before
    public void switch_to_fragment1() throws InterruptedException {
        onView(withText("Drift Bottle")).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.imageView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.button_DriftBottle)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    // Test after clicking the bottle, a new post is shown
    @Test
    public void test_RandomBottle() throws InterruptedException {
        onView(withId(R.id.button_DriftBottle)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.imageview_Post)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.imageButton_Close)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.recyclerView_randbottle)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    // Test close the views
    @Test
    public void test_close() throws InterruptedException {
        onView(withId(R.id.button_DriftBottle)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.imageview_Post)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.imageButton_Close)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.imageButton_Close)).perform(click());
    }

    // Test two bottles are different random bottles
    @Test
    public void test_different_bottle() throws InterruptedException {
        onView(withId(R.id.button_DriftBottle)).perform(click());
        Thread.sleep(500);
        String last_postID = String.valueOf(Fragment1.randomPostID);
        onView(withId(R.id.imageButton_Close)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.button_DriftBottle)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.textView_PostID)).check(matches(not(withText(last_postID))));
    }

    // Test if it can open the user profile
    @Test
    public void test_user_profile() throws InterruptedException {
        onView(withId(R.id.button_DriftBottle)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.imageView_avatar_post)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.textView_username_profile)).check(matches(withText(containsString("TestUser"))));
    }

}
