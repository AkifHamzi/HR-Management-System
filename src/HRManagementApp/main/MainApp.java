package HRManagementApp.main;

import HRManagementApp.dao.*;
import HRManagementApp.gui.HRDashboardPanel;
import HRManagementApp.gui.LoginPanel;
import HRManagementApp.gui.MainFrame;
import HRManagementApp.service.*;

import javax.swing.*;


public class MainApp {
    public static void main(String[] args) {
        // Set a modern Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize ALL DAOs
        IUserDAO userDAO = new UserDAO();
        IDepartmentDAO departmentDAO = new DepartmentDAO();
        IDesignationDAO designationDAO = new DesignationDAO();
        IEmployeeDAO employeeDAO = new EmployeeDAO();

        // Initialize ALL Services with their DAO dependencies
        UserService userService = new UserService(userDAO);
        DepartmentService departmentService = new DepartmentService(departmentDAO);
        DesignationService designationService = new DesignationService(designationDAO);
        EmployeeService employeeService = new EmployeeService(employeeDAO, departmentDAO, designationDAO);

        // Start the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();

            // Create and add login panel, passing all services to it
            JPanel loginPanel = new LoginPanel(mainFrame, userService, designationService, departmentService, employeeService);
            mainFrame.addPanel(loginPanel, "login");

            // Initially show the login panel
            mainFrame.showPanel("login");
            mainFrame.setVisible(true);
        });
    }
}