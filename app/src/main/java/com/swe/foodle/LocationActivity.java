package com.swe.foodle;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Displays the location search page with recipes tailored to the user's location.
 */
public class LocationActivity extends AppCompatActivity {
    // stores the location manager that will retrieve the user location
    private LocationManager locationManager;
    // stores the background info of the LocationActivity page
    private final Context context = this;
    // stores the listener for location updates
    private LocationListener locationListener = new LocationListener() {
        /**
         * Retrieves the updated location.
         * @param location the updated location
         */
        @Override
        public void onLocationChanged(Location location) {
            System.out.println(String.format("%f %f", location.getLatitude(), location.getLongitude()));
            // determines the user's country code based on the user's longitude and latitude
            try{
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                UserLocation.getInstance().setCountryCode(addresses.get(0).getCountryCode());
                System.out.println("**************" + addresses.get(0).getCountryCode());
                LocationSearch.getInstance(context).setCuisineType(UserLocation.getInstance().getCuisineType());
                setUpSearch();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            // user location received - disables further location updates
            locationManager.removeUpdates(this);
        }

        /**
         * Status of the location provider has changed.
         * @param provider name of the location provider associated with this update
         * @param status the status change
         * @param extras provides specific status variables
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        /**
         * Called when the user enables the provider.
         * @param provider name of provider associated with update
         */
        @Override
        public void onProviderEnabled(String provider) {

        }

        /**
         * Called when the user disables the provider.
         * @param provider name of provider associated with update
         */
        @Override
        public void onProviderDisabled(String provider) {
            //Toast.makeText(context, "Please enable GPS and Internet.", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * Configures and sends the search for cuisine recipes.
     */
    private void setUpSearch(){
        final LocationSearch search = LocationSearch.getInstance(this);
        final Context context = this;
        search.sendRequest(new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                search.setResult(result);
                System.out.println("Search Results: " + search.getResult());
                List<Recipe> recipes = search.parseResults(search.getResult());
                ListView listView = findViewById(R.id.recipeListView);
                MainActivity.espressoTestIdlingResource.increment();
                search.displayRecipes(recipes, listView, context);
                return result;
            }
        });
    }

    /**
     * Receives the results of the user location permission request.
     * @param requestCode the request code passed when requesting permission
     * @param permissions the requested permissions
     * @param grantResults the user results of the permissions requested
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // stores whether the user has granted permission to access his or her location
        boolean isLocationAccessible = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

        // permission granted
        if (isLocationAccessible){
            retrieveUserLocation();
        }
        // permission denied - display user message
        else{
            Toast.makeText(getApplicationContext(), "Please enable GPS and Internet.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Retrieves the user's location.
     */
    private void retrieveUserLocation(){
        try{
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        catch(SecurityException e){
            e.printStackTrace();
        }
    }

    /**
     * Creates the location search activity display page.
     * @param savedInstanceState the saved state of LocationActivity
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationsearch);
        // requests permission for location access
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        // permission already granted - get user's location
        else{
            retrieveUserLocation();
        }
    }
}
