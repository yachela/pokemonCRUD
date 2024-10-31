package ar.edu.davinci;
import java.sql.*;
import java.util.List;

public class PokemonDAOimplementsH2 implements PokemonDAO {

    private final Connection connection;

    public PokemonDAOimplementsH2(Connection connection) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "", "");
        this.createTable();
       // this.connection = JdbcConfiguration.getDBConnection();
    }

    private void createTable() {
        ///Cambiar a datos bd Pokemon
        String sql = "CREATE TABLE IF NOT EXISTS producto (id INT PRIMARY KEY, name VARCHAR(255), description VARCHAR(255), price INT, sotck INT, category VARCHAR(255))";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void insertPokemon(PokemonDTO pokemonDTO) {
        try {
            Pokemon newPokemon = new Pokemon();
            newPokemon.getTipo(pokemonDTO.getTipo());
            newPokemon.getEnergia(pokemonDTO.getEnergia());
            newPokemon.getPoder(pokemonDTO.getPoder());
            newPokemon.getEspecie(pokemonDTO.getEspecie());
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO pokemon( tipo, energia, poder, especie) VALUES(?,?,?,?)");
            preparedStatement.setString(1, newPokemon.getNombre());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updatePokemon(PokemonDTO pokemonDTO) {
            String sql = "UPDATE pokemons SET name = ?, price = ?, description = ? WHERE id = ?";

            try (PreparedStatement stmt = connection.) {


                Pokemon product = dtoToEntity(productDTO);
                stmt.setString(1, product.getName());
                stmt.setDouble(2, product.getPrice());
                stmt.setString(3, product.getDescription());
                stmt.setLong(4, product.getId());

                stmt.executeUpdate();
    }

    @Override
    public void deletePokemon(int pokemonID) {
        try{

        }
    }
}

}
