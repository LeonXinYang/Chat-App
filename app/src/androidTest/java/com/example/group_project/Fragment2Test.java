package com.example.group_project;

import android.view.View;
import android.widget.TextView;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

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
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
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
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class Fragment2Test {

    @Rule
    public ActivityTestRule<MainActivity> myRule = new ActivityTestRule<>(MainActivity.class);

    // Ensure in fragment 2
    @Before
    public void switch_to_fragment2() throws InterruptedException {
        onView(withText("Bottle Square")).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.imageView2)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.imageView3)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    // after 10 secs, see if there is a new post
    @Test
    public void test_newPostStream () throws InterruptedException {
        Post last_post = Fragment2.posts.get(0);
        Thread.sleep(10000);
        Post new_post = Fragment2.posts.get(0);
        assertNotEquals(last_post,new_post);
    }

    // Test if the user can follow others
    @Test
    public void test_Following (){
        List<Integer> last_following = GlobalData.getMe().following;
        onView(withId(R.id.RecyclerView_Timeline)).perform(RecyclerViewActions.actionOnItemAtPosition
                (0,click_item_in_recyclerView(R.id.button_follow)));
        onView(withId(R.id.RecyclerView_Timeline)).perform(RecyclerViewActions.actionOnItemAtPosition
                (1,click_item_in_recyclerView(R.id.button_follow)));
        List<Integer> following = GlobalData.getMe().following;
        assertNotEquals(last_following,following);
    }

    // Test if the user can like a post
    @Test
    public void test_Like (){
        List<Post> posts = Fragment2.posts;
        Integer prev_like = posts.get(0).getLikeThisPostUsers().size();
        onView(withId(R.id.RecyclerView_Timeline)).perform(RecyclerViewActions.actionOnItemAtPosition
                (0,click_item_in_recyclerView(R.id.imageView_Like)));
        onView(withId(R.id.RecyclerView_Timeline)).perform(RecyclerViewActions.actionOnItemAtPosition
                (0,click_item_in_recyclerView(R.id.textView_like_number))).check(matches(not(withText(prev_like))));
    }

    // Test if a user profile can be open
    @Test
    public void test_user_profile() throws InterruptedException {
        Thread.sleep(500);
        onView(withId(R.id.RecyclerView_Timeline)).perform(RecyclerViewActions.actionOnItemAtPosition
                (0,click_item_in_recyclerView(R.id.imageView_avatar_post)));
        Thread.sleep(500);
        onView(withId(R.id.textView_username_profile)).check(matches(withText(containsString("TestUser"))));

    }

    // A help method to click a item in the position of recycler view.
    public static ViewAction click_item_in_recyclerView(final int id){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
}