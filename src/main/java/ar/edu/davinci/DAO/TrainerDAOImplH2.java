package ar.edu.davinci.DAO;

import ar.edu.davinci.Interface.IType;
import ar.edu.davinci.Model.Pokemon;
import ar.edu.davinci.Model.Trainer;
import ar.edu.davinci.Model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAOImplH2 implements TrainerDAO {
    private static final String URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    @Override
    public void insertTrainer(Trainer trainer) {
        if (trainer.getUser() == null) {
            throw new IllegalArgumentException("El entrenador debe tener un usuario asignado antes de insertarlo en la base de datos.");
        }
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String insertQuery = "INSERT INTO trainer (name, birth_date, nationality, user_id) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, trainer.getName());
            preparedStatement.setDate(2, Date.valueOf(trainer.getBirthDate()));
            preparedStatement.setString(3, trainer.getNationality());
            preparedStatement.setInt(4, trainer.getUser().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                trainer.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar Trainer: " + e.getMessage(), e);
        }
    }

    @Override
    public Trainer getTrainerById(int trainerId) {
        String query = "SELECT * FROM trainer WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, trainerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return extractTrainerFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener Trainer por ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM trainer";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Trainer trainer = extractTrainerFromResultSet(resultSet);
                trainers.add(trainer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los entrenadores: " + e.getMessage(), e);
        }
        return trainers;
    }

    @Override
    public List<Trainer> getAllTrainersByUser(int userId) {
        List<Trainer> trainers = new ArrayList<>();
        String query = "SELECT * FROM trainer WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Trainer trainer = new Trainer(
                        resultSet.getString("name"),
                        resultSet.getDate("birth_date").toLocalDate(),
                        resultSet.getString("nationality")
                );
                trainer.setId(resultSet.getInt("id"));
                trainers.add(trainer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener entrenadores por usuario: " + e.getMessage(), e);
        }
        return trainers;
    }


    @Override
    public void updateTrainer(Trainer trainer) {
        String query = "UPDATE trainer SET name = ?, birth_date = ?, nationality = ?, user_id = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, trainer.getName());
            preparedStatement.setDate(2, Date.valueOf(trainer.getBirthDate()));
            preparedStatement.setString(3, trainer.getNationality());
            preparedStatement.setInt(4, trainer.getUser().getId());
            preparedStatement.setInt(5, trainer.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Trainer con ID " + trainer.getId() + " no encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar Trainer: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTrainer(int trainerId) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "DELETE FROM trainer WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, trainerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el entrenador: " + e.getMessage(), e);
        }
    }

    private Trainer extractTrainerFromResultSet(ResultSet resultSet) throws SQLException {
        Trainer trainer = new Trainer(
                resultSet.getString("name"),
                resultSet.getDate("birth_date").toLocalDate(),
                resultSet.getString("nationality")
        );
        trainer.setId(resultSet.getInt("id"));

        int userId = resultSet.getInt("user_id");
        if (userId > 0) {
            UserDAOImplH2 userDAO = new UserDAOImplH2();
            User user = userDAO.getUserById(userId);
            trainer.setUser(user);
        }


        return trainer;
    }
}