package HRManagementApp.gui;

import HRManagementApp.model.Employee;
import HRManagementApp.service.DepartmentService;
import HRManagementApp.service.DesignationService;
import HRManagementApp.service.EmployeeService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Vector;
import java.util.Map;
import java.util.stream.Collectors;

public class ManageEmployeePanel extends JPanel {
    private final DefaultTableModel tableModel;
    private final JTable employeeTable;
    private final TableRowSorter<DefaultTableModel> sorter;

    public ManageEmployeePanel(MainFrame mainFrame, EmployeeService employeeService, DepartmentService departmentService, DesignationService designationService) {
        setOpaque(false);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Manage Employees");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        searchPanel.add(new JLabel("Search:"));
        JTextField searchText = new JTextField(20);
        searchPanel.add(searchText);
        JButton backButton = new JButton("Back");
        searchPanel.add(backButton);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID", "Name", "Department", "Designation", "Update", "Delete"};
        tableModel = new DefaultTableModel(null, columnNames) {
            public boolean isCellEditable(int row, int col) { return col >= 4; }
        };
        employeeTable = new JTable(tableModel);
        employeeTable.setRowHeight(30);

        sorter = new TableRowSorter<>(tableModel);
        employeeTable.setRowSorter(sorter);

        add(new JScrollPane(employeeTable), BorderLayout.CENTER);

        // Table Actions
        Action updateAction = new AbstractAction("Update") {
            public void actionPerformed(ActionEvent e) {
                int modelRow = Integer.parseInt(e.getActionCommand());
                long empId = (long) tableModel.getValueAt(modelRow, 0);

                JPanel updatePanel = new UpdateEmployeePanel(mainFrame, employeeService, departmentService, designationService, empId);
                mainFrame.addPanel(updatePanel, "updateEmployee_" + empId);
                mainFrame.showPanel("updateEmployee_" + empId);
            }
        };

        Action deleteAction = new AbstractAction("Delete") {
            public void actionPerformed(ActionEvent e) {
                int modelRow = Integer.parseInt(e.getActionCommand());
                long empId = (long) tableModel.getValueAt(modelRow, 0);
                String empName = (String) tableModel.getValueAt(modelRow, 1);

                int choice = JOptionPane.showConfirmDialog(ManageEmployeePanel.this, "Delete " + empName + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        employeeService.deleteEmployee(empId);
                        tableModel.removeRow(modelRow);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ManageEmployeePanel.this, "Error deleting employee.");
                    }
                }
            }
        };

        new ButtonColumn(employeeTable, updateAction, 4);
        new ButtonColumn(employeeTable, deleteAction, 5);

        // Listeners
        backButton.addActionListener(e -> mainFrame.showPanel("hrDashboard"));
        searchText.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            private void filter() {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText.getText(), 1));
            }
        });

        loadEmployees(employeeService, departmentService, designationService);
    }

    private void loadEmployees(EmployeeService empSvc, DepartmentService deptSvc, DesignationService desigSvc) {
        try {
            tableModel.setRowCount(0);
            Map<Long, String> deptMap = deptSvc.getAll().stream().collect(Collectors.toMap(d -> d.getId(), d -> d.getName()));
            Map<Long, String> desigMap = desigSvc.getAllDesignations().stream().collect(Collectors.toMap(d -> d.getId(), d -> d.getName()));

            for (Employee emp : empSvc.getAllEmployees()) {
                Vector<Object> row = new Vector<>();
                row.add(emp.getId());
                row.add(emp.getName());
                row.add(deptMap.getOrDefault(emp.getDepartmentId(), "N/A"));
                row.add(desigMap.getOrDefault(emp.getDesignationId(), "N/A"));
                row.add("Update");
                row.add("Delete");
                tableModel.addRow(row);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load employees.");
        }
    }
}