package com.spendzy.dao;

import com.spendzy.model.ExpenseCategory;
import java.util.List;

public interface ExpenseCategoryDAO {
    boolean addExpenseCategory(ExpenseCategory category);
    ExpenseCategory getExpenseCategoryById(int expenseCategoryId);
    List<ExpenseCategory> getExpenseCategoriesByUser(int userId);
    boolean updateExpenseCategory(ExpenseCategory category);
    boolean deleteExpenseCategory(int expenseCategoryId);
}
