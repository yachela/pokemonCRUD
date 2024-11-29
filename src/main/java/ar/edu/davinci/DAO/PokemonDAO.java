package ar.edu.davinci.DAO;

import ar.edu.davinci.Model.Pokemon;

import java.util.List;

public interface PokemonDAO {
    void insertPokemon(Pokemon pokemon);

    List<Pokemon> getAllPokemons();

    void updatePokemon(Pokemon pokemon);

    void deletePokemon(int pokemonID);
}

