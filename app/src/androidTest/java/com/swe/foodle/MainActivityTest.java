package com.swe.foodle;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Checkable;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.Matchers.*;

/**
 * Testing functionality of MainActivity and RecipeAdapter
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private String recipeName;
    @Rule
    public ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<>(MainActivity.class);

    /**
     * Sets up the tests for MainActivity.
     */
    @Before
    public void setUp() {
        recipeName = "pizza";
        MainActivity.MainActivityTestHook mainActivityTestHook = mActivity.getActivity().new MainActivityTestHook();
        CountingIdlingResource countingIdlingResource = mainActivityTestHook.getMainActivityIdlingResource();
        IdlingRegistry.getInstance().register(countingIdlingResource);
    }

    /**
     * Ensures that all UI homepage components are present.
     */
    @Test
    public void onCreate() {
        onView(allOf(withId(R.id.search_bar), isDisplayed(), withHint(R.string.search_hint_keyword)));
        onView(allOf(withId(R.id.switchForActionBar), isDisplayed()));
        onView(allOf(withId(R.id.recipeListView), isDisplayed()));
        onView(allOf(withId(R.id.navigationView), isDisplayed()));
        onView(allOf(withId(R.id.searchButton), isDisplayed()));
    }

    /**
     * Ensures recipes are being displayed correctly.
     */
    @Test
    public void displayRecipe() {
        // searching pizza
        ViewInteraction searchAutoComplete = onView(
                Matchers.allOf(withClassName(Matchers.is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                Matchers.allOf(withClassName(Matchers.is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(Matchers.is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("pizza"), ViewActions.closeSoftKeyboard());

        // check recipe list is populated
        ViewInteraction appCompatImageButton = onView(
                Matchers.allOf(withId(R.id.searchButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawerLayout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        // check individual recipe image is displayed
        ViewInteraction imageView = onView(
                Matchers.allOf(withId(R.id.imageView), withContentDescription("TODO"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipeListView),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        // check individual recipe text is displayed
        ViewInteraction textView = onView(
                Matchers.allOf(withId(R.id.recipeText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipeListView),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
    }

    /**
     * Ensures side navigation bar is working.
     */
    @Test
    public void onOptionsItemSelected() {
        ViewInteraction appCompatImageButton = onView(
                Matchers.allOf(withContentDescription("Open"),
                        childAtPosition(
                                Matchers.allOf(withId(R.id.action_bar),
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
                        5),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction frameLayout = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                0),
                        0),
                        isDisplayed()));
    }

    /**
     * Ensures toggle differentiates between keywordSearch and ingredientSearch.
     */
    @Test
    public void toggleOn() {
        // switch on
        onView(withId(R.id.switchForActionBar)).perform(setChecked(true));

        // add button appears
        ViewInteraction imageButton = onView(
                Matchers.allOf(withId(R.id.addButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawerLayout),
                                        0),
                                2),
                        isDisplayed()));
    }

    /**
     * Helper method for toggle testing.
     * @param checked whether the toggle is enabled.
     * @return information about the toggle.
     */
    public static ViewAction setChecked(final boolean checked) {
        return new ViewAction() {
            /**
             * Constraints of the view.
             * @return the view's matcher object.
             */
            @Override
            public BaseMatcher<View> getConstraints() {
                return new BaseMatcher<View>() {
                    /**
                     * Determines whether the item specified is a Checkable.
                     * @param item the item that could be a Checkable
                     * @return whether the item is a Checkable
                     */
                    @Override
                    public boolean matches(Object item) {
                        return isA(Checkable.class).matches(item);
                    }

                    /**
                     * Attaches a description to the parent matcher.
                     * @param description the description of where the child component is in relation to the parent
                     */
                    @Override
                    public void describeTo(Description description) {}
                };
            }

            /*
             * @return the description of the view
             */
            @Override
            public String getDescription() {
                return null;
            }

            /**
             * Changes the UI element's checkable control.
             * @param uiController the UI element to change whether it's activated or not.
             * @param view the view containing the UI element
             */
            @Override
            public void perform(UiController uiController, View view) {
                Checkable checkableView = (Checkable) view;
                checkableView.setChecked(checked);
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