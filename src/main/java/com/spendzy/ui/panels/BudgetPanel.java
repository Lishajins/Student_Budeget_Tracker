package com.spendzy.ui.panels;

import javax.swing.*;
import java.awt.*;

public class BudgetPanel extends JPanel {
    public BudgetPanel() {
        setLayout(null);
        setBackground(new Color(0, 128, 128)); // Teal background

        JLabel welcomeLabel = new JLabel("Welcome, Lisha!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(20, 10, 300, 25);
        add(welcomeLabel);

        JLabel budgetLabel = new JLabel("Remaining budget:");
        budgetLabel.setForeground(Color.WHITE);
        budgetLabel.setBounds(20, 40, 200, 20);
        add(budgetLabel);

        JLabel amountLabel = new JLabel("$100.45");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 26));
        amountLabel.setForeground(Color.ORANGE);
        amountLabel.setBounds(180, 35, 200, 30);
        add(amountLabel);

        JProgressBar progressBar = new JProgressBar(0, 250);
        progressBar.setValue(100);
        progressBar.setForeground(Color.DARK_GRAY);
        progressBar.setBounds(20, 70, 300, 15);
        add(progressBar);
    }
}
