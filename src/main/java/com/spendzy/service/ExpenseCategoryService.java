// ExpenseCategoryService.java
package com.spendzy.service;

import com.spendzy.dao.ExpenseCategoryDAO;
import com.spendzy.dao.ExpenseCategoryDAOImpl;
import com.spendzy.model.ExpenseCategory;

import java.util.List;

public class ExpenseCategoryService {
    private final ExpenseCategoryDAO expenseCategoryDAO;

    public ExpenseCategoryService() {
        this.expenseCategoryDAO = new ExpenseCategoryDAOImpl();
    }

    public boolean addExpenseCategory(ExpenseCategory category) {
        return expenseCategoryDAO.addExpenseCategory(category);
    }

    public ExpenseCategory getExpenseCategoryById(int categoryId) {
        return expenseCategoryDAO.getExpenseCategoryById(categoryId);
    }

    public List<ExpenseCategory> getExpenseCategoriesByUser(int userId) {
        return expenseCategoryDAO.getExpenseCategoriesByUser(userId);
    }

    public boolean updateExpenseCategory(ExpenseCategory category) {
        return expenseCategoryDAO.updateExpenseCategory(category);
    }

    public boolean deleteExpenseCategory(int categoryId) {
        return expenseCategoryDAO.deleteExpenseCategory(categoryId);
    }
}
