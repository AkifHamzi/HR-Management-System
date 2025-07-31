package HRManagementApp.gui;

import HRManagementApp.model.Department;
import HRManagementApp.model.Designation;
import HRManagementApp.model.Employee;
import HRManagementApp.service.DepartmentService;
import HRManagementApp.service.DesignationService;
import HRManagementApp.service.EmployeeService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class UpdateEmployeePanel extends JPanel {

    public UpdateEmployeePanel(MainFrame mainFrame, EmployeeService employeeService, DepartmentService departmentService, DesignationService designationService, long employeeId) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Update Employee Details");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0; gbc.gridy = 0; add(titleLabel, gbc);

        // Form Fields
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1; add(new JLabel("Employee ID:"), gbc);
        gbc.gridy = 2; add(new JLabel("Employee Name:"), gbc);
        gbc.gridy = 3; add(new JLabel("Address:"), gbc);
        gbc.gridy = 4; add(new JLabel("Contact:"), gbc);
        gbc.gridy = 5; add(new JLabel("Department:"), gbc);
        gbc.gridy = 6; add(new JLabel("Designation:"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        JTextField idText = new JTextField(String.valueOf(employeeId), 20);
        idText.setEditable(false);
        JTextField nameText = new JTextField(20);
        JTextField addressText = new JTextField(20);
        JTextField contactText = new JTextField(20);
        JComboBox<Department> deptComboBox = new JComboBox<>();
        JComboBox<Designation> desigComboBox = new JComboBox<>();

        gbc.gridx = 1;
        gbc.gridy = 1; add(idText, gbc);
        gbc.gridy = 2; add(nameText, gbc);
        gbc.gridy = 3; add(addressText, gbc);
        gbc.gridy = 4; add(contactText, gbc);
        gbc.gridy = 5; add(deptComboBox, gbc);
        gbc.gridy = 6; add(desigComboBox, gbc);

        // Populate and pre-fill data
        try {
            departmentService.getAll().forEach(deptComboBox::addItem);
            designationService.getAllDesignations().forEach(desigComboBox::addItem);

            Optional<Employee> empOpt = employeeService.getEmployeeById(employeeId);
            if(empOpt.isPresent()) {
                Employee emp = empOpt.get();
                nameText.setText(emp.getName());
                addressText.setText(emp.getAddress());
                contactText.setText(emp.getContact());

                // Select current department and designation in dropdowns
                for (int i = 0; i < deptComboBox.getItemCount(); i++) {
                    if (deptComboBox.getItemAt(i).getId() == emp.getDepartmentId()) {
                        deptComboBox.setSelectedIndex(i);
                        break;
                    }
                }
                for (int i = 0; i < desigComboBox.getItemCount(); i++) {
                    if (desigComboBox.getItemAt(i).getId() == emp.getDesignationId()) {
                        desigComboBox.setSelectedIndex(i);
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data.");
        }

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton updateButton = new JButton("Update");
        JButton backButton = new JButton("Back");
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);

        gbc.gridy = 7; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Listeners
        backButton.addActionListener(e -> {
            JPanel manageEmployeePanel = new ManageEmployeePanel(mainFrame, employeeService, departmentService, designationService);
            mainFrame.addPanel(manageEmployeePanel, "manageEmployee");
            mainFrame.showPanel("manageEmployee");
        });

        updateButton.addActionListener(e -> {
            try {
                Department selectedDept = (Department) deptComboBox.getSelectedItem();
                Designation selectedDesig = (Designation) desigComboBox.getSelectedItem();

                Employee updatedEmployee = new Employee(
                        employeeId,
                        nameText.getText(),
                        addressText.getText(),
                        contactText.getText(),
                        selectedDept.getId(),
                        selectedDesig.getId()
                );
                employeeService.updateEmployee(updatedEmployee);
                JOptionPane.showMessageDialog(this, "Employee updated successfully!");

                JPanel manageEmployeePanel = new ManageEmployeePanel(mainFrame, employeeService, departmentService, designationService);
                mainFrame.addPanel(manageEmployeePanel, "manageEmployee");
                mainFrame.showPanel("manageEmployee");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}