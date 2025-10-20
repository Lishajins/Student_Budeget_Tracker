package com.spendzy.ui;

import com.spendzy.ui.components.Sidebar;
import com.spendzy.ui.frames.*;
import com.spendzy.ui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout card;
    private JPanel content;
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

        initializePreLoginPanels();
    }

    /** Initializes the welcome/login/signup panels */
    private void initializePreLoginPanels() {
        card = new CardLayout();
        content = new JPanel(card);
        content.setBackground(UIUtils.BG_DARK);

        content.add(new WelcomePanel(this::navigate), "Welcome");
        content.add(new LoginPanel(v -> onLoginSuccess(), this::navigate), "Login");
        content.add(new SignupPanel(v -> onSignupSuccess(), this::navigate), "Signup");

        getContentPane().removeAll();
        add(content, BorderLayout.CENTER);

        revalidate();
        repaint();
        card.show(content, "Welcome");
    }

    /** Initializes main panels after successful login/signup */
    private void setupMainApp() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        sidebar = new Sidebar(this::navigate);
        add(sidebar, BorderLayout.WEST);

        card = new CardLayout();
        content = new JPanel(card);
        content.setBackground(UIUtils.BG_DARK);

        dashboardPanel = new DashboardPanel();
        expensesPanel = new ExpensesPanel();
        incomePanel = new IncomePanel();
        budgetPanel = new BudgetPanel();

        content.add(dashboardPanel, "Dashboard");
        content.add(expensesPanel, "Expenses");
        content.add(incomePanel, "Income");
        content.add(budgetPanel, "Budget");

        add(content, BorderLayout.CENTER);

        revalidate();
        repaint();
        card.show(content, "Dashboard");
    }

    /** Navigation handler for sidebar + all screen transitions */
    private void navigate(String name) {
        if ("Logout".equals(name)) {
            AppContext.setCurrentUser(null);

            // Clean rebuild of pre-login layout
            SwingUtilities.invokeLater(this::initializePreLoginPanels);
            return;
        }

        if ("Dashboard".equals(name) && dashboardPanel != null) {
            dashboardPanel.refresh();
        }

        if (content != null) {
            card.show(content, name);
        }
    }

    private void onLoginSuccess() {
        setupMainApp();
    }

    private void onSignupSuccess() {
        setupMainApp();
    }
}
