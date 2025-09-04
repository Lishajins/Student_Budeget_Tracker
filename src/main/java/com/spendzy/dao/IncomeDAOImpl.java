package com.spendzy.dao;

import com.spendzy.db.DBUtility;
import com.spendzy.model.Income;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeDAOImpl implements IncomeDAO {

    @Override
    public void addIncome(Income income) {
        String query = "INSERT INTO income(user_id, category_id, amount, income_name, date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, income.getUserId());
            ps.setInt(2, income.getCategoryId());
            ps.setDouble(3, income.getAmount());
            ps.setString(4, income.getIncomeName());
            if (income.getDate() != null) {
                ps.setDate(5, new java.sql.Date(income.getDate().getTime()));
            } else {
                ps.setDate(5, null);
            }
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) income.setIncomeId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Income getIncomeById(int id) {
        String query = "SELECT * FROM income WHERE income_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Income(
                        rs.getInt("income_id"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getString("income_name"),
                        rs.getDate("date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Income> getAllIncomes() {
        List<Income> list = new ArrayList<>();
        String query = "SELECT * FROM income";
        try (Connection conn = DBUtility.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Income(
                        rs.getInt("income_id"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getString("income_name"),
                        rs.getDate("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Income> getIncomesByUserId(int userId) {
        List<Income> list = new ArrayList<>();
        String query = "SELECT * FROM income WHERE user_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Income(
                        rs.getInt("income_id"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("amount"),
                        rs.getString("income_name"),
                        rs.getDate("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateIncome(Income income) {
        String query = "UPDATE income SET user_id=?, category_id=?, amount=?, income_name=?, date=? WHERE income_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, income.getUserId());
            ps.setInt(2, income.getCategoryId());
            ps.setDouble(3, income.getAmount());
            ps.setString(4, income.getIncomeName());
            if (income.getDate() != null) {
                ps.setDate(5, new java.sql.Date(income.getDate().getTime()));
            } else {
                ps.setDate(5, null);
            }
            ps.setInt(6, income.getIncomeId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteIncome(int id) {
        String query = "DELETE FROM income WHERE income_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
