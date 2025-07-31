package HRManagementApp.dao;

import HRManagementApp.model.Employee;
import HRManagementApp.util.FileUtils;
import HRManagementApp.util.IDGenerator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implements IEmployeeDAO for file-based persistence of Employee objects.
 */
public class EmployeeDAO implements IEmployeeDAO {
    private static final String FILE_PATH = "data/employees.txt";
    private static final String DELIMITER = "|";

    @Override
    public void add(Employee employee) throws IOException {
        List<Employee> employees = getAll();
        employees.add(employee);
        saveAll(employees);
    }

    @Override
    public List<Employee> getAll() throws IOException {
        return FileUtils.readAllLines(FILE_PATH).stream()
                .map(line -> Employee.fromString(line, DELIMITER))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Employee> getById(long id) throws IOException {
        return getAll().stream()
                .filter(employee -> employee.getId() == id)
                .findFirst();
    }

    @Override
    public void update(Employee updatedEmployee) throws IOException {
        List<Employee> updatedList = getAll().stream()
                .map(employee -> employee.getId() == updatedEmployee.getId() ? updatedEmployee : employee)
                .collect(Collectors.toList());
        saveAll(updatedList);
    }

    @Override
    public void delete(long id) throws IOException {
        List<Employee> employees = getAll();
        employees.removeIf(employee -> employee.getId() == id);
        saveAll(employees);
    }

    @Override
    public long getNextId() throws IOException {
        List<String> lines = FileUtils.readAllLines(FILE_PATH);
        return IDGenerator.getNextId(
                lines,
                line -> Employee.fromString(line, DELIMITER),
                Employee::getId
        );
    }

    private void saveAll(List<Employee> employees) throws IOException {
        List<String> lines = employees.stream()
                .map(employee -> employee.toFileString(DELIMITER))
                .collect(Collectors.toList());
        FileUtils.writeAllLines(FILE_PATH, lines);
    }
}