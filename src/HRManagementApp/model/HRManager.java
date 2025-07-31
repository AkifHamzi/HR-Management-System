package HRManagementApp.model;

public class HRManager extends User {
    public HRManager(String username, String password) {
        super(username, password);
    }

    public HRManager(String username, int passwordHash) {
        super(username, passwordHash);
    }

    @Override
    public UserType getUserType() {
        return UserType.HR_MANAGER;
    }
}