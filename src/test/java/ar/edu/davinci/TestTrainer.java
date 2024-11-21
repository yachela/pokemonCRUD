package ar.edu.davinci;

import ar.edu.davinci.Model.Pokemon;
import ar.edu.davinci.Model.Trainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TestTrainer {
    private Trainer trainer;
    private Trainer opponentTrainer;
    private IType fireType;
    private IType waterType;
    private Pokemon charmander;
    private Pokemon squirtle;

    @BeforeEach
    void setUp() {
        trainer = new Trainer("Aria", LocalDate.of(1989, 5, 20), "Galar");
        opponentTrainer = new Trainer("Kael", LocalDate.of(1999, 4, 12), "Galola");
        fireType = new Fire();
        waterType = new Water();
        charmander = new Pokemon(fireType, "Charmander");
        squirtle = new Pokemon(waterType, "Squirtle");
    }

    @Test
    @DisplayName("Debe permitir capturar un Pokemon cuando la lista está vacía")
    void testCaptureFirstPokemon() {
        trainer.capturePokemon(charmander);
        Assertions.assertEquals(1, trainer.getPokemonList().size());
        Assertions.assertTrue(trainer.getPokemonList().contains(charmander));
    }

    @Test
    @DisplayName("No debe permitir capturar el mismo Pokemon dos veces")
    void testCannotCaptureSamePokemonTwice() {
        trainer.capturePokemon(charmander);
        trainer.capturePokemon(charmander);
        Assertions.assertEquals(1, trainer.getPokemonList().size());
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar capturar más de 5 Pokemon")
    void testCannotCaptureMoreThan5Pokemons() {
        trainer.capturePokemon(new Pokemon(fireType, "Charmander1"));
        trainer.capturePokemon(new Pokemon(waterType, "Squirtle2"));
        trainer.capturePokemon(new Pokemon(fireType, "Charmander3"));
        trainer.capturePokemon(new Pokemon(waterType, "Squirtle4"));
        trainer.capturePokemon(new Pokemon(fireType, "Charmander5"));

        Assertions.assertThrows(IllegalStateException.class, () -> {
            trainer.capturePokemon(new Pokemon(waterType, "ExtraPokemon"));
        });
    }

    @Test
    @DisplayName("Trainer gana el enfrentamiento si el oponente no tiene Pokémon activos")
    void testTrainerWinsBattle() {
        trainer.capturePokemon(charmander);
        opponentTrainer.capturePokemon(squirtle);

        squirtle.restLife(100);

        String result = trainer.faceTrainer(opponentTrainer);
        Assertions.assertEquals("Aria gano la batalla!", result);
    }

    @Test
    @DisplayName("El enfrentamiento termina en empate si ambos entrenadores no tienen Pokémon activos")
    void testBattleEndsInDraw() {
        trainer.capturePokemon(charmander);
        opponentTrainer.capturePokemon(squirtle);

        charmander.restLife(100);
        squirtle.restLife(100);

        String result = trainer.faceTrainer(opponentTrainer);
        Assertions.assertEquals("La batalla termino, en empate!", result);
    }

    @Test
    @DisplayName("Trainer pierde el enfrentamiento si no tiene Pokémon activos")
    void testTrainerLosesBattle() {
        trainer.capturePokemon(charmander);
        opponentTrainer.capturePokemon(squirtle);

        charmander.restLife(100);

        String result = trainer.faceTrainer(opponentTrainer);
        Assertions.assertEquals("Kael gano la batalla!", result);
    }
}