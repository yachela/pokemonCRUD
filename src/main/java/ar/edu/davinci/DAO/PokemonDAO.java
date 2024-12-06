package ar.edu.davinci.DAO;

import ar.edu.davinci.Model.Pokemon;

import java.util.List;

public interface PokemonDAO {
    void insertPokemon(Pokemon pokemon);

    List<Pokemon> getAllPokemons();

    Pokemon getPokemonById(int pokemonID);

    void updatePokemon(Pokemon pokemon);

    void deletePokemon(int pokemonID);

    boolean capturePokemon(int trainerId, int pokemonId);

    String battlePokemons(int pokemon1Id, int pokemon2Id);
}