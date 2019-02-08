package com.swe.foodle;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Tests the CalorieCountActivity functionality (also testing CalorieCountAdapter).
 */
@RunWith(AndroidJUnit4.class)
public class CalorieCountActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Tests the CalorieCount page functionality.
     */
    @Test
    public void CalorieCountActiviyTest() {
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
                Matchers.allOf(childAtPosition(
                        Matchers.allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navigationView),
                                        0)),
                        7),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        // list containing potential ingredients
        ViewInteraction listView = onView(
                Matchers.allOf(withId(R.id.ingredient_listview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        listView.check(matches(isDisplayed()));

        // editText for ingredient input
        ViewInteraction editText = onView(
                Matchers.allOf(withId(R.id.foodItemName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        // add button
        ViewInteraction button = onView(
                Matchers.allOf(withId(R.id.addButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        // total calories text
        ViewInteraction textView = onView(
                Matchers.allOf(withId(R.id.totalCalories), withText("Total Calories: 0.00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        // test non-ingredient input
        ViewInteraction appCompatEditText = onView(
                Matchers.allOf(withId(R.id.foodItemName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("a"), closeSoftKeyboard());

        // error toast appears
        ViewInteraction appCompatButton = onView(
                Matchers.allOf(withId(R.id.addButton), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        // total calories text not changed
        ViewInteraction textView1 = onView(
                Matchers.allOf(withId(R.id.totalCalories), withText("Total Calories: 0.00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        textView1.check(matches(isDisplayed()));

        // searching apple
        ViewInteraction appCompatEditText2 = onView(
                Matchers.allOf(withId(R.id.foodItemName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("apple"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                Matchers.allOf(withId(R.id.addButton), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        // list is populated with one serving apple
        ViewInteraction textView2 = onView(
                Matchers.allOf(withId(R.id.ingredient), withText("apple - 1 serving(s)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_listview),
                                        0),
                                0),
                        isDisplayed()));

        ViewInteraction linearLayout = onView(
                Matchers.allOf(withId(R.id.quantity),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_listview),
                                        0),
                                1),
                        isDisplayed()));

        ViewInteraction imageButton = onView(
                Matchers.allOf(withId(R.id.deleteButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_listview),
                                        0),
                                2),
                        isDisplayed()));

        // total calories is updated with caloric amount of one apple serving
        ViewInteraction textView3 = onView(
                Matchers.allOf(withId(R.id.totalCalories), withText("Total Calories: 32.50"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));

        // increasing serving size by adding apple via edittext
        ViewInteraction appCompatEditText3 = onView(
                Matchers.allOf(withId(R.id.foodItemName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("apple"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                Matchers.allOf(withId(R.id.foodItemName), withText("apple"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatButton3 = onView(
                Matchers.allOf(withId(R.id.addButton), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        // increased serving size is displayed in list
        ViewInteraction textView4 = onView(
                Matchers.allOf(withId(R.id.ingredient), withText("apple - 2 serving(s)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_listview),
                                        0),
                                0),
                        isDisplayed()));

        // total calories is doubled
        ViewInteraction textView5 = onView(
                Matchers.allOf(withId(R.id.totalCalories), withText("Total Calories: 65.00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));

        // increasing serving size via up button
        ViewInteraction appCompatImageButton2 = onView(
                Matchers.allOf(withId(R.id.upButton),
                        childAtPosition(
                                Matchers.allOf(withId(R.id.quantity),
                                        childAtPosition(
                                                withClassName(Matchers.is("android.widget.RelativeLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        // increased serving size is displayed in list
        ViewInteraction textView6 = onView(
                Matchers.allOf(withId(R.id.ingredient), withText("apple - 3 serving(s)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_listview),
                                        0),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("apple - 3 serving(s)")));

        // total calories updated to 3 servings of apple
        ViewInteraction textView7 = onView(
                Matchers.allOf(withId(R.id.totalCalories), withText("Total Calories: 97.50"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        textView7.check(matches(withText("Total Calories: 97.50")));

        // decreasing serving size via down button
        ViewInteraction appCompatImageButton3 = onView(
                Matchers.allOf(withId(R.id.downButton),
                        childAtPosition(
                                Matchers.allOf(withId(R.id.quantity),
                                        childAtPosition(
                                                withClassName(Matchers.is("android.widget.RelativeLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        // serving size change displayed in lisit
        ViewInteraction textView8 = onView(
                Matchers.allOf(withId(R.id.ingredient), withText("apple - 2 serving(s)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_listview),
                                        0),
                                0),
                        isDisplayed()));

        // total calories updated
        ViewInteraction textView9 = onView(
                Matchers.allOf(withId(R.id.totalCalories), withText("Total Calories: 65.00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        textView9.check(matches(withText("Total Calories: 65.00")));

        // adding different ingredient via edittext
        ViewInteraction appCompatEditText5 = onView(
                Matchers.allOf(withId(R.id.foodItemName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("ham"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                Matchers.allOf(withId(R.id.addButton), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        // original list is still displayed
        ViewInteraction textView10 = onView(
                Matchers.allOf(withId(R.id.ingredient), withText("apple - 2 serving(s)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_listview),
                                        0),
                                0),
                        isDisplayed()));
        textView10.check(matches(withText("apple - 2 serving(s)")));

        // one serving of ham added to list
        ViewInteraction textView11 = onView(
                Matchers.allOf(withId(R.id.ingredient), withText("ham - 1 serving(s)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_listview),
                                        1),
                                0),
                        isDisplayed()));

        // total calories updated for 2 servings of apple and 1 serving of ham
        ViewInteraction textView12 = onView(
                Matchers.allOf(withId(R.id.totalCalories), withText("Total Calories: 271.55"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));

        // deleting apple item in list
        ViewInteraction appCompatImageButton4 = onView(
                Matchers.allOf(withId(R.id.deleteButton),
                        childAtPosition(
                                withParent(withId(R.id.ingredient_listview)),
                                2),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        // only ham in list
        ViewInteraction textView13 = onView(
                Matchers.allOf(withId(R.id.ingredient), withText("ham - 1 serving(s)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredient_listview),
                                        0),
                                0),
                        isDisplayed()));

        // total calories updated
        ViewInteraction textView14 = onView(
                Matchers.allOf(withId(R.id.totalCalories), withText("Total Calories: 206.55"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));

        // testing empty user input
        ViewInteraction appCompatButton5 = onView(
                Matchers.allOf(withId(R.id.addButton), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton5.perform(click());


        // total calories text
        ViewInteraction textView16 = onView(
                Matchers.allOf(withId(R.id.totalCalories), withText("Total Calories: 206.55"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
    }

    /**
     * Helps with testing calorie count activity page, helps test that components are displayed correctly.
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