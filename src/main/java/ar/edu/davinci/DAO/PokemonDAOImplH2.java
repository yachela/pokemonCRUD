package ar.edu.davinci.DAO;

import ar.edu.davinci.Model.Pokemon;
import ar.edu.davinci.Model.Trainer;
import ar.edu.davinci.Interface.IType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAOImplH2 implements PokemonDAO {
    private static final String URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public PokemonDAOImplH2() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String createTableQuery = """
                CREATE TABLE IF NOT EXISTS pokemon (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    type VARCHAR(50),
                    energy FLOAT DEFAULT 100,
                    power INT,
                    specie VARCHAR(100),
                    trainer_id INT,
                    FOREIGN KEY (trainer_id) REFERENCES trainer(id)
                )
            """;
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing Pokemon table: " + e.getMessage(), e);
        }
    }

    @Override
    public void insertPokemon(Pokemon pokemon) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO pokemon (type, power, specie, trainer_id) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, pokemon.getType().getClass().getSimpleName());
            preparedStatement.setFloat(2, pokemon.getPower());
            preparedStatement.setString(3, pokemon.getSpecie());
            if (pokemon.getTrainer() != null) {
                preparedStatement.setInt(4, pokemon.getTrainer().getId());
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }
            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                pokemon.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Pokemon: " + e.getMessage(), e);
        }
    }

    @Override
    public Pokemon getPokemonById(int pokemonId) {
        Pokemon pokemon = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pokemon WHERE id = ?")) {

            preparedStatement.setInt(1, pokemonId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String typeStr = resultSet.getString("type");
                IType type = IType.fromString(typeStr);
                String specie = resultSet.getString("specie");
                float energy = resultSet.getFloat("energy");
                float power = resultSet.getFloat("power");
                int trainerId = resultSet.getInt("trainer_id");

                Trainer trainer = null;
                if (!resultSet.wasNull()) {
                    trainer = new TrainerDAOImplH2().getTrainerById(trainerId);
                }

                pokemon = new Pokemon(type, specie);
                pokemon.setId(pokemonId);
                pokemon.setEnergy(energy);
                pokemon.setPower(power);
                pokemon.setTrainer(trainer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching Pokemon by ID: " + e.getMessage(), e);
        }
        return pokemon;
    }

    @Override
    public List<Pokemon> getAllPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("""
                 SELECT p.*, t.id AS trainer_id, t.name AS trainer_name, t.birth_date, t.nationality 
                 FROM pokemon p 
                 LEFT JOIN trainer t ON p.trainer_id = t.id
             """)) {

            while (resultSet.next()) {
                String typeStr = resultSet.getString("type");
                IType type = IType.fromString(typeStr);
                String specie = resultSet.getString("specie");
                float power = resultSet.getFloat("power");
                float energy = resultSet.getFloat("energy");
                int pokemonId = resultSet.getInt("id");

                Trainer trainer = null;
                int trainerId = resultSet.getInt("trainer_id");
                if (!resultSet.wasNull()) {
                    trainer = new TrainerDAOImplH2().getTrainerById(trainerId);
                }

                Pokemon pokemon = new Pokemon(type, specie);
                pokemon.setId(pokemonId);
                pokemon.setEnergy(energy);
                pokemon.setPower(power);
                pokemon.setTrainer(trainer);

                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all Pokemons: " + e.getMessage(), e);
        }
        return pokemons;
    }

    @Override
    public void updatePokemon(Pokemon pokemon) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE pokemon SET type = ?, power = ?, specie = ?, trainer_id = ? WHERE id = ?")) {

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
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Pokemon: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletePokemon(int pokemonId) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pokemon WHERE id = ?")) {

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
    public boolean capturePokemon(int trainerId, int pokemonId) {
        Pokemon pokemon = getPokemonById(pokemonId);
        if (pokemon.getEnergy() > 0) {
            throw new IllegalStateException("El Pokémon no es capturable porque aún tiene energía > 0");
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO trainer_pokemon (trainer_id, pokemon_id) VALUES (?, ?)")) {

            preparedStatement.setInt(1, trainerId);
            preparedStatement.setInt(2, pokemonId);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error capturing Pokemon: " + e.getMessage(), e);
        }
    }

    @Override
    public String battlePokemons(int pokemon1Id, int pokemon2Id) {
        Pokemon pokemon1 = getPokemonById(pokemon1Id);
        Pokemon pokemon2 = getPokemonById(pokemon2Id);

        if (pokemon1 == null || pokemon2 == null) {
            return "Uno de los Pokémon no existe.";
        }

        while (pokemon1.getEnergy() > 0 && pokemon2.getEnergy() > 0) {
            pokemon1.attack(pokemon2);
            if (pokemon2.getEnergy() > 0) {
                pokemon2.attack(pokemon1);
            }
        }

        String winner = pokemon1.getEnergy() > 0 ? pokemon1.getSpecie() : pokemon2.getSpecie();
        return "La batalla ha terminado. El ganador es: " + winner;
    }
}