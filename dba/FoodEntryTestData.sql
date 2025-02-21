-- Use the YourHealthJournal database
USE YourHealthJournal;

-- Insert test data into FoodEntry (time_eaten defaults to the current time if omitted)
INSERT INTO FoodEntry (user_id, food_name, time_eaten, meal_category, fat, protein, carbs, calories, cholesterol, sodium, fiber, sugar, added_sugar, vitamin_d, calcium, iron, potassium, notes)
VALUES
    (1, 'Grilled Chicken Breast', '2024-02-17 12:30:00', 'lunch', 3.50, 31.00, 0.00, 165.00, 85.00, 75.00, 0.00, 0.00, NULL, 0.00, 15.00, 0.80, 256.00, 'Lean protein meal'),

    (2, 'Oatmeal with Banana', '2024-02-17 08:00:00', 'breakfast', 3.00, 6.00, 27.00, 154.00, 0.00, 2.00, 4.00, 9.00, NULL, 1.00, 15.00, 1.50, 164.00, 'Healthy breakfast option'),

    (3, 'Avocado Toast', DEFAULT, 'breakfast', 14.00, 5.00, 18.00, 200.00, 0.00, 150.00, 7.00, 2.00, NULL, 0.00, 20.00, 0.90, 300.00, 'Good fats and fiber'),

    (4, 'Cheeseburger', '2024-02-16 19:30:00', 'dinner', 18.00, 22.00, 34.00, 450.00, 65.00, 780.00, 3.00, 5.00, 2.00, 0.50, 100.00, 2.50, 250.00, 'Cheat meal, high in calories'),

    (5, 'Greek Yogurt with Honey', DEFAULT, 'snack', 4.00, 10.00, 18.00, 150.00, 5.00, 55.00, 0.00, 14.00, 10.00, 2.00, 110.00, 0.80, 160.00, 'Protein-rich snack with probiotics'),

    (6, 'Caesar Salad with Chicken', '2024-02-15 13:00:00', 'lunch', 12.00, 30.00, 8.00, 320.00, 70.00, 900.00, 2.00, 3.00, NULL, 1.50, 110.00, 1.00, 200.00, 'Healthy meal but high sodium'),

    (7, 'Spaghetti with Marinara Sauce', DEFAULT, 'dinner', 5.00, 12.00, 50.00, 320.00, 15.00, 600.00, 4.00, 8.00, NULL, 0.00, 40.00, 3.00, 300.00, 'Carb-heavy meal with some protein'),

    (8, 'Apple with Peanut Butter', '2024-02-14 15:30:00', 'snack', 9.00, 7.00, 22.00, 210.00, 0.00, 150.00, 4.00, 16.00, 3.00, 0.00, 30.00, 0.60, 250.00, 'Balanced snack with healthy fats'),

    (9, 'Protein Shake', DEFAULT, 'breakfast', 2.00, 25.00, 8.00, 160.00, 10.00, 180.00, 0.00, 2.00, 1.00, 3.00, 200.00, 2.50, 450.00, 'Post-workout meal replacement'),

    (10, 'Salmon with Steamed Broccoli', '2024-02-13 19:00:00', 'dinner', 15.00, 35.00, 10.00, 380.00, 80.00, 400.00, 5.00, 1.00, NULL, 2.00, 180.00, 1.80, 400.00, 'High-protein dinner with omega-3');
