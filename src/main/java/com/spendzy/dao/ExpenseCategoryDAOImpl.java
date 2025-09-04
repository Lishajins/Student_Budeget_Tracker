package com.spendzy.dao;

import com.spendzy.db.DBUtility;
import com.spendzy.model.ExpenseCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseCategoryDAOImpl implements ExpenseCategoryDAO {

    @Override
    public boolean addExpenseCategory(ExpenseCategory category) {
        String query = "INSERT INTO expensecategories(name, user_id) VALUES(?, ?)";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getUserId());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) category.setExpenseCategoryId(keys.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ExpenseCategory getExpenseCategoryById(int expenseCategoryId) {
        String query = "SELECT * FROM expensecategories WHERE expense_category_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, expenseCategoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ExpenseCategory(
                        rs.getInt("expense_category_id"),
                        rs.getString("name"),
                        rs.getInt("user_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ExpenseCategory> getExpenseCategoriesByUser(int userId) {
        List<ExpenseCategory> list = new ArrayList<>();
        String query = "SELECT * FROM expensecategories WHERE user_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ExpenseCategory(
                        rs.getInt("expense_category_id"),
                        rs.getString("name"),
                        rs.getInt("user_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateExpenseCategory(ExpenseCategory category) {
        String query = "UPDATE expensecategories SET name=?, user_id=? WHERE expense_category_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getUserId());
            ps.setInt(3, category.getExpenseCategoryId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteExpenseCategory(int expenseCategoryId) {
        String query = "DELETE FROM expensecategories WHERE expense_category_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, expenseCategoryId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
