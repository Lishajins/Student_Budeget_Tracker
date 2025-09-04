package com.spendzy.dao;

import com.spendzy.db.DBUtility;
import com.spendzy.model.IncomeCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeCategoryDAOImpl implements IncomeCategoryDAO {

    @Override
    public boolean addCategory(IncomeCategory category) {
        String query = "INSERT INTO incomecategories(name, user_id) VALUES(?, ?)";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getUserId());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) category.setIncomeCategoryId(keys.getInt(1));
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
    public IncomeCategory getCategoryById(int incomeCategoryId) {
        String query = "SELECT * FROM incomecategories WHERE income_category_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, incomeCategoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new IncomeCategory(
                        rs.getInt("income_category_id"),
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
    public List<IncomeCategory> getAllCategories(int userId) {
        List<IncomeCategory> list = new ArrayList<>();
        String query = "SELECT * FROM incomecategories WHERE user_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new IncomeCategory(
                        rs.getInt("income_category_id"),
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
    public boolean updateCategory(IncomeCategory category) {
        String query = "UPDATE incomecategories SET name=?, user_id=? WHERE income_category_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getUserId());
            ps.setInt(3, category.getIncomeCategoryId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCategory(int incomeCategoryId) {
        String query = "DELETE FROM incomecategories WHERE income_category_id=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, incomeCategoryId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
