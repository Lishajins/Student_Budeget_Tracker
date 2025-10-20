package com.spendzy.ui.frames;

import com.spendzy.model.Budget;
import com.spendzy.model.ExpenseCategory;
import com.spendzy.service.BudgetService;
import com.spendzy.service.ExpenseCategoryService;
import com.spendzy.ui.AppContext;
import com.spendzy.ui.utils.FormatUtils;
import com.spendzy.ui.utils.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetPanel extends JPanel {
    private final BudgetService budgetService = new BudgetService();
    private final ExpenseCategoryService categoryService = new ExpenseCategoryService();
    private JTable table;
    private DefaultTableModel model;
    private final List<Integer> budgetIds = new ArrayList<>();

    public BudgetPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(UIUtils.BG_DARK);

        // --- Title ---
        JLabel title = new JLabel("Budget");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(UIUtils.TEAL);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(new EmptyBorder(30, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // --- Table Container ---
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(15, 20, 15, 20)
        ));
        card.setPreferredSize(new Dimension(900, 400));

        // --- Table Setup ---
        String[] cols = {"Category", "Limit (₹)", "Period"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setForeground(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(240, 240, 240));
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);

        card.add(scrollPane, BorderLayout.CENTER);
        add(card, BorderLayout.CENTER);

        // --- Buttons ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        btnPanel.setBackground(UIUtils.BG_DARK);

        JButton addBudgetBtn = makeButton("Add Budget", UIUtils.TEAL, Color.BLACK);
        JButton updateBudgetBtn = makeButton("Update Budget", UIUtils.TEAL, Color.BLACK);
        JButton deleteBudgetBtn = makeButton("Delete Budget", new Color(255, 140, 0), Color.BLACK);

        btnPanel.add(addBudgetBtn);
        btnPanel.add(updateBudgetBtn);
        btnPanel.add(deleteBudgetBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // --- Actions ---
        addBudgetBtn.addActionListener(e -> openAddBudgetDialog());
        updateBudgetBtn.addActionListener(e -> openUpdateBudgetDialog());
        deleteBudgetBtn.addActionListener(e -> deleteSelectedBudget());

        refresh();
    }

    // ---------- BUTTON STYLE ----------
    private JButton makeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setPreferredSize(new Dimension(220, 45));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(bg.darker()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bg); }
        });
        return btn;
    }

    // ---------- ADD BUDGET ----------
    private void openAddBudgetDialog() {
        JDialog dialog = new JDialog((Frame) null, "Add Budget", true);
        styleDialog(dialog);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel catLabel = makeLabel("Category:");
        JLabel limitLabel = makeLabel("Amount Limit (₹):");
        JLabel periodLabel = makeLabel("Period:");

        JTextField limitField = makeField();
        JComboBox<String> periodBox = new JComboBox<>(new String[]{"Weekly", "Monthly", "Yearly"});

        List<ExpenseCategory> categories = categoryService.getExpenseCategoriesByUser(AppContext.requireUserId());
        JComboBox<String> categoryBox = new JComboBox<>();
        for (ExpenseCategory c : categories) categoryBox.addItem(c.getName());

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(catLabel, gbc);
        gbc.gridx = 1; dialog.add(categoryBox, gbc);
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(limitLabel, gbc);
        gbc.gridx = 1; dialog.add(limitField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(periodLabel, gbc);
        gbc.gridx = 1; dialog.add(periodBox, gbc);

        JButton saveBtn = makeButton("Save", UIUtils.TEAL, Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(saveBtn, gbc);

        saveBtn.addActionListener(e -> {
            try {
                String categoryName = (String) categoryBox.getSelectedItem();
                double limit = Double.parseDouble(limitField.getText().trim());
                String period = (String) periodBox.getSelectedItem();

                ExpenseCategory selected = categories.stream()
                        .filter(c -> c.getName().equals(categoryName))
                        .findFirst().orElse(null);

                if (selected != null) {
                    Budget budget = new Budget(0, AppContext.requireUserId(),
                            selected.getExpenseCategoryId(), limit, period);
                    budgetService.addBudget(budget);
                    JOptionPane.showMessageDialog(dialog, "Budget added successfully!");
                    dialog.dispose();
                    refresh();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Invalid category selected.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        dialog.setVisible(true);
    }

    // ---------- UPDATE BUDGET ----------
    private void openUpdateBudgetDialog() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a budget to update.");
            return;
        }

        int id = budgetIds.get(row);
        Budget existing = budgetService.getBudgetById(id);
        if (existing == null) {
            JOptionPane.showMessageDialog(this, "Could not find selected budget.");
            return;
        }

        JDialog dialog = new JDialog((Frame) null, "Update Budget", true);
        styleDialog(dialog);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel limitLabel = makeLabel("Amount Limit (₹):");
        JLabel periodLabel = makeLabel("Period:");

        JTextField limitField = makeField();
        limitField.setText(String.valueOf(existing.getAmountLimit()));
        JComboBox<String> periodBox = new JComboBox<>(new String[]{"Weekly", "Monthly", "Yearly"});
        periodBox.setSelectedItem(existing.getPeriod());

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(limitLabel, gbc);
        gbc.gridx = 1; dialog.add(limitField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(periodLabel, gbc);
        gbc.gridx = 1; dialog.add(periodBox, gbc);

        JButton saveBtn = makeButton("Update", UIUtils.TEAL, Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(saveBtn, gbc);

        saveBtn.addActionListener(e -> {
            try {
                double limit = Double.parseDouble(limitField.getText().trim());
                String period = (String) periodBox.getSelectedItem();

                existing.setAmountLimit(limit);
                existing.setPeriod(period);

                budgetService.updateBudget(existing);
                JOptionPane.showMessageDialog(dialog, "Budget updated successfully!");
                dialog.dispose();
                refresh();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        dialog.setVisible(true);
    }

    // ---------- DELETE ----------
    private void deleteSelectedBudget() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a budget to delete.");
            return;
        }
        int id = budgetIds.get(row);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this budget?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            budgetService.deleteBudget(id);
            refresh();
        }
    }

    // ---------- UI HELPERS ----------
    private void styleDialog(JDialog dialog) {
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(420, 320);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UIUtils.BG_DARK);
    }

    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    private JTextField makeField() {
        JTextField field = new JTextField(15);
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100)),
                new EmptyBorder(6, 8, 6, 8)
        ));
        return field;
    }

    // ---------- REFRESH ----------
    private void refresh() {
        model.setRowCount(0);
        budgetIds.clear();
        if (AppContext.getCurrentUser() == null) return;

        List<Budget> list = budgetService.getAllBudgetsByUser(AppContext.requireUserId());
        for (Budget b : list) {
            budgetIds.add(b.getBudgetId());
            ExpenseCategory cat = categoryService.getExpenseCategoryById(b.getCategoryId());
            String catName = (cat != null) ? cat.getName() : "Unknown";
            model.addRow(new Object[]{
                    catName,
                    FormatUtils.inr(b.getAmountLimit()),
                    b.getPeriod()
            });
        }
    }
}
