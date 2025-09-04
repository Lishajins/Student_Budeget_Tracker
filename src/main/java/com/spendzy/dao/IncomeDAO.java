package com.spendzy.dao;

import com.spendzy.model.Income;
import java.util.List;

public interface IncomeDAO {
    void addIncome(Income income);
    Income getIncomeById(int incomeId);
    List<Income> getAllIncomes();
    List<Income> getIncomesByUserId(int userId);
    void updateIncome(Income income);
    void deleteIncome(int incomeId);
}
