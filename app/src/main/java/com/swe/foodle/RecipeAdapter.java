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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * Converts Recipe into a useable form to display in the recipe list view.
 */
class RecipeAdapter extends ArrayAdapter<Recipe> {
    /**
     * Creates a RecipeAdapter with a context and a list of recipes.
     * @param context
     * @param recipes
     */
    RecipeAdapter(Context context, List<Recipe> recipes) {
        super(context, R.layout.recipe_row, recipes);
    }

    /**
     * Formats an item to be displayed in the recipe list view.
     * @param position the position of the item in the list view
     * @param convertView the view to convert from
     * @param parent the parent containing the list view
     * @return the view displaying the recipes
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.recipe_row, parent, false);

        final Recipe recipe = getItem(position);
        TextView textView = customView.findViewById(R.id.recipeText);
        ImageView imageView = customView.findViewById(R.id.imageView);

        textView.setText(recipe.getName());
        new DownloadImageTask(imageView).execute(recipe.getPicture());
        return customView;
    }

    /**
     * Retrieves the bitmap image from the recipe picture url link.
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        // stores the image view to display the picture to
        ImageView imageView;

        /**
         * Creates a DownloadImageTask with an image view.
         * @param imageView the image view to display to
         */
        public DownloadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /**
         * Retrieves the recipe bitmap image from the url provided.
         * @param urls the url containing the recipe image url
         * @return the recipe bitmap image
         */
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
                System.out.println(bitmap.getByteCount());
            } catch (Exception e) {
                Log.e("Error getting image", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        /**
         * Sets the image view's display to be the resulting bitmap.
         * @param result the bitmap to be displayed in the image view
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
