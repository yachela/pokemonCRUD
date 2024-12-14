package ar.edu.davinci;

import ar.edu.davinci.DAO.PokemonDAO;
import ar.edu.davinci.DAO.PokemonDAOImplH2;
import ar.edu.davinci.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPokemonDao {

    private PokemonDAOImplH2 pokemonDAO;

    @BeforeEach
    public void setup() {
        pokemonDAO = new PokemonDAOImplH2();
    }

    @Test
    public void testInsertPokemon() {
        Trainer trainer = new Trainer("Ash", null, "Kanto");
        trainer.setId(1);
        Pokemon pokemon = new Pokemon(new Water(), "Squirtle");
        pokemon.setTrainer(trainer);

        assertDoesNotThrow(() -> pokemonDAO.insertPokemon(pokemon));
    }

    @Test
    public void testUpdatePokemon() {
        Pokemon pokemon = new Pokemon(new Water(), "Squirtle");
        pokemon.setId(1);
        pokemon.setPower(50);

        assertDoesNotThrow(() -> pokemonDAO.updatePokemon(pokemon));
    }

    @Test
    public void testDeletePokemon() {
        assertDoesNotThrow(() -> pokemonDAO.deletePokemon(1)); // Simula un ID existente
    }
}