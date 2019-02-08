package com.swe.foodle;

/**
 * Stores info about a recipe needed for displaying to the user.
 */
public class Recipe {
    // stores the name of the recipe
    private String name;
    // stores the link to the recipe
    private String link;
    // stores the source url to the recipe
    private String picture;

    /**
     * Creates a Recipe with a recipe name, link, and picture.
     * @param name the recipe name
     * @param link the recipe link
     * @param picture the recipe picture
     */
    public Recipe(String name, String link, String picture){
        this.name = name;
        this.link = link;
        this.picture = picture;
    }

    /**
     * @return the name of the recipe
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return the recipe link
     */
    public String getLink(){
        return this.link;
    }

    /**
     * @return the recipe picture
     */
    public String getPicture(){
        return this.picture;
    }

    /**
     * Determines if an object is equivalent to this Recipe.
     * @param o the object to compare to
     * @return whether this Recipe and object are equivalent
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Recipe){
            Recipe cmp = (Recipe) o;
            return getName().equals(((Recipe) o).getName()) && getLink().equals(((Recipe) o).getLink()) && getPicture().equals(((Recipe) o).getPicture());
        }
        else return false;
    }
}
