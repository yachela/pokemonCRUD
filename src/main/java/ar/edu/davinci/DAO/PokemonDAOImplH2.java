package ar.edu.davinci.DAO;

import ar.edu.davinci.Model.Pokemon;
import ar.edu.davinci.Model.Trainer;
import ar.edu.davinci.Model.IType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAOImplH2 implements PokemonDAO {
    private static final String URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final String TABLE_NAME = "pokemon";
    private Connection connection;

    public PokemonDAOImplH2() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS pokemon (" + "id INT AUTO_INCREMENT PRIMARY KEY, " + "type VARCHAR(50), " + "energy FLOAT DEFAULT 100, " + "power INT, " + "specie VARCHAR(100), " + "trainer_id INT, " + "FOREIGN KEY (trainer_id) REFERENCES trainer(id)" + ")";
            statement.executeUpdate(createTableQuery);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertPokemon(Pokemon pokemon) {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String insertQuery = "INSERT INTO pokemon (type, power, specie, trainer_id) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, pokemon.getType().getClass().getSimpleName());
            preparedStatement.setFloat(2, pokemon.getPower());
            preparedStatement.setString(3, pokemon.getSpecie());
            preparedStatement.setInt(4, pokemon.getTrainer().getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePokemon(Pokemon pokemon) {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String updateQuery = "UPDATE pokemon SET type = ?, power = ?, specie = ?, trainer_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, pokemon.getType().getClass().getSimpleName());
            preparedStatement.setFloat(2, pokemon.getPower());
            preparedStatement.setString(3, pokemon.getSpecie());

            if (pokemon.getTrainer() != null) {
                preparedStatement.setInt(4, pokemon.getTrainer().getId());
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }

            preparedStatement.setInt(5, pokemon.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Pokemon with ID " + pokemon.getId() + " not found");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Pokemon: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletePokemon(int pokemonId) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String deleteQuery = "DELETE FROM pokemon WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, pokemonId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Pokemon with ID " + pokemonId + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Pokemon: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pokemon> getAllPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();

        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String selectQuery = "SELECT p.*, t.id AS trainer_id, t.name AS trainer_name, t.birth_date, t.nationality " + "FROM pokemon p LEFT JOIN trainer t ON p.trainer_id = t.id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                String typeStr = resultSet.getString("type");
                IType type = null;
                if (typeStr != null && !typeStr.trim().isEmpty()) {
                    type = IType.fromString(typeStr);
                } else {
                    System.out.println("Tipo desconocido o nulo para el Pokémon con ID: " + resultSet.getInt("id"));
                    continue;
                }

                Float power = resultSet.getFloat("power");
                String specie = resultSet.getString("specie");
                int id = resultSet.getInt("id");

                Trainer trainer = null;
                int trainerId = resultSet.getInt("trainer_id");
                if (!resultSet.wasNull()) {
                    String trainerName = resultSet.getString("trainer_name");
                    LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                    String nationality = resultSet.getString("nationality");
                    trainer = new Trainer(trainerName, birthDate, nationality);
                    trainer.setId(trainerId);
                }

                Pokemon pokemon = new Pokemon(type, specie);
                pokemon.setTrainer(trainer);
                pokemons.add(pokemon);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all Pokemons: " + e.getMessage(), e);
        }
        return pokemons;
    }
}