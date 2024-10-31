package ar.edu.davinci;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAOImplementsH2 implements PokemonDAO {
    private final Connection connection;

    // Constructor mejorado con manejo de errores
    public PokemonDAOImplementsH2() throws SQLException {
        // Es mejor usar una configuración centralizada para la conexión
        this.connection = DriverManager.getConnection("jdbc:h2:mem:pokemon_db;DB_CLOSE_DELAY=-1", "", "");
        this.createTable();
    }

    private void createTable() {
        // SQL corregido para la tabla Pokemon
        String sql = "CREATE TABLE IF NOT EXISTS pokemon (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "tipo VARCHAR(50), " +
                "energia DOUBLE, " +
                "poder INT, " +
                "especie VARCHAR(100))";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating Pokemon table: " + e.getMessage(), e);
        }
    }

    @Override
    public void insertPokemon(PokemonDTO pokemonDTO) {
        String sql = "INSERT INTO pokemon (tipo, energia, poder, especie) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Corrección: usar setters en lugar de getters para establecer valores
            preparedStatement.setString(1, pokemonDTO.getTipo());
            preparedStatement.setDouble(2, pokemonDTO.getEnergia());
            preparedStatement.setInt(3, pokemonDTO.getPoder());
            preparedStatement.setString(4, pokemonDTO.getEspecie());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Pokemon: " + e.getMessage(), e);
        }
    }

    @Override
    public void updatePokemon(PokemonDTO pokemonDTO) {
        String sql = "UPDATE pokemon SET tipo = ?, energia = ?, poder = ?, especie = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pokemonDTO.getTipo());
            stmt.setDouble(2, pokemonDTO.getEnergia());
            stmt.setInt(3, pokemonDTO.getPoder());
            stmt.setString(4, pokemonDTO.getEspecie());
            stmt.setInt(5, pokemonDTO.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Pokemon with ID " + pokemonDTO.getId() + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Pokemon: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletePokemon(int pokemonId) {
        String sql = "DELETE FROM pokemon WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pokemonId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Pokemon with ID " + pokemonId + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Pokemon: " + e.getMessage(), e);
        }
    }

    // Agregando métodos adicionales útiles
    public Pokemon getPokemonById(int id) {
        String sql = "SELECT * FROM pokemon WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPokemon(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting Pokemon: " + e.getMessage(), e);
        }
    }

    public List<Pokemon> getAllPokemons() {
        String sql = "SELECT * FROM pokemon";
        List<Pokemon> pokemons = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pokemons.add(mapResultSetToPokemon(rs));
            }
            return pokemons;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all Pokemons: " + e.getMessage(), e);
        }
    }

    private Pokemon mapResultSetToPokemon(ResultSet rs) throws SQLException {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(rs.getInt("id"));
        pokemon.setTipo(rs.getString("tipo"));
        pokemon.setEnergia(rs.getDouble("energia"));
        pokemon.setPoder(rs.getInt("poder"));
        pokemon.setEspecie(rs.getString("especie"));
        return pokemon;
    }

    // Método para cerrar la conexión
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