package com.spendzy.dao;

import com.spendzy.db.DBUtility;
import com.spendzy.model.Budget;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAOImpl implements BudgetDAO {

    @Override
    public void addBudget(Budget budget) {
        String query = "INSERT INTO budget(user_id, category_id, amount_limit, period) VALUES(?, ?, ?, ?)";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, budget.getUserId());
            ps.setInt(2, budget.getCategoryId());
            ps.setDouble(3, budget.getAmountLimit());
            ps.setString(4, budget.getPeriod());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) budget.setBudgetId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Budget getBudgetById(int budgetId) {
        String query = "SELECT * FROM budget WHERE budget_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, budgetId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Budget(
                        rs.getInt("budget_id"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount_limit"),
                        rs.getString("period")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Budget> getAllBudgets() {
        List<Budget> list = new ArrayList<>();
        String query = "SELECT * FROM budget";
        try (Connection conn = DBUtility.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Budget(
                        rs.getInt("budget_id"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount_limit"),
                        rs.getString("period")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Budget> getBudgetsByUser(int userId) {
        List<Budget> list = new ArrayList<>();
        String query = "SELECT * FROM budget WHERE user_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Budget(
                        rs.getInt("budget_id"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount_limit"),
                        rs.getString("period")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateBudget(Budget budget) {
        String query = "UPDATE budget SET user_id=?, category_id=?, amount_limit=?, period=? WHERE budget_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, budget.getUserId());
            ps.setInt(2, budget.getCategoryId());
            ps.setDouble(3, budget.getAmountLimit());
            ps.setString(4, budget.getPeriod());
            ps.setInt(5, budget.getBudgetId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBudget(int budgetId) {
        String query = "DELETE FROM budget WHERE budget_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, budgetId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
