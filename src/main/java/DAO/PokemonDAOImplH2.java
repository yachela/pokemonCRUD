package DAO;
import Model.Pokemon;
import ar.edu.davinci.IType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAOImplH2 implements PokemonDAO {
    private static final String URL = "jdbc:h2:./data/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final String TABLE_NAME = "pokemon";
    private Connection connection;

    public PokemonDAOImplH2() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS pokemon (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "type VARCHAR(50), " +
                    "energy FLOAT, " +
                    "power INT, " +
                    "species VARCHAR(100))";
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
            String insertQuery = "INSERT INTO pokemon (type, energy, power, species) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, pokemon.getType().getClass().getSimpleName());
            preparedStatement.setFloat(2, pokemon.getEnergy());
            preparedStatement.setInt(3, pokemon.getPower());
            preparedStatement.setString(4, pokemon.getSpecie());
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
            String updateQuery = "UPDATE pokemon SET type = ?, energy = ?, power = ?, specie = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, String.valueOf(pokemon.getType()));
            preparedStatement.setFloat(2, pokemon.getEnergy());
            preparedStatement.setInt(3, pokemon.getPower());
            preparedStatement.setString(4, pokemon.getSpecie());
            preparedStatement.setInt(5, pokemon.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Pokemon with ID " + pokemon.getId() + " not found");
            }
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
            Statement statement = connection.createStatement();
            String selectQuery = "SELECT * FROM pokemon";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                IType type = resultSet.getObject("type", IType.class);
                Float energy = resultSet.getFloat("energy");
                int power = resultSet.getInt("power");
                String species = resultSet.getString("species");
                int id = resultSet.getInt("id");

                Pokemon pokemon = new Pokemon(type, energy, power, species, id);
                pokemons.add(pokemon); // Added this missing line
            }
            return pokemons;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all Pokemons: " + e.getMessage(), e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing connection: " + e.getMessage(), e);
        }
    }
}