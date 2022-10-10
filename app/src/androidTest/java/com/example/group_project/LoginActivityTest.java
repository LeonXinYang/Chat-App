package com.example.group_project;
import android.view.View;
import android.widget.TextView;
import android.content.Context;

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

import java.util.LinkedList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> myRule = new ActivityTestRule<>(LoginActivity.class);

    // Test if no user email
    @Test
    public void test1_NoUserEmail(){
        onView(withId(R.id.editText_Email_Login)).perform(setTextInTextView(""));
        onView(withId(R.id.editText_Password_Login)).perform(setTextInTextView("123456"));
        onView(withId(R.id.button_Login)).perform(click());
    }

    // Test if no user password
    @Test
    public void test2_NoPassword(){
        onView(withId(R.id.editText_Email_Login)).perform(setTextInTextView("1@qq.com"));
        onView(withId(R.id.button_Login)).perform(click());
    }

    // Test if it can successfully log in
    @Test
    public void test_LogIn (){
        onView(withId(R.id.editText_Email_Login)).perform(setTextInTextView("1@qq.com"));
        onView(withId(R.id.editText_Password_Login)).perform(setTextInTextView("123456"));
        onView(withId(R.id.button_Login)).perform(click());
    }

    // Test if user and password not match
    @Test
    public void test_UandP_not_match(){
        onView(withId(R.id.editText_Email_Login)).perform(setTextInTextView("1@qq.com"));
        onView(withId(R.id.editText_Password_Login)).perform(setTextInTextView("1"));
        onView(withId(R.id.button_Login)).perform(click());
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
