package com.spendzy.ui.panels;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {
    public StatsPanel() {
        setLayout(null);
        setBackground(new Color(60, 60, 60)); // Dark background

        JLabel monthLabel = new JLabel("August, 2025:");
        monthLabel.setFont(new Font("Arial", Font.BOLD, 18));
        monthLabel.setForeground(Color.WHITE);
        monthLabel.setBounds(20, 10, 200, 30);
        add(monthLabel);

        String[] labels = {"Total Income", "Total Expense", "Budget of the month"};
        String[] values = {"$300.25", "$150.15", "$250.60"};

        int x = 20;
        for (int i = 0; i < 3; i++) {
            JPanel card = new JPanel();
            card.setLayout(null);
            card.setBackground(Color.WHITE);
            card.setBounds(x, 60, 180, 100);

            JLabel title = new JLabel(labels[i]);
            title.setBounds(20, 10, 160, 20);
            title.setFont(new Font("Arial", Font.PLAIN, 14));
            card.add(title);

            JLabel val = new JLabel(values[i]);
            val.setBounds(20, 40, 160, 30);
            val.setFont(new Font("Arial", Font.BOLD, 18));
            val.setForeground(new Color(0, 128, 128));
            card.add(val);

            add(card);
            x += 200;
        }
    }
}
