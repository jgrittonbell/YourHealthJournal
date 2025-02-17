-- Create the YourHealthJournal database
CREATE DATABASE IF NOT EXISTS YourHealthJournal;
USE YourHealthJournal;

-- Create the FoodEntry table
CREATE TABLE IF NOT EXISTS FoodEntry (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
food_name VARCHAR(255) NOT NULL,
time_eaten DATETIME NOT NULL,
meal_category VARCHAR(50) DEFAULT 'uncategorized',
fat DECIMAL(5,2) NOT NULL,
protein DECIMAL(5,2) NOT NULL,
carbs DECIMAL(5,2) NOT NULL,
calories DECIMAL(6,2) NOT NULL,
cholesterol DECIMAL(5,2) NULL,
sodium DECIMAL(5,2) NULL,
fiber DECIMAL(5,2) NULL,
sugar DECIMAL(5,2) NULL,
added_sugar DECIMAL(5,2) NULL,
vitamin_d DECIMAL(5,2) NULL,
calcium DECIMAL(5,2) NULL,
iron DECIMAL(5,2) NULL,
potassium DECIMAL(5,2) NULL,
notes TEXT NULL,
created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
