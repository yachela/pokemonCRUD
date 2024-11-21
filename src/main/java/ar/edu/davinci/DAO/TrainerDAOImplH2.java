package ar.edu.davinci.DAO;

import ar.edu.davinci.Model.Pokemon;
import ar.edu.davinci.Model.Trainer;
import ar.edu.davinci.IType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAOImplH2 implements TrainerDAO {
    private static final String URL = "jdbc:h2:tcp://localhost/~/test";
   // private static final String URL = "jdbc:h2:~/test"
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private Connection connection;

    public TrainerDAOImplH2() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String createTrainerTable = "CREATE TABLE IF NOT EXISTS trainer (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "birth_date DATE NOT NULL, " +
                    "age INT, " +
                    "nationality VARCHAR(50))";
            statement.executeUpdate(createTrainerTable);
            String createTrainerPokemonTable = "CREATE TABLE IF NOT EXISTS trainer_pokemon (" +
                    "trainer_id INT, " +
                    "pokemon_id INT, " +
                    "PRIMARY KEY (trainer_id, pokemon_id), " +
                    "FOREIGN KEY (trainer_id) REFERENCES trainer(id), " +
                    "FOREIGN KEY (pokemon_id) REFERENCES pokemon(id))";
            statement.executeUpdate(createTrainerPokemonTable);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void insertTrainer(Trainer trainer) {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String insertQuery = "INSERT INTO trainer (name, birth_date, age, nationality) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, trainer.getName());
            preparedStatement.setDate(2, Date.valueOf(trainer.getBirthDate()));
            preparedStatement.setInt(3, trainer.getAge());
            preparedStatement.setString(4, trainer.getNationality());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int trainerId = generatedKeys.getInt(1);
                trainer.setId(trainerId);

          /*      for (Pokemon pokemon : trainer.getPokemonList()) {
                    insertTrainerPokemonRelation(trainerId, pokemon.getId());
                }
                          */
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String updateQuery = "UPDATE trainer SET name = ?, birth_date = ?, age = ?, nationality = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, String.valueOf(trainer.getName()));
            preparedStatement.setString(2, trainer.getBirthDate().toString());
            preparedStatement.setInt(3, trainer.getAge());
            preparedStatement.setString(4, trainer.getNationality());
            preparedStatement.setInt(5, trainer.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Trainer with ID " + trainer.getId() + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating trainer: " + e.getMessage(), e);
        }

    }

    @Override
    public void deleteTrainer(int trainerId) {
            try {
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                String deleteQuery = "DELETE FROM trainer WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, trainerId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new RuntimeException("Trainer with ID " + trainerId + " not found");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error deleting Trainer: " + e.getMessage(), e);
            }
        }

    @Override
    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String selectQuery = "SELECT * FROM trainer";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Date birthDate = resultSet.getDate("birth_date");
                int age = resultSet.getInt("age");
                String nationality = resultSet.getString("nationality");

                Trainer trainer = new Trainer(name, birthDate.toLocalDate(),nationality);
                trainer.setPokemonList(getPokemonListByTrainerId(id));
                trainers.add(trainer);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }
    private List<Pokemon> getPokemonListByTrainerId(int trainerId) {
        List<Pokemon> pokemonList = new ArrayList<>();
        try {
            String query = "SELECT p.* FROM pokemon p INNER JOIN trainer_pokemon tp ON p.id = tp.pokemon_id WHERE tp.trainer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, trainerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String typeStr = resultSet.getString("type");
                IType type = IType.fromString(typeStr);
               // float energy = resultSet.getFloat("energy");
                Float power = resultSet.getFloat("power");
                String specie = resultSet.getString("especie");

                Pokemon pokemon = new Pokemon(type, specie);
                pokemonList.add(pokemon);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemonList;
    }
}



