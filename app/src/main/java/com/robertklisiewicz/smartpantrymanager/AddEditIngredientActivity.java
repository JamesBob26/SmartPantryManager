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
    private int currentItemId = -1;
    private Button btnDeleteIngredient;



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
        btnDeleteIngredient = findViewById(R.id.btnDeleteIngredient);


        dbHelper = new DatabaseHelper(this);

        if (getIntent().hasExtra("ITEM_ID")) {
            currentItemId = getIntent().getIntExtra("ITEM_ID", -1);
            etIngredientName.setText(getIntent().getStringExtra("ITEM_NAME"));
            etQuantity.setText(String.valueOf(getIntent().getDoubleExtra("ITEM_QUANTITY", 0)));
            etUnit.setText(getIntent().getStringExtra("ITEM_UNIT"));
            btnDeleteIngredient.setVisibility(android.view.View.VISIBLE);


            String expiry = getIntent().getStringExtra("ITEM_EXPIRY");
            if (expiry != null && !expiry.isEmpty()) {
                etExpiryDate.setText(expiry);
            }

            btnSaveIngredient.setText("Update Ingredient");
        }


        btnSaveIngredient.setOnClickListener(v -> saveIngredient());

        btnDeleteIngredient.setOnClickListener(v -> {
            if (currentItemId != -1) {
                // Create a basic PantryItem just to hold the ID for the delete method
                PantryItem itemToDelete = new PantryItem(currentItemId, "", 0.0, "", "");

                dbHelper.deletePantryItem(itemToDelete);
                android.widget.Toast.makeText(this, "Ingredient deleted", android.widget.Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void saveIngredient() {
        String name = etIngredientName.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiryDate.getText().toString().trim();

        // Input Validation
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
        PantryItem item = new PantryItem(currentItemId == -1 ? 0 : currentItemId, name, quantity, unit, expiry);

        if (currentItemId == -1) {
            long result = dbHelper.insertPantryItem(item);
            if (result != -1) {
                Toast.makeText(this, "Ingredient saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error saving ingredient", Toast.LENGTH_SHORT).show();
            }
        } else {
            int result = dbHelper.updatePantryItem(item);
            if (result > 0) {
                Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error updating ingredient", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

