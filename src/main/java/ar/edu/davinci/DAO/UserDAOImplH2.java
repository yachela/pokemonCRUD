package ar.edu.davinci.DAO;

import ar.edu.davinci.Model.Trainer;
import ar.edu.davinci.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImplH2 implements UserDAO {
    private static String URL = "jdbc:h2:tcp://localhost/~/test";
    private static String USER = "sa";
    private static String PASSWORD = "";
    private Connection connection;

    public UserDAOImplH2() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();

            String createUserTable = "CREATE TABLE IF NOT EXISTS users (" + "id INT AUTO_INCREMENT PRIMARY KEY, " + "name VARCHAR(100) NOT NULL, " + "phone VARCHAR(100), " + "password VARCHAR(100) NOT NULL)";
            statement.executeUpdate(createUserTable);

            String createUserTrainerTable = "CREATE TABLE IF NOT EXISTS user_trainer (" + "user_id INT, " + "trainer_id INT, " + "PRIMARY KEY (user_id, trainer_id), " + "FOREIGN KEY (user_id) REFERENCES users(id), " + "FOREIGN KEY (trainer_id) REFERENCES trainer(id))";
            statement.executeUpdate(createUserTrainerTable);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDAOImplH2(String url, String user, String password) {
        this.URL = url;
        this.USER = user;
        this.PASSWORD = password;

    }

    @Override
    public void insertUser(User user) {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String insertQuery = "INSERT INTO users (name, phone, password) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhone());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String updateQuery = "UPDATE users SET name = ?, phone = ?, password = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhone());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("User with ID " + user.getId() + " not found");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteUser(int userId) {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String deleteQuery = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, userId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("User with ID " + userId + " not found");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String selectQuery = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String password = resultSet.getString("password");
                User user = new User(id, name, phone, password);


                List<Trainer> trainers = getTrainersForUser(id);
                user.setTrainers(trainers);
                users.add(user);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT * FROM users WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("phone"), rs.getString("password"));


                List<Trainer> trainers = getTrainersForUser(user.getId());
                user.setTrainers(trainers);

                return user;
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Trainer> getTrainersForUser(int userId) {
        List<Trainer> trainers = new ArrayList<>();
        String query = """
                    SELECT t.id, t.name, t.birth_date, t.nationality
                    FROM trainer t
                    JOIN user_trainer ut ON t.id = ut.trainer_id
                    WHERE ut.user_id = ?
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Trainer trainer = new Trainer(resultSet.getString("name"), resultSet.getDate("birth_date").toLocalDate(), resultSet.getString("nationality"));
                trainer.setId(resultSet.getInt("id"));
                trainers.add(trainer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }
}