package com.spendzy.ui.frames;

import com.spendzy.model.Expense;
import com.spendzy.model.ExpenseCategory;
import com.spendzy.service.ExpenseCategoryService;
import com.spendzy.service.ExpenseService;
import com.spendzy.ui.AppContext;
import com.spendzy.ui.utils.FormatUtils;
import com.spendzy.ui.utils.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class ExpensesPanel extends JPanel {
    private final ExpenseService expenseService = new ExpenseService();
    private final ExpenseCategoryService categoryService = new ExpenseCategoryService();
    private JTable table;
    private DefaultTableModel model;

    public ExpensesPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(UIUtils.BG_DARK);

        // --- Title ---
        JLabel title = new JLabel("Expenses");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(UIUtils.TEAL);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(new EmptyBorder(30, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // --- Table container (white card) ---
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(15, 20, 15, 20)
        ));
        card.setPreferredSize(new Dimension(900, 400));

        // --- Table setup ---
        String[] cols = {"ID", "Category", "Amount (₹)", "Description", "Date"};
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

        // --- Buttons below table ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        btnPanel.setBackground(UIUtils.BG_DARK);

        JButton addExpenseBtn = new JButton("Add Expense");
        JButton addCategoryBtn = new JButton("Add Expense Category");
        JButton deleteExpenseBtn = new JButton("Delete Expense");

        Font btnFont = new Font("Segoe UI", Font.BOLD, 16);
        for (JButton btn : new JButton[]{addExpenseBtn, addCategoryBtn, deleteExpenseBtn}) {
            btn.setFont(btnFont);
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setPreferredSize(new Dimension(200, 45));
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        btnPanel.add(addExpenseBtn);
        btnPanel.add(addCategoryBtn);
        btnPanel.add(deleteExpenseBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // --- Button Actions ---
        addExpenseBtn.addActionListener(e -> openAddExpenseDialog());
        addCategoryBtn.addActionListener(e -> openAddCategoryDialog());
        deleteExpenseBtn.addActionListener(e -> deleteSelectedExpense());

        refresh();
    }

    // ---------- DIALOG ----------
    private void openAddExpenseDialog() {
        JDialog dialog = new JDialog((Frame) null, "Add Expense", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(420, 380);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UIUtils.BG_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Labels
        JLabel catLabel = makeLabel("Category:");
        JLabel amtLabel = makeLabel("Amount:");
        JLabel descLabel = makeLabel("Description:");
        JLabel dateLabel = makeLabel("Date (yyyy-mm-dd):");

        // Fields
        JTextField amountField = makeField();
        JTextField descField = makeField();
        JTextField dateField = makeField();

        List<ExpenseCategory> categories = categoryService.getExpenseCategoriesByUser(AppContext.requireUserId());
        JComboBox<String> categoryBox = new JComboBox<>();
        for (ExpenseCategory c : categories) categoryBox.addItem(c.getName());

        // Layout form
        gbc.gridx = 0; gbc.gridy = 0; dialog.add(catLabel, gbc);
        gbc.gridx = 1; dialog.add(categoryBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1; dialog.add(amtLabel, gbc);
        gbc.gridx = 1; dialog.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; dialog.add(descLabel, gbc);
        gbc.gridx = 1; dialog.add(descField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; dialog.add(dateLabel, gbc);
        gbc.gridx = 1; dialog.add(dateField, gbc);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBackground(UIUtils.TEAL);
        saveBtn.setForeground(Color.BLACK);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveBtn.setFocusPainted(false);
        saveBtn.setPreferredSize(new Dimension(100, 40));

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(saveBtn, gbc);

        // Save Action
        saveBtn.addActionListener(e -> {
            try {
                String categoryName = (String) categoryBox.getSelectedItem();
                double amt = Double.parseDouble(amountField.getText().trim());
                String desc = descField.getText().trim();
                java.util.Date date = java.sql.Date.valueOf(dateField.getText().trim());

                ExpenseCategory selected = categories.stream()
                        .filter(c -> c.getName().equals(categoryName))
                        .findFirst().orElse(null);

                if (selected != null) {
                    Expense ex = new Expense(0, AppContext.requireUserId(),
                            selected.getExpenseCategoryId(), amt, desc, date);
                    expenseService.addExpense(ex);
                    JOptionPane.showMessageDialog(dialog, "Expense added successfully!");
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

    // ---------- CATEGORY ----------
    private void openAddCategoryDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter new category name:");
        if (name != null && !name.trim().isEmpty()) {
            ExpenseCategory category = new ExpenseCategory(0, name.trim(), AppContext.requireUserId());
            boolean ok = categoryService.addExpenseCategory(category);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Category added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add category.");
            }
        }
    }

    // ---------- DELETE ----------
    private void deleteSelectedExpense() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an expense to delete.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this expense?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            expenseService.deleteExpense(id);
            refresh();
        }
    }

    // ---------- REFRESH ----------
    private void refresh() {
        model.setRowCount(0);
        if (AppContext.getCurrentUser() == null) return;

        List<Expense> list = expenseService.getExpensesByUserId(AppContext.requireUserId());
        for (Expense e : list) {
            ExpenseCategory cat = categoryService.getExpenseCategoryById(e.getCategoryId());
            String catName = (cat != null) ? cat.getName() : "Unknown";
            model.addRow(new Object[]{
                    e.getExpenseId(),
                    catName,
                    FormatUtils.inr(e.getAmount()),
                    e.getDescription(),
                    e.getDate()
            });
        }
    }
}
