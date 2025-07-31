package HRManagementApp.gui;

import HRManagementApp.model.Designation;
import HRManagementApp.service.DesignationService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Vector;

public class ManageDesignationPanel extends JPanel {
    private final DefaultTableModel tableModel;

    public ManageDesignationPanel(MainFrame mainFrame, DesignationService designationService) {
        setOpaque(false);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(new JLabel("Manage Designations", JLabel.LEFT), BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        JButton addButton = new JButton("Add Designation");
        JButton backButton = new JButton("Back");
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID", "Designation Name", "Delete"};
        tableModel = new DefaultTableModel(null, columnNames) {
            public boolean isCellEditable(int row, int col) { return col == 2; }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);

        Action deleteAction = new AbstractAction("Delete") {
            public void actionPerformed(ActionEvent e) {
                int modelRow = Integer.parseInt(e.getActionCommand());
                long desigId = (long) tableModel.getValueAt(modelRow, 0);
                String desigName = (String) tableModel.getValueAt(modelRow, 1);
                int choice = JOptionPane.showConfirmDialog(ManageDesignationPanel.this, "Delete " + desigName + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        designationService.deleteDesignation(desigId);
                        tableModel.removeRow(modelRow);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ManageDesignationPanel.this, "Error deleting.");
                    }
                }
            }
        };
        new ButtonColumn(table, deleteAction, 2);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Listeners
        backButton.addActionListener(e -> mainFrame.showPanel("hrDashboard"));
        addButton.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(this, "Enter New Designation Name:");
            if (newName != null && !newName.trim().isEmpty()) {
                try {
                    designationService.createDesignation(newName.trim());
                    loadData(designationService); // Refresh table
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error adding designation: " + ex.getMessage());
                }
            }
        });

        loadData(designationService);
    }

    private void loadData(DesignationService desigService) {
        try {
            tableModel.setRowCount(0);
            for (Designation desig : desigService.getAllDesignations()) {
                Vector<Object> row = new Vector<>();
                row.add(desig.getId());
                row.add(desig.getName());
                row.add("Delete");
                tableModel.addRow(row);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading designations.");
        }
    }
}