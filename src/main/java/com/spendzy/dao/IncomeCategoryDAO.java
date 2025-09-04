package com.spendzy.dao;

import com.spendzy.model.IncomeCategory;
import java.util.List;

public interface IncomeCategoryDAO {
    boolean addCategory(IncomeCategory category);
    IncomeCategory getCategoryById(int incomeCategoryId);
    List<IncomeCategory> getAllCategories(int userId);
    boolean updateCategory(IncomeCategory category);
    boolean deleteCategory(int incomeCategoryId);
}
