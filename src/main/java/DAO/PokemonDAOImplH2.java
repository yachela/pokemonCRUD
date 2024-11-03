package DAO;

import Model.Pokemon;
import ar.edu.davinci.IType;

import java.sql.*;
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
            String createTableQuery = "CREATE TABLE IF NOT EXISTS pokemon (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "type VARCHAR(50), " +
                    "energy FLOAT DEFAULT 100," +
                    "power INT, " +
                    "especie VARCHAR(100))";
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
            String insertQuery = "INSERT INTO pokemon (type, power, specie) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, pokemon.getType().getClass().getSimpleName());
            //  preparedStatement.setFloat(2, pokemon.getEnergy());
            preparedStatement.setFloat(2, pokemon.getPower());
            preparedStatement.setString(3, pokemon.getSpecie());
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
            String updateQuery = "UPDATE pokemon SET type = ?, power = ?, specie = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, String.valueOf(pokemon.getType()));
            // preparedStatement.setFloat(2, pokemon.getEnergy());
            preparedStatement.setFloat(2, pokemon.getPower());
            preparedStatement.setString(3, pokemon.getSpecie());
            preparedStatement.setInt(4, pokemon.getId());

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
                String typeStr = resultSet.getString("type");
                IType type = null;
                if (typeStr != null && !typeStr.trim().isEmpty()) {
                    type = IType.fromString(typeStr);
                } else {
                    System.out.println("Tipo desconocido o nulo para el Pokémon con ID: " + resultSet.getInt("id"));
                    continue;
                }

                Float power = resultSet.getFloat("power");
                String specie = resultSet.getString("species");
                int id = resultSet.getInt("id");

                Pokemon pokemon = new Pokemon(type, power, specie, id);
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