

## Spendzy — Student Expense Tracker

Spendzy is a desktop application built with **Java Swing** and **MySQL**, designed to help students track their income, expenses, and budgets efficiently.
It features a clean dashboard, categorized tracking, and dynamic visual updates.


### Tech Stack

* **Frontend/UI:** Java Swing
* **Database:** MySQL
* **ORM Layer:** JDBC
* **Build Tool:** Maven
* **Java Version:** 17+


## ⚙️ Setup Instructions

### 1️⃣ Create the Database

Run the following SQL script in your MySQL Workbench or terminal:

```sql
-- Create database
CREATE DATABASE StudentExpenseTracker;
USE StudentExpenseTracker;

-- Users table
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- Income Categories (Salary, Freelance, Gift, etc.)
CREATE TABLE IncomeCategories (
    income_category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- Income (money coming in)
CREATE TABLE Income (
    income_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    category_id INT,
    amount DECIMAL(10,2) NOT NULL,
    income_name VARCHAR(100),
    date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES IncomeCategories(income_category_id) ON DELETE SET NULL
);

-- Expense Categories (Food, Rent, Entertainment, etc.)
CREATE TABLE ExpenseCategories (
    expense_category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- Expenses (money going out)
CREATE TABLE Expenses (
    expense_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    category_id INT,
    amount DECIMAL(10,2) NOT NULL,
    description VARCHAR(200),
    date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES ExpenseCategories(expense_category_id) ON DELETE SET NULL
);

-- Budget (set only for expense categories)
CREATE TABLE Budget (
    budget_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    category_id INT, -- linked to ExpenseCategories
    amount_limit DECIMAL(10,2) NOT NULL,
    period ENUM('weekly','monthly','yearly') DEFAULT 'monthly',
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES ExpenseCategories(expense_category_id) ON DELETE SET NULL
);
```


### 2️⃣ Update Your `DatabaseConfig.java`

Make sure your database connection file matches your MySQL credentials:

```java
public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/StudentExpenseTracker";
    private static final String USER = "root";
    private static final String PASSWORD = "your_mysql_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

---

### 3️⃣ Run the Application

You can run Spendzy from IntelliJ or command line:

```bash
mvn clean install
java -jar target/Spendzy.jar
```

---

## 🧾 Features

| Module              | Description                                                  |
| ------------------- | ------------------------------------------------------------ |
| **Dashboard**       | Shows total income, expenses, budget, and remaining balance. |
| **Income Tracker**  | Add, categorize, and delete income records.                  |
| **Expense Tracker** | Log expenses by category and date.                           |
| **Budget Manager**  | Set spending limits for each expense category.               |
| **User Accounts**   | Signup, login, and personalized tracking.                    |



## 🧑‍💻 Developer Notes

* Keep database running while using the app.
* Make sure your `mysql-connector-j` dependency is in your `pom.xml`.
* For any “Table doesn’t exist” errors, check schema spelling and run the SQL above again.

