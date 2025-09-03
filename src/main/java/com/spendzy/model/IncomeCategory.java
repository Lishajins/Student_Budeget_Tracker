package com.spendzy.model;

public class IncomeCategory {
    private int incomeCategoryId;
    private String name;
    private int userId;

    public IncomeCategory(int incomeCategoryId, String name, int userId) {
        this.incomeCategoryId = incomeCategoryId;
        this.name = name;
        this.userId = userId;
    }

    public IncomeCategory() {}

    public int getIncomeCategoryId() { return incomeCategoryId; }
    public void setIncomeCategoryId(int incomeCategoryId) { this.incomeCategoryId = incomeCategoryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}

