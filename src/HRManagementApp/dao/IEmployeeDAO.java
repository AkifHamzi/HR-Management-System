package HRManagementApp.dao;

import HRManagementApp.model.Employee;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IEmployeeDAO {
    void add(Employee employee) throws IOException;
    List<Employee> getAll() throws IOException;
    Optional<Employee> getById(long id) throws IOException;
    void update(Employee employee) throws IOException;
    void delete(long id) throws IOException;
    long getNextId() throws IOException;
}