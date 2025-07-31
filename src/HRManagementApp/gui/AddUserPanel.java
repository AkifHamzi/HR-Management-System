package HRManagementApp.gui;

import HRManagementApp.model.HRManager;
import HRManagementApp.service.UserService;
import javax.swing.*;
import java.awt.*;

public class AddUserPanel extends JPanel {

    public AddUserPanel(MainFrame mainFrame, UserService userService) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // --- MODIFICATION: Define new font for reuse ---
        Font labelFont = new Font("SansSerif", Font.PLAIN, 16);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 16);

        // Title
        JLabel titleLabel = new JLabel("Add HR Manager");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        // --- MODIFICATION: Add extra space below the title ---
        gbc.insets = new Insets(10, 10, 30, 10);
        add(titleLabel, gbc);

        // Reset insets for the rest of the components
        gbc.insets = new Insets(10, 10, 10, 10);

        // Form fields
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel userLabel = new JLabel("User Name:");
        userLabel.setFont(labelFont);
        gbc.gridy = 1; gbc.gridx = 0; add(userLabel, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        gbc.gridy = 2; gbc.gridx = 0; add(passLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        JTextField userText = new JTextField(15);
        // --- MODIFICATION: Make text fields bigger ---
        userText.setPreferredSize(new Dimension(200, 30));
        userText.setFont(labelFont);
        gbc.gridy = 1; gbc.gridx = 1; add(userText, gbc);

        JPasswordField passText = new JPasswordField(15);
        // --- MODIFICATION: Make text fields bigger ---
        passText.setPreferredSize(new Dimension(200, 30));
        passText.setFont(labelFont);
        gbc.gridy = 2; gbc.gridx = 1; add(passText, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Add gaps
        buttonPanel.setOpaque(false);
        JButton addButton = new JButton("Add Manager");
        JButton backButton = new JButton("Back");

        // --- MODIFICATION: Set bigger font for buttons ---
        addButton.setFont(buttonFont);
        backButton.setFont(buttonFont);

        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Action Listeners
        addButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                userService.createUser(new HRManager(username, password));
                JOptionPane.showMessageDialog(this, "HR Manager added successfully!");
                userText.setText("");
                passText.setText("");
                mainFrame.showPanel("adminDashboard");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> mainFrame.showPanel("adminDashboard"));
    }
}