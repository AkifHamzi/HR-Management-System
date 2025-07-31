package HRManagementApp.dao;

import HRManagementApp.model.Department;
import HRManagementApp.util.FileUtils;
import HRManagementApp.util.IDGenerator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DepartmentDAO implements IDepartmentDAO {
    private static final String FILE_PATH = "data/departments.txt";
    private static final String DELIMITER = "|";

    @Override
    public void add(Department department) throws IOException {
        List<Department> departments = getAll();
        departments.add(department);
        saveAll(departments);
    }

    @Override
    public List<Department> getAll() throws IOException {
        return FileUtils.readAllLines(FILE_PATH).stream()
                .map(line -> Department.fromString(line, DELIMITER))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Department> getById(long id) throws IOException {
        return getAll().stream().filter(d -> d.getId() == id).findFirst();
    }

    @Override
    public void update(Department updatedDepartment) throws IOException {
        List<Department> updatedList = getAll().stream()
                .map(d -> d.getId() == updatedDepartment.getId() ? updatedDepartment : d)
                .collect(Collectors.toList());
        saveAll(updatedList);
    }

    @Override
    public void delete(long id) throws IOException {
        List<Department> departments = getAll();
        departments.removeIf(d -> d.getId() == id);
        saveAll(departments);
    }

    @Override
    public long getNextId() throws IOException {
        List<String> lines = FileUtils.readAllLines(FILE_PATH);
        return IDGenerator.getNextId(
                lines,
                line -> Department.fromString(line, DELIMITER),
                Department::getId
        );
    }

    private void saveAll(List<Department> departments) throws IOException {
        List<String> lines = departments.stream()
                .map(d -> d.toFileString(DELIMITER))
                .collect(Collectors.toList());
        FileUtils.writeAllLines(FILE_PATH, lines);
    }
}