# Smart Pantry Manager

## App Description
Smart Pantry Manager is a native Android application built with Java and Android Studio. It allows users to effortlessly manage their kitchen inventory (adding, editing, and deleting ingredients) and uses a strict-matching algorithm to suggest available recipes based on the exact ingredients and quantities currently in the user's pantry.

## Setup and Run Instructions
1. Clone this repository or extract the project ZIP file.
2. Open **Android Studio**.
3. Select **File > Open** and choose the `SmartPantryManager` folder.
4. Wait for Gradle to finish syncing the project.
5. Select a virtual device (e.g., Pixel 6 or Pixel 7 running API 33/34) from the Device Manager, or connect a physical Android device.
6. Click the green **Run 'app'** button (or press Shift + F10) in the top toolbar to install and launch the application.

## Database Justification (SQLite)
SQLite was chosen as the database for this application because it is natively embedded within the Android operating system and requires zero external server configuration or network connectivity. Since Smart Pantry Manager is designed as a personal, on-the-go utility, storing data locally ensures the app is lightning-fast, fully functional offline, and respects user privacy. Furthermore, SQLite easily handles the relational data model required to link the `Pantry`, `Recipes`, and `Recipe_Ingredients` tables, making it the perfect lightweight, persistent storage solution for this project's requirements.
