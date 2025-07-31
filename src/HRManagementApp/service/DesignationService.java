package HRManagementApp.service;

import HRManagementApp.dao.IDesignationDAO;
import HRManagementApp.model.Designation;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for handling designation-related business logic.
 */
public class DesignationService {

    private final IDesignationDAO designationDAO;

    public DesignationService(IDesignationDAO designationDAO) {
        this.designationDAO = designationDAO;
    }

    /**
     * Gets all designations.
     * @return A list of all designations.
     * @throws IOException If a file error occurs.
     */
    public List<Designation> getAllDesignations() throws IOException {
        return designationDAO.getAll();
    }

    /**
     * Creates a new designation.
     * @param name The name of the new designation.
     * @throws Exception If the name is empty or a file error occurs.
     */
    public void createDesignation(String name) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new Exception("Designation name cannot be empty.");
        }

        // Optional: Check for duplicates before adding
        if (designationDAO.getAll().stream().anyMatch(d -> d.getName().equalsIgnoreCase(name.trim()))) {
            throw new Exception("Designation with this name already exists.");
        }

        long newId = designationDAO.getNextId();
        Designation designation = new Designation(newId, name.trim());
        designationDAO.add(designation);
    }

    /**
     * Updates an existing designation.
     * @param id The ID of the designation to update.
     * @param newName The new name for the designation.
     * @throws Exception If the name is empty or a file error occurs.
     */
    public void updateDesignation(long id, String newName) throws Exception {
        if (newName == null || newName.trim().isEmpty()) {
            throw new Exception("Designation name cannot be empty.");
        }
        Designation designation = new Designation(id, newName.trim());
        designationDAO.update(designation);
    }

    /**
     * Deletes a designation by its ID.
     * @param id The ID of the designation to delete.
     * @throws IOException If a file error occurs.
     */
    public void deleteDesignation(long id) throws IOException {
        // In a real application, you would first check if any employees are assigned this designation.
        designationDAO.delete(id);
    }

    /**
     * Finds a single designation by its ID.
     * @param id The ID of the designation.
     * @return An Optional containing the designation if found.
     * @throws IOException if a file error occurs.
     */
    public Optional<Designation> getDesignationById(long id) throws IOException {
        return designationDAO.getById(id);
    }
}