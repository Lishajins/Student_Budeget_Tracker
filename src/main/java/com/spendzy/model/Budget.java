package com.spendzy.model;

public class Budget {
    private int budgetId;
    private int userId;
    private int categoryId;
    private double amountLimit;
    private String period; // weekly, monthly, yearly

    public Budget(int budgetId, int userId, int categoryId, double amountLimit, String period) {
        this.budgetId = budgetId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amountLimit = amountLimit;
        this.period = period;
    }

    public Budget() {}

    public int getBudgetId() { return budgetId; }
    public void setBudgetId(int budgetId) { this.budgetId = budgetId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public double getAmountLimit() { return amountLimit; }
    public void setAmountLimit(double amountLimit) { this.amountLimit = amountLimit; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}
