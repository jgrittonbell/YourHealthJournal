# Food Journal Database Schema

## FoodEntry Table

| Column Name      | Data Type          | Required | Description |
|-----------------|------------------|----------|-------------|
| `id`            | `BIGINT` (Primary Key, Auto-Increment) | **Yes** | Unique identifier for each food entry |
| `user_id`       | `BIGINT` (Foreign Key) | **Yes** | Links to the user who logged the entry (references `Users.id`) |
| `food_name`     | `VARCHAR(255)` | **Yes** | Name of the food |
| `time_eaten`    | `DATETIME DEFAULT CURRENT_TIMESTAMP` | **Yes** | Timestamp of when the food was eaten (user-defined, defaults to current time) |
| `meal_category` | `VARCHAR(50) DEFAULT 'uncategorized'` | **No** | (Optional) Meal type (`breakfast`, `lunch`, `dinner`, `snack`) |
| `fat`           | `DECIMAL(5,2)` | **Yes** | Total fat in grams |
| `protein`       | `DECIMAL(5,2)` | **Yes** | Total protein in grams |
| `carbs`         | `DECIMAL(5,2)` | **Yes** | Total carbohydrates in grams |
| `calories`      | `DECIMAL(6,2)` | **Yes** | Caloric value of the food |
| `cholesterol`   | `DECIMAL(5,2)` | **No** | Cholesterol in mg (optional) |
| `sodium`        | `DECIMAL(5,2)` | **No** | Sodium in mg (optional) |
| `fiber`         | `DECIMAL(5,2)` | **No** | Dietary fiber in grams (optional) |
| `sugar`         | `DECIMAL(5,2)` | **No** | Total sugar in grams (optional) |
| `added_sugar`   | `DECIMAL(5,2)` | **No** | Added sugar in grams (optional) |
| `vitamin_d`     | `DECIMAL(5,2)` | **No** | Vitamin D in mcg (optional) |
| `calcium`       | `DECIMAL(5,2)` | **No** | Calcium in mg (optional) |
| `iron`          | `DECIMAL(5,2)` | **No** | Iron in mg (optional) |
| `potassium`     | `DECIMAL(5,2)` | **No** | Potassium in mg (optional) |
| `notes`         | `TEXT` | **No** | (Optional) User comments on the meal |

### Notes:
- The `user_id` column is now a **foreign key** referencing the `Users` table.
- **Cascading delete enabled**: If a user is deleted, their food entries are also removed.
- **Food entries are editable**, allowing users to correct or delete logs.
- The **meal category is optional**, with a default of `'uncategorized'`.
