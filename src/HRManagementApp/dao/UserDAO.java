package HRManagementApp.dao;

import HRManagementApp.model.Admin;
import HRManagementApp.model.User;
import HRManagementApp.util.FileUtils;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDAO implements IUserDAO {
    private static final String FILE_PATH = "data/users.txt";
    private static final String DELIMITER = "|";

    public UserDAO() {
        try {
            if (FileUtils.isNewFile(FILE_PATH)) {
                add(new Admin("admin", "admin"));
            }
        } catch (IOException e) {
            // In a real app, use a logger or show an error dialog
            System.err.println("CRITICAL: Could not initialize admin user. " + e.getMessage());
        }
    }

    @Override
    public void add(User user) throws IOException {
        List<User> users = getAll();
        users.add(user);
        saveAll(users);
    }

    @Override
    public List<User> getAll() throws IOException {
        return FileUtils.readAllLines(FILE_PATH).stream()
                .map(line -> User.fromString(line, DELIMITER))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> getByUsername(String username) throws IOException {
        return getAll().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    @Override
    public void update(User updatedUser) throws IOException {
        List<User> users = getAll();
        List<User> updatedList = users.stream()
                .map(user -> user.getUsername().equalsIgnoreCase(updatedUser.getUsername()) ? updatedUser : user)
                .collect(Collectors.toList());
        saveAll(updatedList);
    }

    @Override
    public void delete(String username) throws IOException {
        List<User> users = getAll();
        users.removeIf(user -> user.getUsername().equalsIgnoreCase(username));
        saveAll(users);
    }

    private void saveAll(List<User> users) throws IOException {
        List<String> lines = users.stream()
                .map(user -> user.toFileString(DELIMITER))
                .collect(Collectors.toList());
        FileUtils.writeAllLines(FILE_PATH, lines);
    }
}