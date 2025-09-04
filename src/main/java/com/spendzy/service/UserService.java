// UserService.java
package com.spendzy.service;

import com.spendzy.dao.UserDAO;
import com.spendzy.dao.UserDAOImpl;
import com.spendzy.model.User;
import java.util.List;

public class UserService {
    private final UserDAO userDAO = new UserDAOImpl();

    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }
}
