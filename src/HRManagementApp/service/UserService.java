package HRManagementApp.service;

import HRManagementApp.dao.IUserDAO;
import HRManagementApp.model.User;
import HRManagementApp.model.UserType;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final IUserDAO userDAO;

    public UserService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> authenticate(String username, String password) {
        try {
            Optional<User> userOpt = userDAO.getByUsername(username);
            if (userOpt.isPresent() && userOpt.get().authenticate(password)) {
                return userOpt;
            }
        } catch (IOException e) {
            e.printStackTrace(); // In a real app, use a logger
        }
        return Optional.empty();
    }

    /**
     * Creates a new user after checking for duplicates.
     * This method now correctly accepts a User object.
     * @param user The User object to create.
     * @throws Exception if the username already exists or a file error occurs.
     */
    public void createUser(User user) throws Exception {
        if (userDAO.getByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Username '" + user.getUsername() + "' already exists.");
        }
        userDAO.add(user);
    }

    /**
     * Updates an existing user's details.
     * @param user The User object with updated information.
     * @throws IOException if a file error occurs.
     */
    public void updateUser(User user) throws IOException {
        userDAO.update(user);
    }

    public List<User> getAllUsers() throws IOException {
        return userDAO.getAll();
    }

    public void deleteUser(String username) throws IOException {
        // Prevent deleting the main admin account
        if ("admin".equalsIgnoreCase(username)) {
            throw new IOException("The root admin account cannot be deleted.");
        }
        userDAO.delete(username);
    }
}