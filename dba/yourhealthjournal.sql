-- Drop the database if it exists to ensure a full reset
DROP DATABASE IF EXISTS YourHealthJournal;
CREATE DATABASE YourHealthJournal;
USE YourHealthJournal;

-- Disable FK checks to allow clean drops
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
cognito_id VARCHAR(255) NOT NULL UNIQUE,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
email VARCHAR(255) UNIQUE NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the Food table
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

-- Create the Meal table
CREATE TABLE Meal (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
meal_name VARCHAR(255) NOT NULL,
time_eaten DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
is_favorite BOOLEAN DEFAULT FALSE,
FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Create the FoodMealJournal table
CREATE TABLE FoodMealJournal (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
meal_id BIGINT NOT NULL,
food_id BIGINT NOT NULL,
serving_size DECIMAL(5,2) NOT NULL,
FOREIGN KEY (meal_id) REFERENCES Meal(id) ON DELETE CASCADE,
FOREIGN KEY (food_id) REFERENCES Food(id) ON DELETE CASCADE
);

-- Create the FavoriteItems table
CREATE TABLE FavoriteItems (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
meal_id BIGINT NULL,
food_id BIGINT NULL,
is_favorite BOOLEAN NOT NULL DEFAULT TRUE,
FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
FOREIGN KEY (meal_id) REFERENCES Meal(id) ON DELETE CASCADE,
FOREIGN KEY (food_id) REFERENCES Food(id) ON DELETE SET NULL,
CONSTRAINT unique_favorite UNIQUE (user_id, meal_id, food_id)
);

-- Create the GlucoseReading table
CREATE TABLE GlucoseReading (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
glucose_level DECIMAL(5,2) NOT NULL,
measurement_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
measurement_source VARCHAR(50) NOT NULL,
notes TEXT NULL,
FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);
