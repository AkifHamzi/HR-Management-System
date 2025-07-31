package HRManagementApp.service;

import HRManagementApp.dao.IDepartmentDAO;
import HRManagementApp.dao.IDesignationDAO;
import HRManagementApp.dao.IEmployeeDAO;
import HRManagementApp.model.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for handling employee-related business logic.
 */
public class EmployeeService {
    private final IEmployeeDAO employeeDAO;
    private final IDepartmentDAO departmentDAO;
    private final IDesignationDAO designationDAO;

    public EmployeeService(IEmployeeDAO employeeDAO, IDepartmentDAO departmentDAO, IDesignationDAO designationDAO) {
        this.employeeDAO = employeeDAO;
        this.departmentDAO = departmentDAO;
        this.designationDAO = designationDAO;
    }

    /**
     * Gets all employees.
     * @return A list of all employees.
     * @throws IOException If a file error occurs.
     */
    public List<Employee> getAllEmployees() throws IOException {
        return employeeDAO.getAll();
    }

    /**
     * Creates a new employee after validating the input data.
     * @param name The employee's name.
     * @param address The employee's address.
     * @param contact The employee's contact number.
     * @param departmentId The ID of the assigned department.
     * @param designationId The ID of the assigned designation.
     * @throws Exception If validation fails or a file error occurs.
     */
    public void createEmployee(String name, String address, String contact, long departmentId, long designationId) throws Exception {
        // Validation logic
        if (name == null || name.trim().isEmpty()) {
            throw new Exception("Employee name cannot be empty.");
        }
        if (departmentDAO.getById(departmentId).isEmpty()) {
            throw new Exception("The selected department does not exist.");
        }
        if (designationDAO.getById(designationId).isEmpty()) {
            throw new Exception("The selected designation does not exist.");
        }

        long newId = employeeDAO.getNextId();
        Employee employee = new Employee(newId, name, address, contact, departmentId, designationId);
        employeeDAO.add(employee);
    }

    /**
     * Updates an existing employee's details.
     * @param employee The employee object with updated information.
     * @throws Exception If validation fails or a file error occurs.
     */
    public void updateEmployee(Employee employee) throws Exception {
        // More validation can be added here as needed
        if (employee == null) {
            throw new Exception("Employee data cannot be null.");
        }
        employeeDAO.update(employee);
    }

    /**
     * Deletes an employee by their ID.
     * @param id The ID of the employee to delete.
     * @throws IOException If a file error occurs.
     */
    public void deleteEmployee(long id) throws IOException {
        employeeDAO.delete(id);
    }

    /**
     * Finds a single employee by their ID.
     * @param id The ID of the employee.
     * @return An Optional containing the employee if found.
     * @throws IOException if a file error occurs.
     */
    public Optional<Employee> getEmployeeById(long id) throws IOException {
        return employeeDAO.getById(id);
    }
}