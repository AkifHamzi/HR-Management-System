package HRManagementApp.dao;

import HRManagementApp.model.Department;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IDepartmentDAO {
    void add(Department department) throws IOException;
    List<Department> getAll() throws IOException;
    Optional<Department> getById(long id) throws IOException;
    void update(Department department) throws IOException;
    void delete(long id) throws IOException;
    long getNextId() throws IOException;
}