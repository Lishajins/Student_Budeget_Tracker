package com.spendzy.ui;

import com.spendzy.model.User;

public class AppContext {
    private static User currentUser;

    public static void setCurrentUser(User u) {
        currentUser = u;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static int requireUserId() {
        if (currentUser == null)
            throw new IllegalStateException("No user in session");
        return currentUser.getUserId();
    }
}
