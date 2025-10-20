package com.spendzy.ui.frames;

import com.spendzy.model.Budget;
import com.spendzy.model.Expense;
import com.spendzy.model.Income;
import com.spendzy.service.BudgetService;
import com.spendzy.service.ExpenseService;
import com.spendzy.service.IncomeService;
import com.spendzy.ui.AppContext;
import com.spendzy.ui.utils.FormatUtils;
import com.spendzy.ui.utils.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class DashboardPanel extends JPanel {
    private final IncomeService incomeService = new IncomeService();
    private final ExpenseService expenseService = new ExpenseService();
    private final BudgetService budgetService = new BudgetService();

    private JLabel totalIncomeLabel;
    private JLabel totalExpenseLabel;
    private JLabel totalBudgetLabel;
    private JLabel remainingLabel;

    public DashboardPanel() {
        setLayout(new BorderLayout(0, 30));
        setBackground(UIUtils.BG_DARK);

        // --- Title ---
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(UIUtils.TEAL);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(new EmptyBorder(25, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // --- Cards Container ---
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 30, 10));
        cardsPanel.setBackground(UIUtils.BG_DARK);
        cardsPanel.setBorder(new EmptyBorder(20, 40, 40, 40));

        // --- Create Info Cards ---
        totalIncomeLabel = makeCard(cardsPanel, "Total Income", "₹0.00");
        totalExpenseLabel = makeCard(cardsPanel, "Total Expense", "₹0.00");
        totalBudgetLabel = makeCard(cardsPanel, "Total Budget", "₹0.00");
        remainingLabel = makeCard(cardsPanel, "Remaining", "₹0.00");

        add(cardsPanel, BorderLayout.CENTER);

        // Refresh when shown on screen
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refresh();
            }
        });

        refresh(); // Initial load
    }

    private JLabel makeCard(JPanel parent, String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(UIUtils.TEAL);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        parent.add(card);
        return valueLabel;
    }

    public void refresh() {
        if (AppContext.getCurrentUser() == null) return;
        int userId = AppContext.requireUserId();

        // Fetch data fresh from DB
        List<Income> incomes = incomeService.getIncomesByUserId(userId);
        List<Expense> expenses = expenseService.getExpensesByUserId(userId);
        List<Budget> budgets = budgetService.getAllBudgetsByUser(userId);

        double totalIncome = incomes.stream().mapToDouble(Income::getAmount).sum();
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();
        double totalBudget = budgets.stream().mapToDouble(Budget::getAmountLimit).sum();
        double remaining = totalIncome - totalExpense;

        // Debug print (optional)
        System.out.printf("Dashboard Refresh → Income: %.2f | Expense: %.2f | Budget: %.2f | Remaining: %.2f%n",
                totalIncome, totalExpense, totalBudget, remaining);

        totalIncomeLabel.setText(FormatUtils.inr(totalIncome));
        totalExpenseLabel.setText(FormatUtils.inr(totalExpense));
        totalBudgetLabel.setText(FormatUtils.inr(totalBudget));
        remainingLabel.setText(FormatUtils.inr(remaining));

        // Change color of Remaining dynamically
        if (remaining >= 0) {
            remainingLabel.setForeground(new Color(0, 150, 0)); // Green if positive
        } else {
            remainingLabel.setForeground(Color.RED); // Red if negative
        }

        revalidate();
        repaint();
    }
}
