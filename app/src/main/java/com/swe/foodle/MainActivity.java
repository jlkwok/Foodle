package com.swe.foodle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.Menu.NONE;

/**
 * The initial activity displayed to the user where keyword and ingredient searches takes place.
 * This activity also contains the navigation side bar menu to go to other activity pages.
 */
public class MainActivity extends AppCompatActivity {
    // stores the navigation side bar menu
    private DrawerLayout drawerLayout;
    // stores the trigger for three bars side menu button
    private ActionBarDrawerToggle toggle;
    // stores the ingredient list used to populate the side bar menu when using ingredient search
    private List<Ingredient> ingredientsList = new ArrayList<>();
    // testing purposes - indicates whether task is finished
    public static CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource("Network_Call");

    /**
     * Creates the starting user page.
     * @param savedInstanceState the saved state of MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sets toggle on actionbar
        ActionBar mActionBar = getSupportActionBar();
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar_swtich, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        // app will start on search by keyword since toggle is off -> no add button
        findViewById(R.id.addButton).setVisibility(View.GONE);

        setUpSideMenu();
        setUpSideMenuNavigation();
        setUpSearchView();
        setUpSwitch();
        setUpSearchButton();
        setUpAddButton();
    }



    /**
     * Sets up the button to add ingredients to the list in navigation menu
     */
    private void setUpAddButton() {
        ImageButton add = findViewById(R.id.addButton);
        final Context context = this;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // gets ingredient from searchview
                SearchView search = findViewById(R.id.search_bar);
                String ingredient = search.getQuery().toString();

                if (ingredient.isEmpty()){
                    Toast.makeText(context, "Please enter ingredient.", Toast.LENGTH_SHORT).show();
                    return;
                }
                addIngredientToList(ingredient);

                search.setQuery("", false);
            }
        });
    }

    /**
     * Queries the Spoonacular Food and Recipe API for calorie information and adds a valid ingredient to the ingredient list
     * @param query the user specified ingredient
     */
    private void addIngredientToList(final String query)  {
        String url = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/parseIngredients?includeNutrition=true";
        final Context context = this;

        final ServerCallback callback = new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                try {
                    String name = result.getString("name");
                    JSONObject nutrition = result.getJSONObject("nutrition");
                    JSONArray nutrients = nutrition.getJSONArray("nutrients");
                    System.out.println(nutrients);
                    double calories = (Double)nutrients.getJSONObject(0).get("amount");
                    Ingredient proposedIngredient = new Ingredient(name, calories, 1);
                    if (!ingredientsList.contains(proposedIngredient)) {
                        ingredientsList.add(proposedIngredient);
                        Log.d("kwok, added", proposedIngredient.getName());
                        invalidateOptionsMenu();
                    }

                    return result;
                }
                catch(Exception e){
                    System.out.println("ERROR: No ingredients found");
                    Toast.makeText(context, "No ingredients found.", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                System.out.println("Response: " + response);
                JSONObject r = null;

                JsonObject g  = (new JsonParser()).parse(response.substring(1, response.length()-1)).getAsJsonObject();
                try {
                    r = new JSONObject(g.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(r);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR: " + error);
                if (error != null && error.networkResponse != null && error.networkResponse.statusCode == 400){
                    Toast.makeText(context, "No ingredients found.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("X-Mashape-Key", "EjhrHcodDhmshsWxOp0kGjh8wVxTp1EyLFljsnsSdOHeA1AiNn");
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError{
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("ingredientList", query);
                params.put("servings", "1");
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
        };

        // prevents sending the request twice
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        System.out.println("Request:" + request);
        try {
            System.out.println("Headers: " + request.getHeaders());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        MySingleton.getInstance(this).getRequestQueue().add(request);
    }

    /**
     * Formats the ingredients list populated in the side bar menu after the add button is called.
     * @param menu the menu to change
     * @return whether the menu has been changed successfully
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.getMenu().findItem(R.id.emptyIngredients).setVisible(false);
        Menu submenu = navigationView.getMenu().findItem(R.id.Ingredients).getSubMenu();

        if (!ingredientsList.isEmpty()) {
            navigationView.getMenu().findItem(R.id.Ingredients).setVisible(true);
            submenu.clear();
            System.out.println("Ingredients List: " + ingredientsList);
            for (Ingredient cs : ingredientsList)
                submenu.add(cs.getName());
        }
        else {
            navigationView.getMenu().findItem(R.id.emptyIngredients).setVisible(true);
            navigationView.getMenu().findItem(R.id.Ingredients).setVisible(false);
        }
        for (int i = 0; i < submenu.size(); i++) {
            final MenuItem menuItem = submenu.getItem(i);
            ImageButton imageButton = new ImageButton(getApplicationContext());
            imageButton.setId(R.id.deleteIngredient);
            imageButton.setImageResource(android.R.drawable.ic_delete);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ingredientsList.remove(new Ingredient(menuItem.getTitle().toString(), 0, 0));
                    invalidateOptionsMenu();
                    Log.d("kwok", "click");
                }
            });
            imageButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(R.string.delete_all)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ingredientsList.clear();
                                    invalidateOptionsMenu();
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.create();
                    builder.show();
                    return false;
                }
            });
            menuItem.setActionView(imageButton);
            Log.d("kwok, on prepare", submenu.getItem(i).getTitle().toString());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Sets up the toggle to listen for user's choice of search by ingredient/name
     */
    private void setUpSwitch() {
        Switch s = findViewById(R.id.switchForActionBar);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // switch is on - search by ingredient
                SearchView search = findViewById(R.id.search_bar);
                if (isChecked) {
                    search.setQueryHint(getResources().getString(R.string.search_hint_ingredient));
                    findViewById(R.id.addButton).setVisibility(View.VISIBLE);
                }
                // switch if off - search by keyword
                else {
                    search.setQueryHint(getResources().getString(R.string.search_hint_keyword));
                    findViewById(R.id.addButton).setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Set up the search button to have the same function if the user were to press search on the keyboard
     */
    private void setUpSearchButton() {
        ImageButton search = findViewById(R.id.searchButton);
        final Context context = this;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchView search = findViewById(R.id.search_bar);
                Switch searchSwitch = findViewById(R.id.switchForActionBar);

                String query = search.getQuery().toString();
                if (query.isEmpty() && !searchSwitch.isChecked()){
                    Toast.makeText(context, "Nothing to search for.", Toast.LENGTH_SHORT).show();
                    return;
                }
                espressoTestIdlingResource.increment();
                if(searchSwitch.isChecked()){
                    setUpIngredientSearch();
                }
                else{
                    setUpSearch(query);
                }
                // hides the keyboard on click
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    /**
     * Sets up the search bar to listen for submitted user text.
     */
    private void setUpSearchView(){
        SearchView searchView = findViewById(R.id.search_bar);
        final Context mContext = this;

        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchPlate = (EditText) searchView.findViewById(searchPlateId);
        searchPlate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Switch searchSwitch = findViewById(R.id.switchForActionBar);
                    if (searchSwitch.isChecked()){
                        setUpIngredientSearch();
                    }
                    else{
                        Toast.makeText(mContext, "Nothing to search for.", Toast.LENGTH_SHORT).show();
                    }
                    espressoTestIdlingResource.increment();
                    return false;
                }
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Switch searchSwitch = findViewById(R.id.switchForActionBar);
                if (searchSwitch.isChecked()){
                    setUpIngredientSearch();
                }
                else{
                    setUpSearch(query);
                }
                espressoTestIdlingResource.increment();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    /**
     * Sets up the search based on the user query provided.
     * @param query the user specified request for recipes of
     */
    private void setUpSearch(String query){
        final KeywordSearch search = new KeywordSearch(this, query);
        final Context context = this;
        search.sendRequest(new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                search.setResult(result);
                System.out.println("Search Results: " + search.getResult());

                List<Recipe> recipes = search.parseResults(search.getResult());
                ListView listView = findViewById(R.id.recipeListView);
                search.displayRecipes(recipes, listView, context);
                return result;
            }
        });
    }

    /**
     * Sets up the search based on the user query provided.
     */
    private void setUpIngredientSearch(){
        final IngredientSearch search = new IngredientSearch(this,ingredientsList);
        final Context context = this;
        search.sendRequest(new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                try {
                    search.setRecipeIds(result);
                    search.getRecipes(new ServerCallback() {
                        @Override
                        public JSONObject onSuccess(JSONObject result) {
                            search.setResult(result);
                            Log.d("kwok", "search results: " + search.getResult());
                            System.out.println("Search Results: " + search.getResult());

                            List<Recipe> recipes = search.parseResults(search.getResult());
                            ListView listView = findViewById(R.id.recipeListView);
                            search.displayRecipes(recipes, listView, context);
                            return result;
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }
        });
    }

    /**
     * Sets up the side bar menu.
     */
    private void setUpSideMenu(){
        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // back button icon option enabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // stores the name of the side bar menu items to their respective activity pages
    private static HashMap<Integer, Class<?>> sideMenuItemToClassCache = new HashMap<Integer, Class<?>>();
    static{
        // ingredients, breakfast, lunch, dinner, dessert, location, calorie counter, help
        sideMenuItemToClassCache.put(R.id.Breakfast, BreakfastActivity.class);
        sideMenuItemToClassCache.put(R.id.Lunch, LunchActivity.class);
        sideMenuItemToClassCache.put(R.id.Dinner, DinnerActivity.class);
        sideMenuItemToClassCache.put(R.id.Dessert, DessertActivity.class);
        sideMenuItemToClassCache.put(R.id.Location, LocationActivity.class);
        sideMenuItemToClassCache.put(R.id.CalorieCounter, CalorieCountActivity.class);
        sideMenuItemToClassCache.put(R.id.Help, HelpActivity.class);
    }

    /**
     * Sets up the side bar menu navigation. Sends the user to their requested activity page.
     */
    private void setUpSideMenuNavigation(){
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.getMenu().findItem(R.id.Ingredients).getSubMenu().getItem();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        String title = menuItem.getTitle().toString();

                        // start the activity page of the menu item selected
                        Class<?> menuItemClass = sideMenuItemToClassCache.get(id);
                        if (menuItemClass != null){
                            espressoTestIdlingResource.increment();
                            startActivity(new Intent(MainActivity.this, menuItemClass));
                        }

                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                }
        );
    }

    /**
     * Registers whether the user wishes to view the other page options.
     * @param item the menu item selected
     * @return whether the menu item was selected by the user
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item);
    }

    /**
     * Testing purposes
     */
    public class MainActivityTestHook{
        /**
         * Makes espresso aware to wait for api call to return.
         * @return the idling resource to use to signal espresso when to continue tests
         */
        public CountingIdlingResource getMainActivityIdlingResource(){
            return MainActivity.this.espressoTestIdlingResource;
        }
    }

}
