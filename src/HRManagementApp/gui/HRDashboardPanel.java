package HRManagementApp.gui;

import HRManagementApp.service.DepartmentService;
import HRManagementApp.service.DesignationService;
import HRManagementApp.service.EmployeeService;
import javax.swing.*;
import java.awt.*;

public class HRDashboardPanel extends JPanel {

    public HRDashboardPanel(MainFrame mainFrame, EmployeeService employeeService, DepartmentService departmentService, DesignationService designationService) {
        setOpaque(false);
        setLayout(new BorderLayout());

        // Top Panel for Title and Log off button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("HR Dashboard");
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

        // Create buttons to represent the icons in your mockup
        JButton addEmployeeButton = new JButton("Add Employees");
        JButton manageEmployeeButton = new JButton("Manage Employees");
        JButton manageDeptButton = new JButton("Manage Departments");
        JButton manageDesigButton = new JButton("Manage Designations");

        // Style buttons to be larger
        Font buttonFont = new Font("SansSerif", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(220, 80);
        for(JButton btn : new JButton[]{addEmployeeButton, manageEmployeeButton, manageDeptButton, manageDesigButton}) {
            btn.setFont(buttonFont);
            btn.setPreferredSize(buttonSize);
        }

        gbc.gridx = 0; gbc.gridy = 0; centerPanel.add(addEmployeeButton, gbc);
        gbc.gridx = 1; gbc.gridy = 0; centerPanel.add(manageEmployeeButton, gbc);
        gbc.gridx = 0; gbc.gridy = 1; centerPanel.add(manageDeptButton, gbc);
        gbc.gridx = 1; gbc.gridy = 1; centerPanel.add(manageDesigButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // --- Navigation Logic ---
        logoffButton.addActionListener(e -> mainFrame.showPanel("login"));

        addEmployeeButton.addActionListener(e -> {
            JPanel addEmployeePanel = new AddEmployeePanel(mainFrame, employeeService, departmentService, designationService);
            mainFrame.addPanel(addEmployeePanel, "addEmployee");
            mainFrame.showPanel("addEmployee");
        });

        manageEmployeeButton.addActionListener(e -> {
            JPanel manageEmployeePanel = new ManageEmployeePanel(mainFrame, employeeService, departmentService, designationService);
            mainFrame.addPanel(manageEmployeePanel, "manageEmployee");
            mainFrame.showPanel("manageEmployee");
        });

        manageDeptButton.addActionListener(e -> {
            JPanel manageDeptPanel = new ManageDepartmentPanel(mainFrame, departmentService);
            mainFrame.addPanel(manageDeptPanel, "manageDepartment");
            mainFrame.showPanel("manageDepartment");
        });

        manageDesigButton.addActionListener(e -> {
            JPanel manageDesigPanel = new ManageDesignationPanel(mainFrame, designationService);
            mainFrame.addPanel(manageDesigPanel, "manageDesignation");
            mainFrame.showPanel("manageDesignation");
        });
    }
}