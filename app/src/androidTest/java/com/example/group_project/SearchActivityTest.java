package com.example.group_project;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
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
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withTagKey;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.bumptech.glide.util.Preconditions.checkNotNull;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class SearchActivityTest {
    GlobalData globalData;

    @Rule
    public ActivityTestRule<MainActivity> myRule = new ActivityTestRule<>(MainActivity.class);

    // Ensure in search activity fragment
    @Before
    public void switch_to_searchActivity() throws InterruptedException {
        onView(withText("Search")).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.editText_Search)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.imageButton_Search)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    // Search tag #Dinner
    @Test
    public void testSearchTag1() throws InterruptedException {
        onView(withId(R.id.editText_Search)).perform(setTextInTextView("#Dinner"));
        onView(withId(R.id.imageButton_Search)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.RecyclerView_Timeline_Searched)).check(matches
                (atPosition(0, hasDescendant(withText("#Dinner")))));
    }

    // Search tag #Comp2100
    @Test
    public void testSearchTag2() throws InterruptedException {
        onView(withId(R.id.editText_Search)).perform(setTextInTextView("#Comp2100"));
        onView(withId(R.id.imageButton_Search)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.RecyclerView_Timeline_Searched)).check(matches
                (atPosition(0, hasDescendant(withText("#Comp2100")))));
    }

    // Search tag #Comp6442isGood
    @Test
    public void testSearchTag3() throws InterruptedException {
        onView(withId(R.id.editText_Search)).perform(setTextInTextView("#Comp6442isGood"));
        onView(withId(R.id.imageButton_Search)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.RecyclerView_Timeline_Searched)).check(matches
                (atPosition(0, hasDescendant(withText("#Comp6442isGood")))));
    }

    // Test if user profile can be open.
    @Test
    public void test_user_profile() throws InterruptedException {
        onView(withId(R.id.editText_Search)).perform(setTextInTextView("#Comp2100"));
        onView(withId(R.id.imageButton_Search)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.RecyclerView_Timeline_Searched)).perform(RecyclerViewActions.actionOnItemAtPosition
                (0,click_item_in_recyclerView(R.id.imageView_avatar_post)));
        Thread.sleep(500);
        onView(withId(R.id.textView_username_profile)).check(matches(withText(containsString("TestUser"))));

    }


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

    /*
    riwnodennyk, "How to assert inside a RecyclerView in Espresso?",
    Jan 14 2016, available at:
    https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
     */

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    /*
     * Pavneet_Singh, "How to set a value to the textview using Espresso in Android",
     * Nov 10 2017, Available at:
     * https://stackoverflow.com/questions/47216782/how-to-set-a-value-to-the-textview-using-espresso-in-android*/
    public static ViewAction setTextInTextView(final String value){
        return new ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }
}
