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
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
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
public class black_box_espresso_tests {
    // helper functions
    public void espressoLogin(){
        // Type text and then press the button.
        String email = "killddl@usc.edu";
        String password = "wegotit";

        onView(withId(R.id.email))
                .perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.pswd))
                .perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.LoginBtn)).perform(click());
    }
    public void espressoAddEvent(){
        String eventName = "testEvent";
        String eventDescription = "testDescription";
        // fill in event name and description
        onView(withId(R.id.topic_val))
                .perform(typeText(eventName));
        onView(withId(R.id.des_val))
                .perform(typeText(eventDescription), closeSoftKeyboard());
        // click add event
        onView(withId(R.id.add_event)).perform(click());
        // go back
        Espresso.pressBack();
    }

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
        espressoLogin();
        // Check that the view was changed to MainViewActivity which means user successfully logged in.
        intended(hasComponent(MainViewActivity.class.getName()));
    }

    // test 2
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

        // Check that the view does not have add_event button, which means login was unsuccessful.
        onView(withId(R.id.add_event))
                .check(doesNotExist());
    }

    // test 3
    @Test
    public void register(){
        onView(withId(R.id.register)).perform(click());
        // Check on register view
        intended(hasComponent(RegisterActivity.class.getName()));
    }

    // test 4
    @Test
    public void add_event() {
        espressoLogin();
        // on main view page
        onView(withId(R.id.floatingActionButton)).perform(click());
        espressoAddEvent();
        onView(withId(R.id.dailyTag)).perform(click());
        // event exists
        onView(withId(R.id.card)).check(matches(isDisplayed()));
    }

    // test 5
    @Test
    public void click_profile_button(){
        espressoLogin();
        // click profile button
        onView(withId(R.id.profile)).perform(click());
        // check that button3 (profile button) exists
        onView(withId(R.id.button3)).check(matches(isDisplayed()));
    }

    // test 6
    @Test
    public void click_list_view(){
        espressoLogin();
        // click list button
        onView(withId(R.id.profile)).perform(click());
        // check list is displayed
        onView(withId(R.id.card)).check(matches(isDisplayed()));
    }

    // test 7
    @Test
    public void correct_event_name(){
        espressoLogin();
        onView(withId(R.id.floatingActionButton)).perform(click());
        espressoAddEvent();
        onView(withId(R.id.dailyTag)).perform(click());
        // check event name is correct
        onView(withId(R.id.evetitle)).check(matches(withText("testEvent")));
    }

    // test 8
    @Test
    public void delete_event(){
        espressoLogin();
        onView(withId(R.id.floatingActionButton)).perform(click());
        espressoAddEvent();
        onView(withId(R.id.dailyTag)).perform(click());

        onView(withId(R.id.card)).perform(click());
        // click delete button
        onView(withId(R.id.deleteBtn)).perform(click());
        // check event has been deleted
        onView(withId(R.id.card))
                .check(doesNotExist());
    }
    // test 9
    @Test
    public void good_register(){
        String name = "testName";
        String email = "test@usc.edu";
        String pass = "testPass";
        String image = "test.jpg";

        onView(withId(R.id.register)).perform(click());
        onView(withId(R.id.name))
                .perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.emailAdd))
                .perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.pswd))
                .perform(typeText(pass), closeSoftKeyboard());
//        onView(withId(R.id.img))
//                .perform(typeText(image), closeSoftKeyboard());
        onView(withId(R.id.saveBtn)).perform(click());
        onView(withId(R.id.name))
                .check(matches(isDisplayed()));
    }
    // test 10
    @Test
    public void bad_register(){
        onView(withId(R.id.register)).perform(click());
        onView(withId(R.id.name))
                .perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.saveBtn)).perform(click());
        // stay on same page = unregistered
        onView(withId(R.id.name))
                .check(matches(isDisplayed()));
    }
    // test 11
    @Test
    public void switchViews(){
        String email = "killddl@usc.edu";
        String password = "wegotit";

        onView(withId(R.id.email))
                .perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.pswd))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.LoginBtn)).perform(click());

        onView(withId(R.id.dailyTag)).perform(click());

        intended(hasComponent(MainViewActivity.class.getName()));
    }

    // test 12
    @Test
    public void switchViews2(){
        String email = "killddl@usc.edu";
        String password = "wegotit";

        onView(withId(R.id.email))
                .perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.pswd))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.LoginBtn)).perform(click());

        onView(withId(R.id.dailyTag)).perform(click());
        onView(withId(R.id.monthlyTag)).perform(click());

        intended(hasComponent(MainViewActivity.class.getName()));
    }
    // test 13
    @Test
    public void check_events_test_no_events(){
        espressoLogin();

        onView(withId(R.id.profile)).perform(click());
        // event doesn't exist
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.button3)).perform(click());
        //intended(hasComponent(ProfileFragment.class.getName()));
    }
    // test 14
    @Test
    public void check_events_test_with_events(){
        espressoLogin();
        // add an event
        onView(withId(R.id.floatingActionButton)).perform(click());
        espressoAddEvent();

        //Go to Profile
        onView(withId(R.id.profile)).perform(click());

        // event exists so we check if the current time relates to it
        onView(withId(R.id.button3)).perform(click());

        onView(withId(R.id.button3)).perform(click());

        onView(withId(R.id.button3)).perform(click());

    }
    // test 15
    @Test
    public void send_email_test(){
        espressoLogin();

        //Go to Profile
        onView(withId(R.id.profile)).perform(click());

        // event exists so we check if the current time relates to it
        onView(withId(R.id.emailButton)).perform(click());
        // go back
        Espresso.pressBack();


    }
}