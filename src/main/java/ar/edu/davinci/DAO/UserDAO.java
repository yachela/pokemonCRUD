package ar.edu.davinci.DAO;
import ar.edu.davinci.Model.User;
import java.util.List;

public interface UserDAO {
    void insertUser(User user);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int userID);
    User getUserByUsername(String username);
}
