package com.map524s1a.killddl;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class espressoTests {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void init_before(){
        Intents.init();
    }
    @After
    public void release_after(){
        Intents.release();
    }

    // test 1
    @Test
    public void valid_login() {
        // Type text and then press the button.
        String email = "killddl@usc.edu";
        String password = "wegotit";

        onView(withId(R.id.email))
                .perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.pswd))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.LoginBtn)).perform(click());

        // Check that the view was changed to MainViewActivity which means user successfully logged in.
        intended(hasComponent(MainViewActivity.class.getName()));
    }

    // test 2 TODO
    @Test
    public void invalid_login(){
        // Type text and then press the button.
        String email = "usc.edu";
        String password = "false";

        onView(withId(R.id.email))
                .perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.pswd))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.LoginBtn)).perform(click());

        // Check that the view is still LoginActivity which means user was unsuccessful in logging in.
        intended(hasComponent(LoginActivity.class.getName()));
    }

    // test 3
    @Test
    public void register(){
        onView(withId(R.id.register)).perform(click());
        // Check on register view
        intended(hasComponent(RegisterActivity.class.getName()));
    }

    // test 4 TODO
    @Test
    public void add_event() {
        // Type text and then press the button.
        String email = "killddl@usc.edu";
        String password = "wegotit";

        onView(withId(R.id.email))
                .perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.pswd))
                .perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.LoginBtn)).perform(click());
        // on main view page
    }
}