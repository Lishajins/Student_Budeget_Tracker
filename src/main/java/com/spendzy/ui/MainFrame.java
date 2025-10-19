package com.spendzy.ui;

import com.spendzy.ui.components.Sidebar;
import com.spendzy.ui.frames.*;
import com.spendzy.ui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout card = new CardLayout();
    private final JPanel content = new JPanel(card);
    private Sidebar sidebar;

    // Keep references for refresh/navigation
    private DashboardPanel dashboardPanel;
    private ExpensesPanel expensesPanel;
    private IncomePanel incomePanel;
    private BudgetPanel budgetPanel;

    public MainFrame() {
        setTitle("SPENDZY — Student Expense Tracker");
        setSize(1100, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(UIUtils.BG_DARK);
        setLayout(new BorderLayout());

        // Initial screens (before login/signup)
        content.setBackground(UIUtils.BG_DARK);
        content.add(new WelcomePanel(this::navigate), "Welcome");
        content.add(new LoginPanel(v -> onLoginSuccess(), this::navigate), "Login");
        content.add(new SignupPanel(v -> onSignupSuccess(), this::navigate), "Signup");

        add(content, BorderLayout.CENTER);
        navigate("Welcome");
    }

    // Called after successful login/signup
    private void setupMainApp() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        sidebar = new Sidebar(this::navigate);
        add(sidebar, BorderLayout.WEST);

        // Initialize main panels
        dashboardPanel = new DashboardPanel();
        expensesPanel = new ExpensesPanel();
        incomePanel = new IncomePanel();
        budgetPanel = new BudgetPanel();

        content.removeAll();
        content.add(dashboardPanel, "Dashboard");
        content.add(expensesPanel, "Expenses");
        content.add(incomePanel, "Income");
        content.add(budgetPanel, "Budget");

        add(content, BorderLayout.CENTER);
        revalidate();
        repaint();
        navigate("Dashboard");
    }

    // Handles sidebar and internal navigation
    private void navigate(String name) {
        if ("Logout".equals(name)) {
            // Clear current user
            AppContext.setCurrentUser(null);

            // Rebuild pre-login layout
            getContentPane().removeAll();
            setLayout(new BorderLayout());

            JPanel freshContent = new JPanel(card);
            freshContent.setBackground(UIUtils.BG_DARK);

            freshContent.add(new WelcomePanel(this::navigate), "Welcome");
            freshContent.add(new LoginPanel(v -> onLoginSuccess(), this::navigate), "Login");
            freshContent.add(new SignupPanel(v -> onSignupSuccess(), this::navigate), "Signup");

            add(freshContent, BorderLayout.CENTER);

            revalidate();
            repaint();

            card.show(freshContent, "Welcome");
            return;
        }

        // Refresh dashboard every time it’s opened
        if ("Dashboard".equals(name) && dashboardPanel != null) {
            dashboardPanel.refresh();
        }

        card.show(content, name);
    }

    private void onLoginSuccess() {
        setupMainApp();
    }

    private void onSignupSuccess() {
        setupMainApp();
    }
}
