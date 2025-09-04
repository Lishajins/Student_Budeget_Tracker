package com.spendzy.service;

import com.spendzy.dao.IncomeDAO;
import com.spendzy.dao.IncomeDAOImpl;
import com.spendzy.model.Income;

import java.util.List;

public class IncomeService {

    private IncomeDAO incomeDAO = new IncomeDAOImpl();

    public void addIncome(Income income) {
        incomeDAO.addIncome(income);
    }

    public Income getIncomeById(int id) {
        return incomeDAO.getIncomeById(id);
    }

    public List<Income> getAllIncomes() {
        return incomeDAO.getAllIncomes();
    }

    public List<Income> getIncomesByUserId(int userId) {
        return incomeDAO.getIncomesByUserId(userId);
    }

    public void updateIncome(Income income) {
        incomeDAO.updateIncome(income);
    }

    public void deleteIncome(int id) {
        incomeDAO.deleteIncome(id);
    }
}
