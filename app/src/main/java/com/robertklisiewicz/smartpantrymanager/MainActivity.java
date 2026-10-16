package com.robertklisiewicz.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPantry;
    private PantryAdapter pantryAdapter;
    private DatabaseHelper dbHelper;
    private List<PantryItem> pantryList;
    private TextView tvEmptyPantry;
    private FloatingActionButton fabAddIngredient;
    private Button btnFindRecipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        recyclerViewPantry = findViewById(R.id.recyclerViewPantry);
        tvEmptyPantry = findViewById(R.id.tvEmptyPantry);
        fabAddIngredient = findViewById(R.id.fabAddIngredient);
        btnFindRecipes = findViewById(R.id.btnFindRecipes);

        // Setup RecyclerView
        recyclerViewPantry.setLayoutManager(new LinearLayoutManager(this));
        pantryList = new ArrayList<>();

        // Initialize Database
        dbHelper = new DatabaseHelper(this);

        // Setup Floating Action Button to open the Add form
        fabAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });

        // Open the available recipes screen when the button is clicked
        btnFindRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AvailableRecipesActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryData();
    }

    private void loadPantryData() {
        pantryList.clear();
        pantryList.addAll(dbHelper.getAllPantryItems());

        if (pantryAdapter == null) {
            pantryAdapter = new PantryAdapter(pantryList);
            recyclerViewPantry.setAdapter(pantryAdapter);
        } else {
            pantryAdapter.notifyDataSetChanged();
        }

        // Handle the empty state requirement
        if (pantryList.isEmpty()) {
            tvEmptyPantry.setVisibility(View.VISIBLE);
            recyclerViewPantry.setVisibility(View.GONE);
        } else {
            tvEmptyPantry.setVisibility(View.GONE);
            recyclerViewPantry.setVisibility(View.VISIBLE);
        }
    }
}
