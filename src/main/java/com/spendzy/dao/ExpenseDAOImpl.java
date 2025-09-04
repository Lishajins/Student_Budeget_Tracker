package com.spendzy.dao;

import com.spendzy.db.DBUtility;
import com.spendzy.model.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAOImpl implements ExpenseDAO {

    @Override
    public boolean addExpense(Expense expense) {
        String query = "INSERT INTO expenses(user_id, category_id, amount, description, date) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, expense.getUserId());
            ps.setInt(2, expense.getCategoryId());
            ps.setDouble(3, expense.getAmount());
            ps.setString(4, expense.getDescription());
            if (expense.getDate() != null) {
                ps.setDate(5, new java.sql.Date(expense.getDate().getTime()));
            } else {
                ps.setDate(5, null);
            }
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) expense.setExpenseId(keys.getInt(1));
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
    public Expense getExpenseById(int expenseId) {
        String query = "SELECT * FROM expenses WHERE expense_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, expenseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Expense(
                        rs.getInt("expense_id"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getDate("date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Expense> getExpensesByUserId(int userId) {
        List<Expense> list = new ArrayList<>();
        String query = "SELECT * FROM expenses WHERE user_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Expense(
                        rs.getInt("expense_id"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getDate("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateExpense(Expense expense) {
        String query = "UPDATE expenses SET user_id=?, category_id=?, amount=?, description=?, date=? WHERE expense_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, expense.getUserId());
            ps.setInt(2, expense.getCategoryId());
            ps.setDouble(3, expense.getAmount());
            ps.setString(4, expense.getDescription());
            if (expense.getDate() != null) {
                ps.setDate(5, new java.sql.Date(expense.getDate().getTime()));
            } else {
                ps.setDate(5, null);
            }
            ps.setInt(6, expense.getExpenseId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteExpense(int expenseId) {
        String query = "DELETE FROM expenses WHERE expense_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, expenseId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
