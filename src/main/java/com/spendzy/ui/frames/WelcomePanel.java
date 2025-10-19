package com.spendzy.ui.frames;

import com.spendzy.ui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class WelcomePanel extends JPanel {
    public WelcomePanel(Consumer<String> nav) {
        setLayout(new GridBagLayout());
        setBackground(UIUtils.BG_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // --- Title ---
        JLabel title = new JLabel("<html><div style='text-align:center; font-size:32px;'>Manage your expenses easily with <span style='color:#FF5929;'>SPENDZY</span></div></html>");
        title.setForeground(UIUtils.FG_TEXT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        add(title, gbc);

        // --- Buttons ---
        gbc.gridy++;
        gbc.insets = new Insets(40, 0, 0, 0);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttons.setBackground(UIUtils.BG_DARK);

        JButton login = new JButton("Login");
        JButton signup = new JButton("Sign Up");

        Font btnFont = new Font("Segoe UI", Font.BOLD, 16);

        for (JButton btn : new JButton[]{login, signup}) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setFont(btnFont);
            btn.setPreferredSize(new Dimension(140, 50));
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        buttons.add(login);
        buttons.add(signup);
        add(buttons, gbc);

        // Navigation
        login.addActionListener(e -> nav.accept("Login"));
        signup.addActionListener(e -> nav.accept("Signup"));
    }
}
