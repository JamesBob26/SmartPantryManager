package com.robertklisiewicz.smartpantrymanager;

import java.util.ArrayList;
import java.util.List;

public class RecipeMatcher {

    // Returns only the recipes the user has enough ingredients to make
    public static List<Recipe> getMatchingRecipes(List<PantryItem> pantry, List<Recipe> allRecipes) {
        List<Recipe> matchedRecipes = new ArrayList<>();

        for (Recipe recipe : allRecipes) {
            if (canMakeRecipe(pantry, recipe)) {
                matchedRecipes.add(recipe);
            }
        }

        return matchedRecipes;
    }

    // Helper method to check a single recipe against the pantry
    private static boolean canMakeRecipe(List<PantryItem> pantry, Recipe recipe) {
        // If the recipe has no ingredients listed, skip it
        if (recipe.getIngredients().isEmpty()) {
            return false;
        }

        for (RecipeIngredient reqIngredient : recipe.getIngredients()) {
            boolean hasEnough = false;

            for (PantryItem pantryItem : pantry) {
                // Check if the ingredient name matches (ignoring uppercase/lowercase differences)
                if (pantryItem.getName().equalsIgnoreCase(reqIngredient.getName())) {
                    // Strict check: Does the pantry have enough of it?
                    if (pantryItem.getQuantity() >= reqIngredient.getQuantity()) {
                        hasEnough = true;
                        break;
                    }
                }
            }

            // If even one required ingredient is missing or insufficient, we cannot make this recipe
            if (!hasEnough) {
                return false;
            }
        }

        // If we get through the whole loop, the user has enough of everything
        return true;
    }
}

