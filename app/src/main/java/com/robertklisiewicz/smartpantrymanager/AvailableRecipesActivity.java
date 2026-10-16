package com.robertklisiewicz.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AvailableRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRecipes;
    private TextView tvNoRecipes;
    private DatabaseHelper dbHelper;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_recipes);

        // Initialize Views
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        tvNoRecipes = findViewById(R.id.tvNoRecipes);

        // Setup RecyclerView
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Database
        dbHelper = new DatabaseHelper(this);

        // Load and match recipes
        loadMatchingRecipes();
    }

    private void loadMatchingRecipes() {
        // 1. Get current pantry inventory
        List<PantryItem> currentPantry = dbHelper.getAllPantryItems();

        // 2. Get all available recipes from the database
        List<Recipe> allRecipes = dbHelper.getAllRecipes();

        // 3. Run the strict-matching algorithm
        List<Recipe> matchedRecipes = RecipeMatcher.getMatchingRecipes(currentPantry, allRecipes);

        // 4. Handle UI based on results
        if (matchedRecipes.isEmpty()) {
            tvNoRecipes.setVisibility(View.VISIBLE);
            recyclerViewRecipes.setVisibility(View.GONE);
        } else {
            tvNoRecipes.setVisibility(View.GONE);
            recyclerViewRecipes.setVisibility(View.VISIBLE);

            // Bind the matched recipes to the RecyclerView
            recipeAdapter = new RecipeAdapter(matchedRecipes);
            recyclerViewRecipes.setAdapter(recipeAdapter);
        }
    }
}

