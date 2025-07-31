package HRManagementApp.service;

import HRManagementApp.dao.IDepartmentDAO;
import HRManagementApp.model.Department;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DepartmentService {
    private final IDepartmentDAO departmentDAO;

    public DepartmentService(IDepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    public List<Department> getAll() throws IOException {
        return departmentDAO.getAll();
    }

    public void save(String name) throws IOException {
        long id = departmentDAO.getNextId();
        Department newDept = new Department(id, name);
        departmentDAO.add(newDept);
    }

    public void update(long id, String newName) throws IOException {
        Department dept = new Department(id, newName);
        departmentDAO.update(dept);
    }

    public void delete(long id) throws IOException {
        departmentDAO.delete(id);
    }

    public Optional<Department> findById(long id) throws IOException {
        return departmentDAO.getById(id);
    }
}