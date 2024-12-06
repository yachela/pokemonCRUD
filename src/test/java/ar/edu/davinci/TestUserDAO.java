package ar.edu.davinci;

import ar.edu.davinci.DAO.UserDAO;
import ar.edu.davinci.DAO.UserDAOImplH2;
import ar.edu.davinci.Model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserDAO {

    private final UserDAO userDAO = new UserDAOImplH2();

    @Test
    public void testInsertUser() {
        User user = new User("Tracy", "123456789", "pass123");
        userDAO.insertUser(user);

        assertNotNull(user.getId(), "El ID del usuario no debería ser nulo al insertarlo.");
    }

    @Test
    public void testGetAllUsers() {
        userDAO.insertUser(new User("Casia", "987654321", "pass123"));
        userDAO.insertUser(new User("Juan", "456789123", "pass456"));

        List<User> users = userDAO.getAllUsers();

        assertTrue(users.size() >= 2, "Debería haber al menos 2 usuarios en la base de datos.");
    }

    @Test
    public void testUpdateUser() {
        User user = new User("Charly", "111222333", "password789");
        userDAO.insertUser(user);

        user.setName("Charlie");
        user.setPhone("999888777");
        userDAO.updateUser(user);

        User updatedUser = userDAO.getUserByUsername("Charlie");
        assertNotNull(updatedUser, "El usuario actualizado deberia existir en la base de datos.");
        assertEquals("999888777", updatedUser.getPhone(), "El telefono deberia actualizarse");
    }

    @Test
    public void testDeleteUser() {
        User user = new User("David", "555666777", "password000");
        userDAO.insertUser(user);

        userDAO.deleteUser(user.getId());

        User deletedUser = userDAO.getUserByUsername("David");
        assertNull(deletedUser, "El usuario debería haberse eliminado de la base de datos.");
    }
}