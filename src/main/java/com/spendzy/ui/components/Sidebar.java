package com.spendzy.ui.components;

import com.spendzy.ui.utils.UIUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class Sidebar extends JPanel {
    public Sidebar(Consumer<String> onNavigate) {
        setLayout(new BorderLayout());
        setBackground(UIUtils.BG_PANEL);
        setPreferredSize(new Dimension(200, 720));

        // --- Header Logo ---
        JLabel logo = new JLabel("SPENDZY");
        logo.setForeground(UIUtils.ACCENT);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        add(logo, BorderLayout.NORTH);

        // --- Top Navigation Buttons ---
        JPanel topButtons = new JPanel();
        topButtons.setLayout(new BoxLayout(topButtons, BoxLayout.Y_AXIS));
        topButtons.setBackground(UIUtils.BG_PANEL);
        topButtons.setBorder(BorderFactory.createEmptyBorder(30, 15, 30, 15));

        String[] menuItems = {"Dashboard", "Expenses", "Income", "Budget"};
        for (String item : menuItems) {
            JButton btn = createSidebarButton(item, onNavigate, UIUtils.TEAL, Color.BLACK);
            topButtons.add(btn);
            topButtons.add(Box.createVerticalStrut(15));
        }

        add(topButtons, BorderLayout.CENTER);

        // --- Bottom Logout Button ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
        bottomPanel.setBackground(UIUtils.BG_PANEL);

        JButton logoutBtn = createSidebarButton("Logout", onNavigate, new Color(230, 70, 50), Color.BLACK);
        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createSidebarButton(String text, Consumer<String> onNavigate, Color baseColor, Color textColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setBackground(baseColor);
        btn.setForeground(textColor);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(150, 40));
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        // --- Hover effect ---
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(UIUtils.ACCENT);
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(baseColor);
                btn.setForeground(textColor);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                onNavigate.accept(text);
            }
        });

        return btn;
    }
}
