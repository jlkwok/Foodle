package com.swe.foodle;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Tests the KeywordSearch functionality
 */
@RunWith(AndroidJUnit4.class)
public class KeywordSearchTest {
    private KeywordSearch search;
    private JSONObject testRecipe;
    private JSONArray testArray;
    private JSONObject testResult;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Sets up tests for KeywordSearch
     * @throws JSONException
     */
    @Before
    public void setUp() throws JSONException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        search = new KeywordSearch(appContext, "tiramisu");

        testRecipe = new JSONObject();
        testRecipe.put("title", "tiramisu");
        testRecipe.put("sourceUrl", "www.tiramisu.com");
        testRecipe.put("image", "tiramisu.jpg");

        testArray = new JSONArray();
        testArray.put(testRecipe);

        testResult = new JSONObject();
        testResult.put("recipes", testArray);

        search.setResult(testResult);

    }

    /**
     * Tests Recipe class getter method.
     */
    @Test
    public void getRecipeNameTest() {
        assertEquals("tiramisu", search.getRecipeName());
    }

    /**
     * Tests if server request is being sent.
     */
    @Test
    public void sendRequestTest() {
        String expected = "[ ] https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10&tags=tiramisu 0xc6b39fa6 NORMAL 1";
        assertEquals(expected, search.sendRequest(new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                search.setResult(result);
                List<Recipe> recipes = search.parseResults(search.getResult());
                return result;
            }
        }));
    }

    /**
     * Tests if result sent back from API is parsed correctly and stored in the recipes list.
     */
    @Test
    public void parseResultsTest() {
        Recipe r = new Recipe("tiramisu", "www.tiramisu.com", "tiramisu.jpg");
        search.setResult(testResult);
        search.parseResults(testResult);
        assertEquals(search.getRecipes().get(0), r);
    }

    /**
     * Tests user interaction with keyword search functionality.
     */
    @Test
    public void KeywordSearchTest() {
        // toggle is off - set for keyword search
        ViewInteraction switch_ = onView(
                allOf(withId(R.id.switchForActionBar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                1),
                        isDisplayed()));
        switch_.check(matches(isDisplayed()));

        // no user input
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.searchButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawerLayout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        // invalid user input
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

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.searchButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawerLayout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), withText("a"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete2.perform(click());

        // valid user input
        ViewInteraction searchAutoComplete3 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), withText("a"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete3.perform(replaceText("apple"));

        ViewInteraction searchAutoComplete4 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), withText("apple"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete4.perform(closeSoftKeyboard());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.searchButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawerLayout),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        // list is populated with recipes
        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView), withContentDescription("TODO"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipeListView),
                                        0),
                                0),
                        isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.recipeText), withText("Mediterranean Chopped Salad"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipeListView),
                                        0),
                                1),
                        isDisplayed()));
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