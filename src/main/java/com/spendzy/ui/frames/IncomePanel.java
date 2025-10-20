package com.spendzy.ui.frames;

import com.spendzy.model.Income;
import com.spendzy.model.IncomeCategory;
import com.spendzy.service.IncomeCategoryService;
import com.spendzy.service.IncomeService;
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

public class IncomePanel extends JPanel {
    private final IncomeService incomeService = new IncomeService();
    private final IncomeCategoryService categoryService = new IncomeCategoryService();
    private JTable table;
    private DefaultTableModel model;
    private final List<Integer> incomeIds = new ArrayList<>();

    public IncomePanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(UIUtils.BG_DARK);

        // --- Title ---
        JLabel title = new JLabel("Income");
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
        String[] cols = {"Category", "Amount (₹)", "Source", "Date"};
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

        JButton addIncomeBtn = makeButton("Add Income", UIUtils.TEAL, Color.BLACK);
        JButton addCategoryBtn = makeButton("Add Income Category", UIUtils.TEAL, Color.BLACK);
        JButton deleteIncomeBtn = makeButton("Delete Income", new Color(255, 140, 0), Color.BLACK);

        btnPanel.add(addIncomeBtn);
        btnPanel.add(addCategoryBtn);
        btnPanel.add(deleteIncomeBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // --- Actions ---
        addIncomeBtn.addActionListener(e -> openAddIncomeDialog());
        addCategoryBtn.addActionListener(e -> openAddCategoryDialog());
        deleteIncomeBtn.addActionListener(e -> deleteSelectedIncome());

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

    // ---------- ADD INCOME ----------
    private void openAddIncomeDialog() {
        JDialog dialog = new JDialog((Frame) null, "Add Income", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(420, 380);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(UIUtils.BG_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel catLabel = makeLabel("Category:");
        JLabel amtLabel = makeLabel("Amount:");
        JLabel nameLabel = makeLabel("Source:");
        JLabel dateLabel = makeLabel("Date (yyyy-mm-dd):");

        JTextField amountField = makeField();
        JTextField nameField = makeField();
        JTextField dateField = makeField();

        List<IncomeCategory> categories = categoryService.getAllCategories(AppContext.requireUserId());
        JComboBox<String> categoryBox = new JComboBox<>();
        for (IncomeCategory c : categories) categoryBox.addItem(c.getName());

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(catLabel, gbc);
        gbc.gridx = 1; dialog.add(categoryBox, gbc);
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(amtLabel, gbc);
        gbc.gridx = 1; dialog.add(amountField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(nameLabel, gbc);
        gbc.gridx = 1; dialog.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(dateLabel, gbc);
        gbc.gridx = 1; dialog.add(dateField, gbc);

        JButton saveBtn = makeButton("Save", UIUtils.TEAL, Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(saveBtn, gbc);

        saveBtn.addActionListener(e -> {
            try {
                String categoryName = (String) categoryBox.getSelectedItem();
                double amt = Double.parseDouble(amountField.getText().trim());
                String source = nameField.getText().trim();
                java.util.Date date = java.sql.Date.valueOf(dateField.getText().trim());

                IncomeCategory selected = categories.stream()
                        .filter(c -> c.getName().equals(categoryName))
                        .findFirst().orElse(null);

                if (selected != null) {
                    Income income = new Income(0, AppContext.requireUserId(),
                            selected.getIncomeCategoryId(), amt, source, date);
                    incomeService.addIncome(income);
                    JOptionPane.showMessageDialog(dialog, "Income added successfully!");
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

    // ---------- ADD CATEGORY ----------
    private void openAddCategoryDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter new income category name:");
        if (name != null && !name.trim().isEmpty()) {
            IncomeCategory category = new IncomeCategory(0, name.trim(), AppContext.requireUserId());
            boolean ok = categoryService.addCategory(category);
            if (ok) JOptionPane.showMessageDialog(this, "Income category added successfully!");
            else JOptionPane.showMessageDialog(this, "Failed to add category.");
        }
    }

    // ---------- DELETE INCOME ----------
    private void deleteSelectedIncome() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an income to delete.");
            return;
        }
        int id = incomeIds.get(row);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this income?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            incomeService.deleteIncome(id);
            refresh();
        }
    }

    // ---------- REFRESH ----------
    private void refresh() {
        model.setRowCount(0);
        incomeIds.clear();
        if (AppContext.getCurrentUser() == null) return;

        List<Income> list = incomeService.getIncomesByUserId(AppContext.requireUserId());
        for (Income i : list) {
            incomeIds.add(i.getIncomeId());
            IncomeCategory cat = categoryService.getCategoryById(i.getCategoryId());
            String catName = (cat != null) ? cat.getName() : "Unknown";
            model.addRow(new Object[]{
                    catName,
                    FormatUtils.inr(i.getAmount()),
                    i.getIncomeName(),
                    i.getDate()
            });
        }
    }
}
