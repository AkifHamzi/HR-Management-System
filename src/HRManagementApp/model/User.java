package HRManagementApp.model;

import java.util.Objects;

/**
 * Abstract base class for all user types in the system.
 */
public abstract class User {
    protected String username;
    protected int passwordHash;

    public User(String username, String password) {
        this.username = username;
        setPassword(password);
    }

    // Constructor for loading from file
    public User(String username, int passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public int getPasswordHash() {
        return passwordHash;
    }

    // Setter for password (always hashes it)
    public final void setPassword(String password) {
        this.passwordHash = Objects.hash(password);
    }

    public boolean authenticate(String password) {
        return Objects.hash(password) == this.passwordHash;
    }

    public abstract UserType getUserType();

    public String toFileString(String delimiter) {
        return username + delimiter + passwordHash + delimiter + getUserType();
    }

    public static User fromString(String line, String delimiter) {
        String[] parts = line.split("\\" + delimiter);
        String username = parts[0];
        int passwordHash = Integer.parseInt(parts[1]);
        UserType userType = UserType.valueOf(parts[2]);

        switch (userType) {
            case ADMIN:
                return new Admin(username, passwordHash);
            case HR_MANAGER:
                return new HRManager(username, passwordHash);
            default:
                throw new IllegalArgumentException("Invalid user type in file: " + userType);
        }
    }
}