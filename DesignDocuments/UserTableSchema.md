# User Table Schema

## Users Table

| Column Name     | Data Type          | Required | Description |
|---------------|------------------|----------|-------------|
| `id`         | `BIGINT` (Primary Key, Auto-Increment) | **Yes** | Unique identifier for each user |
| `full_name`  | `VARCHAR(100)` | **No** | User's full name (optional) |
| `email`      | `VARCHAR(255) UNIQUE` | **Yes** | User's email address (unique) |
| `created_at` | `TIMESTAMP DEFAULT CURRENT_TIMESTAMP` | **Yes** | Timestamp when the user account was created |

### Notes:
- This table is designed for **basic user management** without AWS Cognito integration (yet).
- The `email` field is **unique** to prevent duplicate accounts.
- The `created_at` field automatically records when the user account is created.
- This table can be **expanded later** to include AWS Cognito integration (adding `cognito_id`, etc.).
- This table links to the `FoodEntry` table via a **One-to-Many relationship**.