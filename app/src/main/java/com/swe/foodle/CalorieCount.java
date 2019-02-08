package com.swe.foodle;

import java.util.List;

/**
 * Calculates the total number of calories of the ingredients listed.
 */
public class CalorieCount {
    // stores the total number of calories
    private double totalCalories;
    // stores the ingredients the total calories will be calculated for
    private List<Ingredient> ingredients;
    // stores a singleton pattern of CalorieCount
    private static CalorieCount instance = new CalorieCount();

    /**
     * Creates a default CalorieCount object.
     */
    private CalorieCount(){
    }

    /**
     * @return the single instance of CalorieCount
     */
    public static CalorieCount getInstance(){
        return instance;
    }

    /**
     * @return the total calories of the ingredients
     */
    public double getTotalCalories(){
       return this.totalCalories;
    }

    /**
     * @return the ingredients total calories will be calculated for
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Sets the ingredients to calculate total calories.
     * @param ingredients the ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Sets the total calories.
     * @param totalCalories the total number of calories
     */
    public void setTotalCalories(double totalCalories){
        this.totalCalories = totalCalories;
    }

    /**
     * Adds the ingredient to the list of ingredients.
     * @param ingredient the ingredient to be added
     * @return whether the ingredient has been added successfully
     */
    public boolean addIngredient(Ingredient ingredient){
        if (getIngredients().contains(ingredient)){
            return false;
        }
        else{
            getIngredients().add(ingredient);
            setTotalCalories(getTotalCalories() + (ingredient.getCalories() * ingredient.getQuantity()));
            return true;
        }
    }

    /**
     * Removes the ingredient from the list of ingredients.
     * @param ingredient the ingredient to be removed
     * @return whether the ingredient has been removed successfully
     */
    public boolean removeIngredient(Ingredient ingredient){
        boolean isRemoved = getIngredients().remove(ingredient);
        if (isRemoved){
            setTotalCalories(getTotalCalories() - (ingredient.getCalories() * ingredient.getQuantity()));
        }
        return isRemoved;
    }

    /**
     * Updates the ingredient quantity to the new quantity specified.
     * @param ingredient the ingredient whose quantity will be changed
     * @param newQuantity the new quantity of the ingredient
     */
    public void changeIngredientQuantity(Ingredient ingredient, int newQuantity){
        int ingredientIndex = getIngredients().indexOf(ingredient);
        Ingredient ingredientToChange = getIngredients().get(ingredientIndex);
        double newTotal = getTotalCalories() + ((newQuantity - ingredientToChange.getQuantity()) * ingredientToChange.getCalories());
        setTotalCalories(newTotal);
        ingredientToChange.setQuantity(newQuantity);
    }

    /**
     * Calculates the total number of calories based on the ingredients provided.
     * @param ingredients the ingredients the total number of calories will be calculated for
     * @return the sum of the calories of the ingredients
     */
    public double calculateTotalCalories(List<Ingredient> ingredients){
        double totalCalories = 0;
        for (Ingredient ingredient : ingredients){
            totalCalories += (ingredient.getCalories() * ingredient.getQuantity());
        }
        return totalCalories;
    }
}
