package HRManagementApp.gui;

import HRManagementApp.model.Department;
import HRManagementApp.model.Designation;
import HRManagementApp.service.DepartmentService;
import HRManagementApp.service.DesignationService;
import HRManagementApp.service.EmployeeService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AddEmployeePanel extends JPanel {

    public AddEmployeePanel(MainFrame mainFrame, EmployeeService employeeService, DepartmentService departmentService, DesignationService designationService) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Add an Employee");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0; gbc.gridy = 0; add(titleLabel, gbc);

        // Form fields
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1; add(new JLabel("Employee Name:"), gbc);
        gbc.gridy = 2; add(new JLabel("Address:"), gbc);
        gbc.gridy = 3; add(new JLabel("Contact:"), gbc);
        gbc.gridy = 4; add(new JLabel("Department:"), gbc);
        gbc.gridy = 5; add(new JLabel("Designation:"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        JTextField nameText = new JTextField(20);
        JTextField addressText = new JTextField(20);
        JTextField contactText = new JTextField(20);
        JComboBox<Department> deptComboBox = new JComboBox<>();
        JComboBox<Designation> desigComboBox = new JComboBox<>();

        // Populate dropdowns
        try {
            departmentService.getAll().forEach(deptComboBox::addItem);
            designationService.getAllDesignations().forEach(desigComboBox::addItem);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data for dropdowns.");
        }

        gbc.gridx = 1;
        gbc.gridy = 1; add(nameText, gbc);
        gbc.gridy = 2; add(addressText, gbc);
        gbc.gridy = 3; add(contactText, gbc);
        gbc.gridy = 4; add(deptComboBox, gbc);
        gbc.gridy = 5; add(desigComboBox, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton addButton = new JButton("Add Employee");
        JButton backButton = new JButton("Back");
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Listeners
        backButton.addActionListener(e -> mainFrame.showPanel("hrDashboard"));
        addButton.addActionListener(e -> {
            try {
                String name = nameText.getText();
                String address = addressText.getText();
                String contact = contactText.getText();
                Department selectedDept = (Department) deptComboBox.getSelectedItem();
                Designation selectedDesig = (Designation) desigComboBox.getSelectedItem();

                if (name.isEmpty() || selectedDept == null || selectedDesig == null) {
                    JOptionPane.showMessageDialog(this, "Name, Department, and Designation are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                employeeService.createEmployee(name, address, contact, selectedDept.getId(), selectedDesig.getId());
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
                mainFrame.showPanel("hrDashboard");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}