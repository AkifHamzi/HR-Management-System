package HRManagementApp.gui;

import HRManagementApp.service.UserService;
import javax.swing.*;
import java.awt.*;

public class  AdminDashboardPanel extends JPanel {

    public AdminDashboardPanel(MainFrame mainFrame, UserService userService) {
        setOpaque(false);
        setLayout(new BorderLayout());

        // Top Panel for Title and Log off button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton logoffButton = new JButton("Log off");
        logoffButton.setBackground(Color.DARK_GRAY);
        logoffButton.setForeground(Color.RED);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(logoffButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel for Buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JButton addHrButton = new JButton("Add HR Manager");
        addHrButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        addHrButton.setPreferredSize(new Dimension(250, 65));

        JButton manageHrButton = new JButton("Manage HR Managers");
        manageHrButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        manageHrButton.setPreferredSize(new Dimension(250, 65));

        gbc.gridx = 0; gbc.gridy = 0; centerPanel.add(addHrButton, gbc);
        gbc.gridx = 1; gbc.gridy = 0; centerPanel.add(manageHrButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // --- Navigation Logic ---
        logoffButton.addActionListener(e -> mainFrame.showPanel("login"));

        addHrButton.addActionListener(e -> {
            // Create and switch to the Add User Panel
            JPanel addUserPanel = new AddUserPanel(mainFrame, userService);
            mainFrame.addPanel(addUserPanel, "addUser");
            mainFrame.showPanel("addUser");
        });

        manageHrButton.addActionListener(e -> {
            // Create and switch to the Manage User Panel
            JPanel manageUserPanel = new ManageUserPanel(mainFrame, userService);
            mainFrame.addPanel(manageUserPanel, "manageUser");
            mainFrame.showPanel("manageUser");
        });
    }
}