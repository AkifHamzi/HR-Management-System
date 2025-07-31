package HRManagementApp.gui;

import HRManagementApp.model.User;
import HRManagementApp.model.UserType;
import HRManagementApp.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Vector;

public class ManageUserPanel extends JPanel {
    private final DefaultTableModel tableModel;
    private final JTable userTable;
    private final TableRowSorter<DefaultTableModel> sorter;

    public ManageUserPanel(MainFrame mainFrame, UserService userService) {
        setOpaque(false);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top panel with title, search, and back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Manage HR Managers");
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

        // Table setup
        String[] columnNames = {"Username", "Role", "Update", "Delete"};
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 3;
            }
        };
        userTable = new JTable(tableModel);
        userTable.setRowHeight(30);

        sorter = new TableRowSorter<>(tableModel);
        userTable.setRowSorter(sorter);

        // Action for the "Update" button in the table
        Action updateAction = new AbstractAction("Update") {
            public void actionPerformed(ActionEvent e) {
                int modelRow = Integer.parseInt(e.getActionCommand());
                String username = (String) tableModel.getValueAt(modelRow, 0);

                // Create and navigate to the UpdateUserPanel
                JPanel updateUserPanel = new UpdateUserPanel(mainFrame, userService, username);
                String panelName = "updateUser_" + username; // Unique name for the panel
                mainFrame.addPanel(updateUserPanel, panelName);
                mainFrame.showPanel(panelName);
            }
        };

        // Action for the "Delete" button in the table
        Action deleteAction = new AbstractAction("Delete") {
            public void actionPerformed(ActionEvent e) {
                int modelRow = Integer.parseInt(e.getActionCommand());
                String username = (String) tableModel.getValueAt(modelRow, 0);

                if ("admin".equalsIgnoreCase(username)) {
                    JOptionPane.showMessageDialog(ManageUserPanel.this, "Cannot delete the admin account.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int choice = JOptionPane.showConfirmDialog(ManageUserPanel.this, "Delete user '" + username + "'?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        userService.deleteUser(username);
                        tableModel.removeRow(modelRow);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ManageUserPanel.this, "Error deleting user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };

        // Attach the button actions to the table columns
        new ButtonColumn(userTable, updateAction, 2);
        new ButtonColumn(userTable, deleteAction, 3);

        add(new JScrollPane(userTable), BorderLayout.CENTER);

        // Real-time search functionality
        searchText.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            private void filter() {
                String text = searchText.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    // Filter by username column (index 0), case-insensitive
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0));
                }
            }
        });

        backButton.addActionListener(e -> mainFrame.showPanel("adminDashboard"));

        loadUsers(userService);
    }

    private void loadUsers(UserService userService) {
        try {
            tableModel.setRowCount(0); // Clear table
            for (User user : userService.getAllUsers()) {
                if (user.getUserType() == UserType.HR_MANAGER) {
                    Vector<Object> row = new Vector<>();
                    row.add(user.getUsername());
                    row.add(user.getUserType().toString());
                    row.add("Update");
                    row.add("Delete");
                    tableModel.addRow(row);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}