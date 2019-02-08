package com.swe.foodle;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Tests the functionality of IngredientSearch.
 */
public class IngredientSearchTest {
    /**
     * empty input
     * invalid input
     * correct input - added to list
     * double input
     * multiple inputs
     * deleting input
     * search - displayed in list
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Tests the functionality of ingredient-based searches.
     */
    @Test
    public void ingredientSearchTest() {
        // clicks on the search toggle to enable Ingredient search
        onView(withId(R.id.switchForActionBar)).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isEnabled(); // no constraints, they are checked above
            }

            @Override
            public String getDescription() {
                return "click plus button";
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.performClick();
            }
        });

        // testing empty user input
        // add button
        ViewInteraction addButton = onView(
                allOf(withId(R.id.addButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawerLayout),
                                        0),
                                0),
                        isDisplayed()));
        addButton.perform(click());

        // testing invalid ingredient input
        ViewInteraction searchAutoComplete = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("a"), closeSoftKeyboard());

        addButton.perform(click());

        // add apple to the list
        ViewInteraction searchAutoComplete2 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete2.perform(replaceText("apple"), closeSoftKeyboard());

        addButton.perform(click());

        ViewInteraction open = onView(
                allOf(withContentDescription("Open"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                2),
                        isDisplayed()));
        open.perform(click());

        ViewInteraction apple = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navigationView),
                                        0)),
                        2),
                        isDisplayed()));
        apple.check(matches(isDisplayed()));

        ViewInteraction close = onView(
                allOf(withContentDescription("Close"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                2),
                        isDisplayed()));
        close.perform(click());

        ViewInteraction searchAutoComplete3 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete3.perform(click());

        // test duplicate ingredient
        ViewInteraction searchAutoComplete4 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete4.perform(replaceText("apple"), closeSoftKeyboard());

        addButton.perform(click());
        open.perform(click());
        // only one apple item in list
        apple.check(matches(isDisplayed()));
        close.perform(click());

        // add ham to the list
        ViewInteraction searchAutoComplete5 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete5.perform(replaceText("ham"), closeSoftKeyboard());

        addButton.perform(click());

        // add cheese to the list
        ViewInteraction searchAutoComplete6 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete6.perform(replaceText("cheese"), closeSoftKeyboard());

        addButton.perform(click());
        open.perform(click());

        apple.check(matches(isDisplayed()));

        ViewInteraction ham = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navigationView),
                                        0)),
                        3),
                        isDisplayed()));
        ham.check(matches(isDisplayed()));

        ViewInteraction cheese = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navigationView),
                                        0)),
                        4),
                        isDisplayed()));
        cheese.check(matches(isDisplayed()));

        // delete cheese
        onView(withIndex(withId(R.id.deleteIngredient), 2)).perform(click());

        apple.check(matches(isDisplayed()));

        ham.check(matches(isDisplayed()));

        close.perform(click());

        // searching for recipes with apple and ham
        ViewInteraction search = onView(
                allOf(withId(R.id.searchButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawerLayout),
                                        0),
                                1),
                        isDisplayed()));
        search.perform(click());

        // check list is populated with recipes
        allOf(withId(R.id.imageView),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.recipeListView),
                                0),
                        0),
                isDisplayed());

        allOf(withId(R.id.recipeText),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.recipeListView),
                                0),
                        1),
                isDisplayed());

        open.perform(click());

        // delete all ingredients
        onView(withIndex(withId(R.id.deleteIngredient), 1)).perform(longClick());
        onView(withId(android.R.id.button1)).perform(click());

        close.perform(click());

    }

    /**
     * Helps with testing main activity page, helps test that components are displayed correctly.
     * @param matcher the matcher
     * @param index the position of the component
     * @return the view containing the components
     */
    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            /**
             * Attaches a description to the matcher.
             * @param description the description of where the component is
             */
            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            /**
             * Determines whether the matcher contains the view specified.
             * @param view the view to determine if the matcher contains
             * @return whether the matcher contains the view specified
             */
            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    /**
     * Helps with testing main activity page, helps test that components are displayed correctly.
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
