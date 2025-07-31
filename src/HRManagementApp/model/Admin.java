package HRManagementApp.model;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    public Admin(String username, int passwordHash) {
        super(username, passwordHash);
    }

    @Override
    public UserType getUserType() {
        return UserType.ADMIN;
    }
}