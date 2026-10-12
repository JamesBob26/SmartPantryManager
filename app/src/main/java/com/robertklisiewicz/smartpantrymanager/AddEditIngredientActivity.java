package com.robertklisiewicz.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText etIngredientName, etQuantity, etUnit, etExpiryDate;
    private Button btnSaveIngredient;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // Initialize Views
        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);

        dbHelper = new DatabaseHelper(this);

        btnSaveIngredient.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {
        String name = etIngredientName.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiryDate.getText().toString().trim();

        // Assignment Requirement: Input Validation
        if (name.isEmpty() || quantityStr.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, "Please fill in Name, Quantity, and Unit", Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number for quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the object and insert into SQLite
        PantryItem newItem = new PantryItem(0, name, quantity, unit, expiry);
        long result = dbHelper.insertPantryItem(newItem);

        if (result != -1) {
            Toast.makeText(this, "Ingredient saved", Toast.LENGTH_SHORT).show();
            finish(); // Closes this screen and returns to MainActivity
        } else {
            Toast.makeText(this, "Error saving ingredient", Toast.LENGTH_SHORT).show();
        }
    }
}

