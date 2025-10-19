package com.spendzy.ui.frames;

import com.spendzy.model.User;
import com.spendzy.service.UserService;
import com.spendzy.ui.AppContext;
import com.spendzy.ui.utils.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class SignupPanel extends JPanel {
    private final JTextField username = new JTextField(15);
    private final JTextField email = new JTextField(15);
    private final JPasswordField password = new JPasswordField(15);
    private final Consumer<Void> onSuccess;
    private final Consumer<String> nav;

    private final UserService userService = new UserService();

    public SignupPanel(Consumer<Void> onSuccess, Consumer<String> nav) {
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
        JLabel title = new JLabel("Create Account");
        title.setForeground(UIUtils.ACCENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;

        // --- Username ---
        addLabelAndField("Username", username, gbc, 1);

        // --- Email ---
        addLabelAndField("Email", email, gbc, 2);

        // --- Password ---
        addLabelAndField("Password", password, gbc, 3);

        // --- Buttons ---
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttons.setBackground(UIUtils.BG_DARK);

        JButton signupBtn = new JButton("Sign Up");
        JButton goLogin = new JButton("Login");

        Font btnFont = new Font("Segoe UI", Font.BOLD, 16);
        for (JButton btn : new JButton[]{signupBtn, goLogin}) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setFont(btnFont);
            btn.setPreferredSize(new Dimension(130, 45));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        buttons.add(signupBtn);
        buttons.add(goLogin);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(buttons, gbc);

        // Actions
        goLogin.addActionListener(e -> nav.accept("Login"));
        signupBtn.addActionListener(e -> doSignup());
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

    private void doSignup() {
        String un = username.getText().trim();
        String em = email.getText().trim();
        String pw = new String(password.getPassword()).trim();

        if (un.isEmpty() || em.isEmpty() || pw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        User u = new User(0, un, em, pw);
        boolean ok = userService.addUser(u);
        if (ok) {
            AppContext.setCurrentUser(new User(u.getUserId(), u.getUsername(), u.getEmail(), null));
            JOptionPane.showMessageDialog(this, "Account created. Welcome, " + un + "!");
            onSuccess.accept(null);
        } else {
            JOptionPane.showMessageDialog(this, "Signup failed. Try again.");
        }
    }
}
