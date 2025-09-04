package com.spendzy.dao;

import com.spendzy.model.Budget;
import java.util.List;

public interface BudgetDAO {
    void addBudget(Budget budget);
    Budget getBudgetById(int id);
    List<Budget> getAllBudgets();
    List<Budget> getBudgetsByUser(int userId);
    void updateBudget(Budget budget);
    void deleteBudget(int id);
}
