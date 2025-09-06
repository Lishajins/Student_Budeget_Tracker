package com.spendzy.ui.panels;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {
    public SidebarPanel() {
        setBackground(new Color(40, 40, 40));
        setLayout(null);

        JLabel appName = new JLabel("SPENDZY");
        appName.setForeground(Color.ORANGE);
        appName.setFont(new Font("Arial", Font.BOLD, 18));
        appName.setBounds(30, 20, 100, 30);
        add(appName);

        String[] menuItems = {"Dashboard", "Expenses", "Income", "Budget", "Logout"};
        int y = 70;

        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setBounds(20, y, 120, 30);
            button.setFocusPainted(false);
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);
            add(button);
            y += 40;
        }
    }
}
