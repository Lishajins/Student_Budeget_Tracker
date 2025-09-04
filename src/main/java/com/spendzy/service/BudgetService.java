// BudgetService.java
package com.spendzy.service;

import com.spendzy.dao.BudgetDAO;
import com.spendzy.dao.BudgetDAOImpl;
import com.spendzy.model.Budget;

import java.util.List;

public class BudgetService {
    private final BudgetDAO budgetDAO;

    public BudgetService() {
        this.budgetDAO = new BudgetDAOImpl();
    }

    public void addBudget(Budget budget) {
        budgetDAO.addBudget(budget);
    }

    public Budget getBudgetById(int budgetId) {
        return budgetDAO.getBudgetById(budgetId);
    }

    public List<Budget> getAllBudgets() {
        return budgetDAO.getAllBudgets();
    }

    public List<Budget> getAllBudgetsByUser(int userId) {
        return budgetDAO.getBudgetsByUser(userId);
    }

    public void updateBudget(Budget budget) {
        budgetDAO.updateBudget(budget);
    }

    public void deleteBudget(int budgetId) {
        budgetDAO.deleteBudget(budgetId);
    }
}
