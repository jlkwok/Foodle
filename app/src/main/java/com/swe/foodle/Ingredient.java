package com.swe.foodle;

/**
 * Stores info about an ingredient needed for displaying to the user.
 */
public class Ingredient {
    // stores the name of the ingredient
    private String name;
    // stores the number of calories associated with the ingredient
    private double calories;
    // stores the number of the ingredient
    private int quantity;

    /**
     * Creates an Ingredient with a name, number of calories, and quantity.
     * @param name the name of the ingredient
     */
    public Ingredient(String name){
        this.name = name;
        this.calories = 0;
        this.quantity = 1;
    }

    /**
     * Creates an Ingredient with a name, number of calories, and quantity.
     * @param name the name of the ingredient
     * @param calories the ingredient's number of calories
     */
    public Ingredient(String name, double calories){
        this.name = name;
        this.calories = calories;
        this.quantity = 1;
    }

    /**
     * Creates an Ingredient with a name, number of calories, and quantity.
     * @param name the name of the ingredient
     * @param calories the ingredient's number of calories
     * @param quantity the ingredient's quantity
     */
    public Ingredient(String name, double calories, int quantity){
        this.name = name;
        this.calories = calories;
        this.quantity = quantity;
    }

    /**
     * @return the name of the ingredient
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return the ingredient's number of calories
     */
    public double getCalories(){
        return this.calories;
    }

    /**
     * @return the ingredient's quantity
     */
    public int getQuantity(){
        return this.quantity;
    }

    /**
     * Sets the ingredient's quantity to the specified amount.
     * @param quantity the quantity of this ingredient
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * Determines if an object is equivalent to this Ingredient.
     * @param o the object to compare to
     * @return whether this Ingredient and object are equivalent
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Ingredient){
            Ingredient cmp = (Ingredient) o;
            return getName().equals(((Ingredient) o).getName());
        }
        else return false;
    }
}
