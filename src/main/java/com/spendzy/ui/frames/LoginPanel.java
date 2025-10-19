package com.spendzy.ui.frames;

import com.spendzy.db.DBUtility;
import com.spendzy.model.User;
import com.spendzy.ui.AppContext;
import com.spendzy.ui.utils.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.function.Consumer;

public class LoginPanel extends JPanel {
    private final JTextField email = new JTextField(15);
    private final JPasswordField password = new JPasswordField(15);
    private final Consumer<Void> onSuccess;
    private final Consumer<String> nav;

    public LoginPanel(Consumer<Void> onSuccess, Consumer<String> nav) {
        this.onSuccess = onSuccess;
        this.nav = nav;

        setLayout(new GridBagLayout());
        setBackground(UIUtils.BG_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // --- Title ---
        JLabel title = new JLabel("Login");
        title.setForeground(UIUtils.ACCENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;

        // --- Email ---
        addLabelAndField("Email", email, gbc, 1);

        // --- Password ---
        addLabelAndField("Password", password, gbc, 2);

        // --- Buttons ---
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttons.setBackground(UIUtils.BG_DARK);

        JButton loginBtn = new JButton("Login");
        JButton goSignup = new JButton("Sign Up");

        Font btnFont = new Font("Segoe UI", Font.BOLD, 16);
        for (JButton btn : new JButton[]{loginBtn, goSignup}) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setFont(btnFont);
            btn.setPreferredSize(new Dimension(130, 45));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        buttons.add(loginBtn);
        buttons.add(goSignup);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttons, gbc);

        // Actions
        goSignup.addActionListener(e -> nav.accept("Signup"));
        loginBtn.addActionListener(e -> doLogin());
    }

    private void addLabelAndField(String labelText, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        JLabel label = new JLabel(labelText);
        label.setForeground(UIUtils.TEAL);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        add(label, gbc);

        gbc.gridx = 1;
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 90)),
                new EmptyBorder(6, 8, 6, 8)
        ));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(field, gbc);
    }

    private void doLogin() {
        String em = email.getText().trim();
        String pw = new String(password.getPassword()).trim();

        if (em.isEmpty() || pw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password.");
            return;
        }

        String query = "SELECT user_id, username, email, password FROM users WHERE email=? AND password=?";
        try (Connection conn = DBUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, em);
            ps.setString(2, pw);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("email"), null);
                AppContext.setCurrentUser(user);
                JOptionPane.showMessageDialog(this, "Welcome back, " + user.getUsername() + "!");
                onSuccess.accept(null);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Login failed: " + ex.getMessage());
        }
    }
}
