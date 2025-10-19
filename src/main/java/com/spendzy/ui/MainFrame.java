package com.spendzy.ui;

import com.spendzy.ui.components.Sidebar;
import com.spendzy.ui.frames.BudgetPanel;
import com.spendzy.ui.frames.DashboardPanel;
import com.spendzy.ui.frames.ExpensesPanel;
import com.spendzy.ui.frames.IncomePanel;
import com.spendzy.ui.frames.LoginPanel;
import com.spendzy.ui.frames.SignupPanel;
import com.spendzy.ui.frames.WelcomePanel;
import com.spendzy.ui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout card = new CardLayout();
    private final JPanel content = new JPanel(card);
    private Sidebar sidebar;

    // ✅ Keep references for dynamic refresh
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

        // Initial screens before login
        content.setBackground(UIUtils.BG_DARK);
        content.add(new WelcomePanel(this::navigate), "Welcome");
        content.add(new LoginPanel(v -> onLoginSuccess(), this::navigate), "Login");
        content.add(new SignupPanel(v -> onSignupSuccess(), this::navigate), "Signup");

        add(content, BorderLayout.CENTER);
        navigate("Welcome");
    }

    private void setupMainApp() {
        // called after login/signup success
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        sidebar = new Sidebar(this::navigate);
        add(sidebar, BorderLayout.WEST);

        // ✅ Initialize panels
        dashboardPanel = new DashboardPanel();
        expensesPanel = new ExpensesPanel();
        incomePanel = new IncomePanel();
        budgetPanel = new BudgetPanel();

        // Add all content cards
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

    private void navigate(String name) {
        if ("Logout".equals(name)) {
            AppContext.setCurrentUser(null);
            getContentPane().removeAll();
            add(content, BorderLayout.CENTER);
            navigate("Welcome");
            revalidate();
            repaint();
            return;
        }

        // ✅ Refresh dashboard every time it’s shown
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
