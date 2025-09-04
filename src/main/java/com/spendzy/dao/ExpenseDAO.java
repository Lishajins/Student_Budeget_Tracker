package com.spendzy.dao;

import com.spendzy.model.Expense;
import java.util.List;

public interface ExpenseDAO {
    boolean addExpense(Expense expense);
    Expense getExpenseById(int expenseId);
    List<Expense> getExpensesByUserId(int userId);
    boolean updateExpense(Expense expense);
    boolean deleteExpense(int expenseId);
}
