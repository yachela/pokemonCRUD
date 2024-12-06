package ar.edu.davinci.DAO;

import ar.edu.davinci.Model.Pokemon;
import ar.edu.davinci.Model.Trainer;
import ar.edu.davinci.Interface.IType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainerDAOImplH2 implements TrainerDAO {
    private static final String URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public TrainerDAOImplH2() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            String createTrainerTable = """
                CREATE TABLE IF NOT EXISTS trainer (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    birth_date DATE NOT NULL,
                    nationality VARCHAR(50)
                )
            """;
            statement.executeUpdate(createTrainerTable);

            String createTrainerPokemonTable = """
                CREATE TABLE IF NOT EXISTS trainer_pokemon (
                    trainer_id INT,
                    pokemon_id INT,
                    PRIMARY KEY (trainer_id, pokemon_id),
                    FOREIGN KEY (trainer_id) REFERENCES trainer(id),
                    FOREIGN KEY (pokemon_id) REFERENCES pokemon(id)
                )
            """;
            statement.executeUpdate(createTrainerPokemonTable);
        } catch (SQLException e) {
            throw new RuntimeException("Error creando la tabla: " + e.getMessage(), e);
        }
    }

    @Override
    public void insertTrainer(Trainer trainer) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO trainer (name, birth_date, nationality) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, trainer.getName());
            preparedStatement.setDate(2, Date.valueOf(trainer.getBirthDate()));
            preparedStatement.setString(3, trainer.getNationality());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                trainer.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error agregando trainer: " + e.getMessage(), e);
        }
    }

    @Override
    public Trainer getTrainerById(int trainerId) {
        Trainer trainer = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM trainer WHERE id = ?")) {

            preparedStatement.setInt(1, trainerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                String nationality = resultSet.getString("nationality");

                trainer = new Trainer(name, birthDate, nationality);
                trainer.setId(trainerId);
                trainer.setPokemonList(getPokemonListByTrainerId(trainerId));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando trainer por ID: " + e.getMessage(), e);
        }
        return trainer;
    }

    @Override
    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM trainer")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
                String nationality = resultSet.getString("nationality");

                Trainer trainer = new Trainer(name, birthDate, nationality);
                trainer.setId(id);
                trainer.setPokemonList(getPokemonListByTrainerId(id));
                trainers.add(trainer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando todos los trainers: " + e.getMessage(), e);
        }
        return trainers;
    }

    private List<Pokemon> getPokemonListByTrainerId(int trainerId) {
        List<Pokemon> pokemonList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("""
                 SELECT p.* FROM pokemon p 
                 INNER JOIN trainer_pokemon tp ON p.id = tp.pokemon_id 
                 WHERE tp.trainer_id = ?
             """)) {

            preparedStatement.setInt(1, trainerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String typeStr = resultSet.getString("type");
                IType type = IType.fromString(typeStr);
                String specie = resultSet.getString("specie");
                float energy = resultSet.getFloat("energy");
                float power = resultSet.getFloat("power");

                Pokemon pokemon = new Pokemon(type, specie);
                pokemon.setId(id);
                pokemon.setEnergy(energy);
                pokemon.setPower(power);

                pokemonList.add(pokemon);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando Pokemons por Trainer ID: " + e.getMessage(), e);
        }
        return pokemonList;
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE trainer SET name = ?, birth_date = ?, nationality = ? WHERE id = ?")) {

            preparedStatement.setString(1, trainer.getName());
            preparedStatement.setDate(2, Date.valueOf(trainer.getBirthDate()));
            preparedStatement.setString(3, trainer.getNationality());
            preparedStatement.setInt(4, trainer.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Trainer CON id " + trainer.getId() + " no encuentra");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando Trainer: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTrainer(int trainerId) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM trainer WHERE id = ?")) {

            preparedStatement.setInt(1, trainerId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Trainer con ID " + trainerId + " no encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error borrando Trainer: " + e.getMessage(), e);
        }
    }

    @Override
    public String battleTrainers(int trainer1Id, int trainer2Id) {
        Trainer trainer1 = getTrainerById(trainer1Id);
        Trainer trainer2 = getTrainerById(trainer2Id);

        if (trainer1.getPokemonList().isEmpty() || trainer2.getPokemonList().isEmpty()) {
            return "Uno de los entrenadores no tiene Pokemones disponibles.";
        }

        Pokemon pokemon1 = trainer1.getPokemonList().get(0);
        Pokemon pokemon2 = trainer2.getPokemonList().get(0);

        while (pokemon1.getEnergy() > 0 && pokemon2.getEnergy() > 0) {
            pokemon1.attack(pokemon2);
            if (pokemon2.getEnergy() > 0) {
                pokemon2.attack(pokemon1);
            }
        }

        String winner = pokemon1.getEnergy() > 0 ? trainer1.getName() : trainer2.getName();
        return "La batalla termino. El ganador es: " + winner;
    }
}