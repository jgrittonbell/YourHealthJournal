-- Drop the database if it exists to ensure a full reset
DROP DATABASE IF EXISTS YourHealthJournal;
CREATE DATABASE YourHealthJournal;
USE YourHealthJournal;

-- Drop tables if they exist (in reverse dependency order to prevent foreign key issues)
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS GlucoseReading;
DROP TABLE IF EXISTS FavoriteItems;
DROP TABLE IF EXISTS FoodMealJournal;
DROP TABLE IF EXISTS Meal;
DROP TABLE IF EXISTS Food;
DROP TABLE IF EXISTS Users;
SET FOREIGN_KEY_CHECKS = 1;

-- Create the Users table
CREATE TABLE Users (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
full_name VARCHAR(100) NOT NULL,
email VARCHAR(255) UNIQUE NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the Food table (Stores food items and their nutrition details)
CREATE TABLE Food (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
food_name VARCHAR(255) NOT NULL,
fat DECIMAL(5,2) NOT NULL,
protein DECIMAL(5,2) NOT NULL,
carbs DECIMAL(5,2) NOT NULL,
calories DECIMAL(6,2) NOT NULL,
cholesterol DECIMAL(5,2),
sodium DECIMAL(5,2),
fiber DECIMAL(5,2),
sugar DECIMAL(5,2),
added_sugar DECIMAL(5,2),
vitamin_d DECIMAL(5,2),
calcium DECIMAL(5,2),
iron DECIMAL(5,2),
potassium DECIMAL(5,2),
notes TEXT
);

-- Create the Meal table (Replaces logging food entries directly)
CREATE TABLE Meal (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
meal_name VARCHAR(255) NOT NULL,
time_eaten DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
is_favorite BOOLEAN DEFAULT FALSE,
FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Create the FoodMealJournal table (Links Food to Meals for Many-to-Many)
CREATE TABLE FoodMealJournal (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
meal_id BIGINT NOT NULL,
food_id BIGINT NOT NULL,
serving_size DECIMAL(5,2) NOT NULL,
FOREIGN KEY (meal_id) REFERENCES Meal(id) ON DELETE CASCADE,
FOREIGN KEY (food_id) REFERENCES Food(id) ON DELETE CASCADE
);

-- Create the FavoriteItems table (Stores user favorites for meals & foods)
CREATE TABLE FavoriteItems (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
meal_id BIGINT NULL,
food_id BIGINT NULL,
is_favorite BOOLEAN NOT NULL DEFAULT TRUE,
FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
FOREIGN KEY (meal_id) REFERENCES Meal(id) ON DELETE CASCADE, -- Removes favorite meals if deleted
FOREIGN KEY (food_id) REFERENCES Food(id) ON DELETE SET NULL, -- Keeps favorite foods but removes reference
CONSTRAINT unique_favorite UNIQUE (user_id, meal_id, food_id) -- Prevents duplicate favorites
);

-- Create the GlucoseReading table (Tracks glucose readings for each user)
CREATE TABLE GlucoseReading (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
glucose_level DECIMAL(5,2) NOT NULL,
measurement_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
measurement_source VARCHAR(50) NOT NULL,
notes TEXT NULL,
FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);
