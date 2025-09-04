// ExpenseService.java
package com.spendzy.service;

import com.spendzy.dao.ExpenseDAO;
import com.spendzy.dao.ExpenseDAOImpl;
import com.spendzy.model.Expense;

import java.util.List;

public class ExpenseService {
    private final ExpenseDAO expenseDAO = new ExpenseDAOImpl();

    public boolean addExpense(Expense expense) {
        return expenseDAO.addExpense(expense);
    }

    public Expense getExpenseById(int expenseId) {
        return expenseDAO.getExpenseById(expenseId);
    }

    public List<Expense> getExpensesByUserId(int userId) {
        return expenseDAO.getExpensesByUserId(userId);
    }

    public boolean updateExpense(Expense expense) {
        return expenseDAO.updateExpense(expense);
    }

    public boolean deleteExpense(int expenseId) {
        return expenseDAO.deleteExpense(expenseId);
    }
}
