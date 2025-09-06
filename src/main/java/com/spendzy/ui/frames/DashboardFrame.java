package com.spendzy.ui.frames;

import com.spendzy.ui.panels.*;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    public DashboardFrame() {
        setTitle("SPENDZY Dashboard");
        setSize(800, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Sidebar
        SidebarPanel sidebar = new SidebarPanel();
        sidebar.setBounds(0, 0, 160, 500);
        add(sidebar);

        // Header
        BudgetPanel header = new BudgetPanel();
        header.setBounds(160, 0, 640, 100);
        add(header);

        // Stats panel (below header)
        StatsPanel stats = new StatsPanel();
        stats.setBounds(160, 100, 640, 400);
        add(stats);

        setVisible(true);
    }
}
