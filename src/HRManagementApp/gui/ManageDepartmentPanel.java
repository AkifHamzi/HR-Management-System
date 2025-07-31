package HRManagementApp.gui;

import HRManagementApp.model.Department;
import HRManagementApp.service.DepartmentService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Vector;

public class ManageDepartmentPanel extends JPanel {
    private final DefaultTableModel tableModel;

    public ManageDepartmentPanel(MainFrame mainFrame, DepartmentService departmentService) {
        setOpaque(false);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(new JLabel("Manage Departments", JLabel.LEFT), BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        JButton addButton = new JButton("Add Department");
        JButton backButton = new JButton("Back");
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID", "Department Name", "Delete"};
        tableModel = new DefaultTableModel(null, columnNames) {
            public boolean isCellEditable(int row, int col) { return col == 2; }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);

        Action deleteAction = new AbstractAction("Delete") {
            public void actionPerformed(ActionEvent e) {
                int modelRow = Integer.parseInt(e.getActionCommand());
                long deptId = (long) tableModel.getValueAt(modelRow, 0);
                String deptName = (String) tableModel.getValueAt(modelRow, 1);
                int choice = JOptionPane.showConfirmDialog(ManageDepartmentPanel.this, "Delete " + deptName + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        departmentService.delete(deptId);
                        tableModel.removeRow(modelRow);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ManageDepartmentPanel.this, "Error deleting.");
                    }
                }
            }
        };
        new ButtonColumn(table, deleteAction, 2);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Listeners
        backButton.addActionListener(e -> mainFrame.showPanel("hrDashboard"));
        addButton.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(this, "Enter New Department Name:");
            if (newName != null && !newName.trim().isEmpty()) {
                try {
                    departmentService.save(newName.trim());
                    loadData(departmentService); // Refresh table
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error adding department.");
                }
            }
        });

        loadData(departmentService);
    }

    private void loadData(DepartmentService deptService) {
        try {
            tableModel.setRowCount(0);
            for(Department dept : deptService.getAll()) {
                Vector<Object> row = new Vector<>();
                row.add(dept.getId());
                row.add(dept.getName());
                row.add("Delete");
                tableModel.addRow(row);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading departments.");
        }
    }
}