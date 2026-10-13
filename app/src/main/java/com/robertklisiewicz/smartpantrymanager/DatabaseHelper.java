package com.robertklisiewicz.smartpantrymanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_PANTRY = "pantry";
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";

    // Pantry Table Columns
    public static final String COLUMN_PANTRY_ID = "id";
    public static final String COLUMN_PANTRY_NAME = "ingredient_name";
    public static final String COLUMN_PANTRY_QTY = "quantity";
    public static final String COLUMN_PANTRY_UNIT = "unit";
    public static final String COLUMN_PANTRY_EXPIRY = "expiry_date";

    // Recipes Table Columns
    public static final String COLUMN_RECIPE_ID = "id";
    public static final String COLUMN_RECIPE_NAME = "recipe_name";
    public static final String COLUMN_RECIPE_STEPS = "preparation_steps";

    // Recipe Ingredients Table Columns (Links ingredients to specific recipes)
    public static final String COLUMN_RI_ID = "id";
    public static final String COLUMN_RI_RECIPE_ID = "recipe_id";
    public static final String COLUMN_RI_NAME = "ingredient_name";
    public static final String COLUMN_RI_REQ_QTY = "required_quantity";
    public static final String COLUMN_RI_UNIT = "unit";

    // CREATE TABLE Statements
    private static final String CREATE_TABLE_PANTRY = "CREATE TABLE " + TABLE_PANTRY + " ("
            + COLUMN_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PANTRY_NAME + " TEXT, "
            + COLUMN_PANTRY_QTY + " REAL, "
            + COLUMN_PANTRY_UNIT + " TEXT, "
            + COLUMN_PANTRY_EXPIRY + " TEXT);";

    private static final String CREATE_TABLE_RECIPES = "CREATE TABLE " + TABLE_RECIPES + " ("
            + COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RECIPE_NAME + " TEXT, "
            + COLUMN_RECIPE_STEPS + " TEXT);";

    private static final String CREATE_TABLE_RECIPE_INGREDIENTS = "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " ("
            + COLUMN_RI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RI_RECIPE_ID + " INTEGER, "
            + COLUMN_RI_NAME + " TEXT, "
            + COLUMN_RI_REQ_QTY + " REAL, "
            + COLUMN_RI_UNIT + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_RI_RECIPE_ID + ") REFERENCES " + TABLE_RECIPES + "(" + COLUMN_RECIPE_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute the creation of the tables
        db.execSQL(CREATE_TABLE_PANTRY);
        db.execSQL(CREATE_TABLE_RECIPES);
        db.execSQL(CREATE_TABLE_RECIPE_INGREDIENTS);

        // Call the seeder method
        seedRecipes(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        // Create tables again
        onCreate(db);
    }

    // Database Seeding Methods

    private void seedRecipes(SQLiteDatabase db) {
        // Recipe 1: Basic Omelette
        long idOmelette = insertRecipe(db, "Basic Omelette", "Crack eggs into a bowl, whisk, and cook in a pan until set.");
        insertRecipeIngredient(db, idOmelette, "Egg", 3, "pcs");
        insertRecipeIngredient(db, idOmelette, "Butter", 10, "g");

        // Recipe 2: Tomato Pasta
        long idPasta = insertRecipe(db, "Tomato Pasta", "Boil pasta. Blend tomatoes and garlic, simmer, and mix with pasta.");
        insertRecipeIngredient(db, idPasta, "Pasta", 200, "g");
        insertRecipeIngredient(db, idPasta, "Tomato", 3, "pcs");
        insertRecipeIngredient(db, idPasta, "Garlic", 1, "clove");

        // Recipe 3: Grilled Cheese Sandwich
        long idCheeseSandwich = insertRecipe(db, "Grilled Cheese Sandwich", "Butter bread, place cheese between slices, and grill in a pan until golden.");
        insertRecipeIngredient(db, idCheeseSandwich, "Bread", 2, "slices");
        insertRecipeIngredient(db, idCheeseSandwich, "Cheese", 2, "slices");
        insertRecipeIngredient(db, idCheeseSandwich, "Butter", 15, "g");

        // Recipe 4: Simple Potato Salad
        long idPotatoSalad = insertRecipe(db, "Simple Potato Salad", "Boil potatoes until soft. Chop and mix with mayonnaise.");
        insertRecipeIngredient(db, idPotatoSalad, "Potato", 4, "pcs");
        insertRecipeIngredient(db, idPotatoSalad, "Mayonnaise", 3, "tbsp");

        // Recipe 5: Garlic Bread
        long idGarlicBread = insertRecipe(db, "Garlic Bread", "Mix crushed garlic with butter. Spread on bread and bake.");
        insertRecipeIngredient(db, idGarlicBread, "Bread", 4, "slices");
        insertRecipeIngredient(db, idGarlicBread, "Garlic", 2, "cloves");
        insertRecipeIngredient(db, idGarlicBread, "Butter", 30, "g");

        // Recipe 6: Pancakes
        long idPancakes = insertRecipe(db, "Pancakes", "Mix flour, milk, egg, and sugar. Pour batter onto a hot buttered pan and flip when bubbly.");
        insertRecipeIngredient(db, idPancakes, "Flour", 200, "g");
        insertRecipeIngredient(db, idPancakes, "Milk", 250, "ml");
        insertRecipeIngredient(db, idPancakes, "Egg", 1, "pcs");
        insertRecipeIngredient(db, idPancakes, "Sugar", 2, "tbsp");

        // Recipe 7: Peanut Butter & Jelly Sandwich
        long idPbj = insertRecipe(db, "PB&J Sandwich", "Spread peanut butter on one slice of bread and jelly on the other. Press together.");
        insertRecipeIngredient(db, idPbj, "Bread", 2, "slices");
        insertRecipeIngredient(db, idPbj, "Peanut Butter", 2, "tbsp");
        insertRecipeIngredient(db, idPbj, "Jelly", 2, "tbsp");

        // Recipe 8: Scrambled Eggs
        long idScrambled = insertRecipe(db, "Scrambled Eggs", "Whisk eggs with a splash of milk. Cook in a buttered pan over medium heat, stirring gently.");
        insertRecipeIngredient(db, idScrambled, "Egg", 3, "pcs");
        insertRecipeIngredient(db, idScrambled, "Milk", 2, "tbsp");
        insertRecipeIngredient(db, idScrambled, "Butter", 10, "g");

        // Recipe 9: Simple Fruit Salad
        long idFruitSalad = insertRecipe(db, "Simple Fruit Salad", "Chop the apple, slice the banana, and peel/segment the orange. Mix in a bowl.");
        insertRecipeIngredient(db, idFruitSalad, "Apple", 1, "pcs");
        insertRecipeIngredient(db, idFruitSalad, "Banana", 1, "pcs");
        insertRecipeIngredient(db, idFruitSalad, "Orange", 1, "pcs");

        // Recipe 10: French Toast
        long idFrenchToast = insertRecipe(db, "French Toast", "Whisk egg, milk, and cinnamon. Dip bread slices in mixture and fry in butter until browned.");
        insertRecipeIngredient(db, idFrenchToast, "Bread", 2, "slices");
        insertRecipeIngredient(db, idFrenchToast, "Egg", 1, "pcs");
        insertRecipeIngredient(db, idFrenchToast, "Milk", 50, "ml");
        insertRecipeIngredient(db, idFrenchToast, "Cinnamon", 1, "tsp");
        insertRecipeIngredient(db, idFrenchToast, "Butter", 10, "g");

        // Recipe 11: Rice and Beans
        long idRiceBeans = insertRecipe(db, "Rice and Beans", "Boil rice until cooked. Sauté chopped onion, add canned beans, and serve over rice.");
        insertRecipeIngredient(db, idRiceBeans, "Rice", 150, "g");
        insertRecipeIngredient(db, idRiceBeans, "Canned Beans", 1, "can");
        insertRecipeIngredient(db, idRiceBeans, "Onion", 1, "pcs");

        // Recipe 12: Simple Chicken Stir Fry
        long idStirFry = insertRecipe(db, "Chicken Stir Fry", "Dice chicken and chop broccoli. Fry chicken in oil, add broccoli and soy sauce, cook until tender.");
        insertRecipeIngredient(db, idStirFry, "Chicken Breast", 2, "pcs");
        insertRecipeIngredient(db, idStirFry, "Broccoli", 1, "head");
        insertRecipeIngredient(db, idStirFry, "Soy Sauce", 3, "tbsp");
        insertRecipeIngredient(db, idStirFry, "Oil", 1, "tbsp");

        // Recipe 13: Macaroni and Cheese
        long idMacCheese = insertRecipe(db, "Macaroni and Cheese", "Boil macaroni. Drain, then stir in butter, milk, and shredded cheese until melted.");
        insertRecipeIngredient(db, idMacCheese, "Macaroni", 200, "g");
        insertRecipeIngredient(db, idMacCheese, "Cheese", 100, "g");
        insertRecipeIngredient(db, idMacCheese, "Milk", 50, "ml");
        insertRecipeIngredient(db, idMacCheese, "Butter", 20, "g");

        // Recipe 14: Tuna Salad
        long idTunaSalad = insertRecipe(db, "Tuna Salad", "Drain tuna. Finely chop celery and mix with tuna and mayonnaise.");
        insertRecipeIngredient(db, idTunaSalad, "Canned Tuna", 1, "can");
        insertRecipeIngredient(db, idTunaSalad, "Mayonnaise", 3, "tbsp");
        insertRecipeIngredient(db, idTunaSalad, "Celery", 2, "stalks");

        // Recipe 15: Avocado Toast
        long idAvoToast = insertRecipe(db, "Avocado Toast", "Toast bread. Mash avocado with salt and lemon juice, then spread on toast.");
        insertRecipeIngredient(db, idAvoToast, "Bread", 2, "slices");
        insertRecipeIngredient(db, idAvoToast, "Avocado", 1, "pcs");
        insertRecipeIngredient(db, idAvoToast, "Lemon Juice", 1, "tsp");

    }

    private long insertRecipe(SQLiteDatabase db, String name, String steps) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_RECIPE_NAME, name);
        values.put(COLUMN_RECIPE_STEPS, steps);

        // insert() returns the newly created row ID
        return db.insert(TABLE_RECIPES, null, values);
    }

    private void insertRecipeIngredient(SQLiteDatabase db, long recipeId, String name, double qty, String unit) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_RI_RECIPE_ID, recipeId);
        values.put(COLUMN_RI_NAME, name);
        values.put(COLUMN_RI_REQ_QTY, qty);
        values.put(COLUMN_RI_UNIT, unit);

        db.insert(TABLE_RECIPE_INGREDIENTS, null, values);
    }

    // Pantry CRUD Operations

    // 1. Create
    public long insertPantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_PANTRY_NAME, item.getName());
        values.put(COLUMN_PANTRY_QTY, item.getQuantity());
        values.put(COLUMN_PANTRY_UNIT, item.getUnit());
        values.put(COLUMN_PANTRY_EXPIRY, item.getExpiryDate());

        long id = db.insert(TABLE_PANTRY, null, values);
        db.close();
        return id;
    }

    // 2. Read (Get all items for the RecyclerView list)
    public java.util.List<PantryItem> getAllPantryItems() {
        java.util.List<PantryItem> pantryList = new java.util.ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PANTRY;

        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PantryItem item = new PantryItem();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_ID)));
                item.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_NAME)));
                item.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_QTY)));
                item.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_UNIT)));
                item.setExpiryDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_EXPIRY)));

                pantryList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pantryList;
    }

    // 3. Update
    public int updatePantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_PANTRY_NAME, item.getName());
        values.put(COLUMN_PANTRY_QTY, item.getQuantity());
        values.put(COLUMN_PANTRY_UNIT, item.getUnit());
        values.put(COLUMN_PANTRY_EXPIRY, item.getExpiryDate());

        // updating row
        int rowsAffected = db.update(TABLE_PANTRY, values, COLUMN_PANTRY_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
        return rowsAffected;
    }

    // 4. Delete
    public void deletePantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PANTRY, COLUMN_PANTRY_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // 1. Get all recipes
        String recipeQuery = "SELECT * FROM Recipes";
        Cursor recipeCursor = db.rawQuery(recipeQuery, null);

        if (recipeCursor.moveToFirst()) {
            do {
                int recipeId = recipeCursor.getInt(recipeCursor.getColumnIndexOrThrow("id"));
                String recipeName = recipeCursor.getString(recipeCursor.getColumnIndexOrThrow("name"));
                String instructions = recipeCursor.getString(recipeCursor.getColumnIndexOrThrow("instructions"));

                Recipe recipe = new Recipe(recipeId, recipeName, instructions);

                // 2. Get all ingredients for this specific recipe
                String ingredientQuery = "SELECT * FROM Recipe_Ingredients WHERE recipe_id = ?";
                Cursor ingredientCursor = db.rawQuery(ingredientQuery, new String[]{String.valueOf(recipeId)});

                if (ingredientCursor.moveToFirst()) {
                    do {
                        String ingName = ingredientCursor.getString(ingredientCursor.getColumnIndexOrThrow("ingredient_name"));
                        double quantity = ingredientCursor.getDouble(ingredientCursor.getColumnIndexOrThrow("quantity"));
                        String unit = ingredientCursor.getString(ingredientCursor.getColumnIndexOrThrow("unit"));

                        // Add the ingredient to the recipe object
                        recipe.addIngredient(new RecipeIngredient(ingName, quantity, unit));
                    } while (ingredientCursor.moveToNext());
                }
                ingredientCursor.close();

                // Add the fully populated recipe to the main list
                recipeList.add(recipe);

            } while (recipeCursor.moveToNext());
        }
        recipeCursor.close();

        return recipeList;
    }
}
