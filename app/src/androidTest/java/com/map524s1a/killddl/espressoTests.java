package com.map524s1a.killddl;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class espressoTests {

    @Rule
    public ActivityTestRule<MainViewActivity> mActivityRule =
            new ActivityTestRule<>(MainViewActivity.class);
    @Test
    public void add_event() {
        onView(withId(R.id.add_event))
                .check(matches(not(isDisplayed())));
    }
}