package com.spendzy.model;

public class ExpenseCategory {
    private int expenseCategoryId;
    private String name;
    private int userId;

    public ExpenseCategory(int expenseCategoryId, String name, int userId) {
        this.expenseCategoryId = expenseCategoryId;
        this.name = name;
        this.userId = userId;
    }

    public ExpenseCategory() {}

    public int getExpenseCategoryId() { return expenseCategoryId; }
    public void setExpenseCategoryId(int expenseCategoryId) { this.expenseCategoryId = expenseCategoryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
