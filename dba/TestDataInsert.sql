-- Use the test database if needed
USE YourHealthJournal_test;

-- Insert Users (id is auto-generated)
INSERT INTO Users (cognito_id, first_name, last_name, email, created_at) VALUES
('user-001', 'John', 'Doe', 'john.doe@example.com', NOW()),
('user-002', 'Jane', 'Smith', 'jane.smith@example.com', NOW()),
('user-003', 'Michael', 'Brown', 'michael.brown@example.com', NOW());

-- Insert Foods
INSERT INTO Food (food_name, fat, protein, carbs, calories, cholesterol, sodium, fiber, sugar, added_sugar, vitamin_d, calcium, iron, potassium, notes) VALUES
('Grilled Chicken Breast', 3.50, 31.00, 0.00, 165.00, 85.00, 75.00, 0.00, 0.00, NULL, 0.00, 15.00, 0.80, 256.00, 'Lean protein source'),
('Oatmeal with Banana', 3.00, 6.00, 27.00, 154.00, 0.00, 2.00, 4.00, 9.00, NULL, 1.00, 15.00, 1.50, 164.00, 'Healthy breakfast option'),
('Salmon with Steamed Broccoli', 15.00, 35.00, 10.00, 380.00, 80.00, 400.00, 5.00, 1.00, NULL, 2.00, 180.00, 1.80, 400.00, 'High-protein dinner with omega-3'),
('Avocado Toast', 14.00, 5.00, 18.00, 200.00, 0.00, 150.00, 7.00, 2.00, NULL, 0.00, 20.00, 0.90, 300.00, 'Good fats and fiber'),
('Greek Yogurt with Honey', 4.00, 10.00, 18.00, 150.00, 5.00, 55.00, 0.00, 14.00, 10.00, 2.00, 110.00, 0.80, 160.00, 'Protein-rich snack with probiotics');

-- Insert Meals (user_id = 1, 2, 3 manually match to Users inserted in order)
INSERT INTO Meal (user_id, meal_name, time_eaten, is_favorite) VALUES
(1, 'Healthy Breakfast', '2024-02-17 08:00:00', TRUE),
(2, 'Protein-Packed Lunch', '2024-02-17 12:30:00', FALSE),
(3, 'Omega-3 Rich Dinner', '2024-02-17 19:00:00', TRUE);

-- Insert FoodMealJournal
INSERT INTO FoodMealJournal (meal_id, food_id, serving_size) VALUES
(1, 2, 1.5),
(1, 4, 2.0),
(2, 1, 1.0),
(2, 5, 1.5),
(3, 3, 1.0);

-- Insert FavoriteItems
INSERT INTO FavoriteItems (user_id, meal_id, food_id, is_favorite) VALUES
(1, 1, NULL, TRUE),
(1, NULL, 3, TRUE),
(2, NULL, 5, TRUE),
(3, 3, NULL, TRUE);

-- Insert GlucoseReadings
INSERT INTO GlucoseReading (user_id, glucose_level, measurement_time, measurement_source, notes) VALUES
(1, 110.5, '2024-02-17 07:45:00', 'Manual', 'Before breakfast'),
(1, 145.2, '2024-02-17 12:15:00', 'Dexcom', 'Post-lunch spike'),
(2, 98.0, '2024-02-17 18:50:00', 'Nightscout', 'Before dinner'),
(3, 120.3, '2024-02-17 22:30:00', 'Manual', 'Bedtime reading');