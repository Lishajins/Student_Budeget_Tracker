package com.spendzy.ui.utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIUtils {
    public static final Color BG_DARK = new Color(22, 24, 28);
    public static final Color BG_PANEL = new Color(30, 33, 38);
    public static final Color FG_TEXT = new Color(230, 230, 230);
    public static final Color ACCENT = new Color(255, 92, 51); // SPENDZY orange
    public static final Color TEAL = new Color(61, 199, 190);

    public static void stylePanel(JComponent c) {
        c.setBackground(BG_PANEL);
        c.setForeground(FG_TEXT);
    }

    public static JLabel title(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(FG_TEXT);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 22f));
        l.setBorder(new EmptyBorder(12, 12, 12, 12));
        return l;
    }

    public static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(ACCENT);
        b.setForeground(Color.BLACK);
        b.setBorder(BorderFactory.createEmptyBorder(10,16,10,16));
        return b;
    }

    public static JButton ghostButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(BG_PANEL);
        b.setForeground(FG_TEXT);
        b.setBorder(BorderFactory.createLineBorder(new Color(70,70,70)));
        return b;
    }

    public static JPanel cardStat(String heading, String value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG_PANEL);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60,60,60)),
                new EmptyBorder(16,16,16,16)
        ));
        JLabel h = new JLabel(heading);
        h.setForeground(new Color(200,200,200));
        h.setFont(h.getFont().deriveFont(Font.PLAIN, 14f));
        JLabel v = new JLabel(value);
        v.setForeground(FG_TEXT);
        v.setFont(v.getFont().deriveFont(Font.BOLD, 22f));
        p.add(h, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);
        return p;
    }
}
