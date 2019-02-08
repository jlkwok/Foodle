package com.swe.foodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Converts Ingredients into a useable form to display in the CalorieCount list view.
 */
public class CalorieCountAdapter extends ArrayAdapter<Ingredient> {
    // stores the ingredients to be displayed in the list view
    List<Ingredient> ingredients;
    /**
     * Creates a CalorieCountAdapter with a context and a list of ingredients.
     * @param context the background info to display info to
     * @param ingredients the ingredient info to display
     */
    CalorieCountAdapter(Context context, List<Ingredient> ingredients) {
        super(context, R.layout.ingredient_row, ingredients);
        this.ingredients = ingredients;
    }

    /**
     * Formats an item to be displayed in the CalorieCount list view.
     * @param position the position of the item in the list view
     * @param convertView the view to convert from
     * @param parent the parent containing the list view
     * @return the view displaying the ingredients
     */
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.ingredient_row, parent, false);
        View quantity = customView.findViewById(R.id.quantity);

        final Ingredient ingredient = getItem(position);
        TextView textView = customView.findViewById(R.id.ingredient);
        final ImageButton deleteButton = customView.findViewById(R.id.deleteButton);
        final ImageButton upButton = quantity.findViewById(R.id.upButton);
        final ImageButton downButton = quantity.findViewById(R.id.downButton);

        String item = String.format("%s - %d serving(s)", ingredient.getName(), ingredient.getQuantity());
        textView.setText(item);

        // negative quantity not allowed
        if (ingredients.get(position).getQuantity() == 1) {
            downButton.setEnabled(false);
        }
        else {
            downButton.setEnabled(true);
        }
        deleteButton.setTag(position);
        upButton.setTag(position);
        downButton.setTag(position);

        final CalorieCount calorieCount = CalorieCount.getInstance();
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredient removeIngredient = calorieCount.getIngredients().get(position);
                calorieCount.removeIngredient(removeIngredient);
                notifyDataSetChanged();
                CalorieCountActivity.updateTotalCaloriesText();
            }
        });
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredient item = calorieCount.getIngredients().get(position);
                calorieCount.changeIngredientQuantity(item, item.getQuantity() + 1);
                notifyDataSetChanged();
                CalorieCountActivity.updateTotalCaloriesText();
            }
        });
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredient item = calorieCount.getIngredients().get(position);
                calorieCount.changeIngredientQuantity(item, item.getQuantity() - 1);
                notifyDataSetChanged();
                CalorieCountActivity.updateTotalCaloriesText();
            }
        });
        return customView;
    }
}
