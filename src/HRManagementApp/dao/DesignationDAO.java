package HRManagementApp.dao;

import HRManagementApp.model.Designation;
import HRManagementApp.util.FileUtils;
import HRManagementApp.util.IDGenerator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implements IDesignationDAO for file-based persistence of Designation objects.
 */
public class DesignationDAO implements IDesignationDAO {
    private static final String FILE_PATH = "data/designations.txt";
    private static final String DELIMITER = "|";

    @Override
    public void add(Designation designation) throws IOException {
        List<Designation> designations = getAll();
        designations.add(designation);
        saveAll(designations);
    }

    @Override
    public List<Designation> getAll() throws IOException {
        return FileUtils.readAllLines(FILE_PATH).stream()
                .map(line -> Designation.fromString(line, DELIMITER))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Designation> getById(long id) throws IOException {
        return getAll().stream()
                .filter(designation -> designation.getId() == id)
                .findFirst();
    }

    @Override
    public void update(Designation updatedDesignation) throws IOException {
        List<Designation> updatedList = getAll().stream()
                .map(designation -> designation.getId() == updatedDesignation.getId() ? updatedDesignation : designation)
                .collect(Collectors.toList());
        saveAll(updatedList);
    }

    @Override
    public void delete(long id) throws IOException {
        List<Designation> designations = getAll();
        designations.removeIf(designation -> designation.getId() == id);
        saveAll(designations);
    }

    @Override
    public long getNextId() throws IOException {
        List<String> lines = FileUtils.readAllLines(FILE_PATH);
        return IDGenerator.getNextId(
                lines,
                line -> Designation.fromString(line, DELIMITER),
                Designation::getId
        );
    }

    private void saveAll(List<Designation> designations) throws IOException {
        List<String> lines = designations.stream()
                .map(designation -> designation.toFileString(DELIMITER))
                .collect(Collectors.toList());
        FileUtils.writeAllLines(FILE_PATH, lines);
    }
}