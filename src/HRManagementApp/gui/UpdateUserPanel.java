package HRManagementApp.gui;

import HRManagementApp.model.User;
import HRManagementApp.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class UpdateUserPanel extends JPanel {

    public UpdateUserPanel(MainFrame mainFrame, UserService userService, String usernameToUpdate) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel titleLabel = new JLabel("Update HR Manager");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Form Fields
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1; gbc.gridx = 0; add(new JLabel("Username:"), gbc);
        gbc.gridy = 2; gbc.gridx = 0; add(new JLabel("New Password:"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        JTextField userText = new JTextField(usernameToUpdate, 15);
        userText.setEditable(false); // Username should not be changed
        userText.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridy = 1; gbc.gridx = 1; add(userText, gbc);

        JPasswordField newPassText = new JPasswordField(15);
        gbc.gridy = 2; gbc.gridx = 1; add(newPassText, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton updateButton = new JButton("Update Password");
        JButton backButton = new JButton("Back to Manage");
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Action Listeners
        updateButton.addActionListener(e -> {
            String newPassword = new String(newPassText.getPassword());
            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "New password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                // Find the user to update
                Optional<User> userOpt = userService.getAllUsers().stream()
                        .filter(u -> u.getUsername().equals(usernameToUpdate))
                        .findFirst();

                if (userOpt.isPresent()) {
                    User userToUpdate = userOpt.get();
                    userToUpdate.setPassword(newPassword); // This method hashes the password
                    userService.updateUser(userToUpdate);
                    JOptionPane.showMessageDialog(this, "Password updated successfully!");
                    mainFrame.showPanel("adminDashboard"); // Go back to main dashboard
                } else {
                    JOptionPane.showMessageDialog(this, "User not found! This should not happen.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error updating password: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            // Recreate the manage panel to ensure it's fresh when going back
            JPanel manageUserPanel = new ManageUserPanel(mainFrame, userService);
            mainFrame.addPanel(manageUserPanel, "manageUser"); // Overwrites the old one
            mainFrame.showPanel("manageUser");
        });
    }
}