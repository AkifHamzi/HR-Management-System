package HRManagementApp.dao;

import HRManagementApp.model.Designation;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IDesignationDAO {
    void add(Designation designation) throws IOException;
    List<Designation> getAll() throws IOException;
    Optional<Designation> getById(long id) throws IOException;
    void update(Designation designation) throws IOException;
    void delete(long id) throws IOException;
    long getNextId() throws IOException;
}