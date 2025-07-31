package HRManagementApp.gui;

import HRManagementApp.model.UserType;
import HRManagementApp.service.UserService;
import HRManagementApp.service.DepartmentService;
import HRManagementApp.service.DesignationService;
import HRManagementApp.service.EmployeeService;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoginPanel extends JPanel {

    public LoginPanel(MainFrame mainFrame, UserService userService, DesignationService designationService, DepartmentService departmentService, EmployeeService employeeService) {
        setOpaque(false); // Make panel transparent
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        JLabel titleLabel = new JLabel("Colombo Institute of Studies");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(Color.DARK_GRAY);

        // Login Box Panel
        JPanel loginBox = new JPanel(new GridBagLayout());
        loginBox.setOpaque(false);
        loginBox.setPreferredSize(new Dimension(400, 300)); // Wider and taller

        // Fonts
        Font headingFont = new Font("SansSerif", Font.BOLD, 28);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 16);
        Font inputFont = new Font("SansSerif", Font.PLAIN, 16);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 16);

        // Components
        JLabel loginTitle = new JLabel("Login");
        loginTitle.setFont(headingFont);

        JLabel userLabel = new JLabel("User Name");
        userLabel.setFont(labelFont);
        JTextField userText = new JTextField();
        userText.setFont(inputFont);
        userText.setPreferredSize(new Dimension(220, 30));

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(labelFont);
        JPasswordField passText = new JPasswordField();
        passText.setFont(inputFont);
        passText.setPreferredSize(new Dimension(220, 30));

        // --- MODIFIED BUTTON CREATION ---
        final Color buttonColor = new Color(46, 139, 87); // SeaGreen

        JButton loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                // Custom painting for a rounded look
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set color based on button state (e.g., pressed)
                if (getModel().isPressed()) {
                    g2.setColor(buttonColor.darker());
                } else {
                    g2.setColor(buttonColor);
                }

                // Fill the rounded rectangle shape
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20)); // 20, 20 are the arc width and height
                g2.dispose();

                // Paint the button's text (label) on top
                super.paintComponent(g);
            }
        };

        loginButton.setFont(buttonFont);
        loginButton.setForeground(Color.WHITE); // White text for better contrast
        loginButton.setPreferredSize(new Dimension(140, 40));

        // Crucial for custom painting
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        // --- END OF MODIFICATION ---

        // Layout for Login Box
        GridBagConstraints gbcLogin = new GridBagConstraints();
        gbcLogin.insets = new Insets(10, 10, 10, 10);
        gbcLogin.gridwidth = 2;
        gbcLogin.gridx = 0;
        gbcLogin.gridy = 0;
        loginBox.add(loginTitle, gbcLogin);

        gbcLogin.gridwidth = 1;
        gbcLogin.anchor = GridBagConstraints.WEST;
        gbcLogin.gridx = 0;
        gbcLogin.gridy = 1;
        loginBox.add(userLabel, gbcLogin);

        gbcLogin.gridx = 1;
        gbcLogin.gridy = 1;
        loginBox.add(userText, gbcLogin);

        gbcLogin.gridx = 0;
        gbcLogin.gridy = 2;
        loginBox.add(passLabel, gbcLogin);

        gbcLogin.gridx = 1;
        gbcLogin.gridy = 2;
        loginBox.add(passText, gbcLogin);

        gbcLogin.gridwidth = 2;
        gbcLogin.anchor = GridBagConstraints.CENTER;
        gbcLogin.gridx = 0;
        gbcLogin.gridy = 3;
        gbcLogin.insets = new Insets(20, 10, 10, 10); // Extra space above button
        loginBox.add(loginButton, gbcLogin);

        // Main Layout
        gbc.gridy = 0;
        add(titleLabel, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 0, 0, 0); // Space between title and login box
        add(loginBox, gbc);

        // Action Listener
        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());

            userService.authenticate(username, password).ifPresentOrElse(
                    user -> {
                        // Clear fields upon login
                        userText.setText("");
                        passText.setText("");

                        if (user.getUserType() == UserType.ADMIN) {
                            JPanel adminDashboard = new AdminDashboardPanel(mainFrame, userService);
                            mainFrame.addPanel(adminDashboard, "adminDashboard");
                            mainFrame.showPanel("adminDashboard");
                        } else if(user.getUserType() == UserType.HR_MANAGER){
                            JPanel hrDashboard = new HRDashboardPanel(mainFrame, employeeService, departmentService, designationService);
                            mainFrame.addPanel(hrDashboard, "hrDashboard");
                            mainFrame.showPanel("hrDashboard");
                        }
                    },
                    () -> JOptionPane.showMessageDialog(this,
                            "Invalid username or password.",
                            "Login Failed", JOptionPane.ERROR_MESSAGE)
            );
        });
    }
}