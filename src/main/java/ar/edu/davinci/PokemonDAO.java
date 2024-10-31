package ar.edu.davinci;
import java.util.List;

public interface PokemonDAO {
        void insertPokemon(PokemonDTO pokemonDTO);
       // List<Pokemon> getAllPokemons();
        void updatePokemon(Pokemon pokemon);
        void deletePokemon(int pokemonID);
    }

