package HRManagementApp.dao;

import HRManagementApp.model.User;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    void add(User user) throws IOException;
    List<User> getAll() throws IOException;
    Optional<User> getByUsername(String username) throws IOException;
    void update(User user) throws IOException;
    void delete(String username) throws IOException;
}