package com.spendzy.dao;

import com.spendzy.model.User;
import java.util.List;

public interface UserDAO {
    boolean addUser(User user);
    User getUserById(int userId);
    List<User> getAllUsers();
    boolean updateUser(User user);
    boolean deleteUser(int userId);
}
