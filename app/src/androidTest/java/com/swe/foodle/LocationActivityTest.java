package com.swe.foodle;


import android.os.Build;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Test the functionality of the Location Activity and LocationSearch, RecipeAdapter classes implicitly
 */
@RunWith(AndroidJUnit4.class)
public class LocationActivityTest {

    /**
     * the activity to be initially tested
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    /**
     * Tests that location-related recipes are displayed when the location button is pressed.
     */
    @Test
    public void lcoationActivityTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navigationView),
                                        0)),
                        6),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction listView = onView(
                allOf(withId(R.id.recipeListView),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        listView.check(matches(isDisplayed()));

        try {
            Thread.sleep(5000);
        }
        catch(Exception e) {}

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipeListView),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.recipeText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipeListView),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
    }

    /**
     * Helps with testing location activity page, helps test that components are displayed correctly.
     * @param parentMatcher the parent containing the children components
     * @param position the position of the child component
     * @return the view containing the components
     */
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            /**
             * Attaches a description to the parent matcher.
             * @param description the description of where the child component is in relation to the parent
             */
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            /**
             * Determines whether the parent view contains the view specified with child component.
             * @param view the view to determine if the parent view contains
             * @return whether the parent view contains the view specified with child component
             */
            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

