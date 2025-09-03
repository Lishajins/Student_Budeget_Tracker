package com.spendzy.model;

import java.util.Date;

public class Income {
    private int incomeId;
    private int userId;
    private int categoryId;
    private double amount;
    private String incomeName;
    private Date date;

    public Income(int incomeId, int userId, int categoryId, double amount, String incomeName, Date date) {
        this.incomeId = incomeId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.incomeName = incomeName;
        this.date = date;
    }

    public Income() {}

    public int getIncomeId() { return incomeId; }
    public void setIncomeId(int incomeId) { this.incomeId = incomeId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getIncomeName() { return incomeName; }
    public void setIncomeName(String incomeName) { this.incomeName = incomeName; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
