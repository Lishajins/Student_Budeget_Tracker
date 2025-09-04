package com.spendzy.service;

import com.spendzy.dao.IncomeCategoryDAO;
import com.spendzy.dao.IncomeCategoryDAOImpl;
import com.spendzy.model.IncomeCategory;

import java.util.List;

public class IncomeCategoryService {
    private final IncomeCategoryDAO categoryDAO = new IncomeCategoryDAOImpl();

    public boolean addCategory(IncomeCategory category) {
        return categoryDAO.addCategory(category);
    }

    public IncomeCategory getCategoryById(int categoryId) {
        return categoryDAO.getCategoryById(categoryId);
    }

    public List<IncomeCategory> getAllCategories(int userId) {
        return categoryDAO.getAllCategories(userId);
    }

    public boolean updateCategory(IncomeCategory category) {
        return categoryDAO.updateCategory(category);
    }

    public boolean deleteCategory(int categoryId) {
        return categoryDAO.deleteCategory(categoryId);
    }
}
