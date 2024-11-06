package ar.edu.davinci;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import Model.Pokemon;
import Model.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestTrainer {
    private Trainer trainer;
    private IType fireType;

    @BeforeEach
    void setUp() {
        trainer = new Trainer("Ash", LocalDate.of(2000, 5, 22), "Arg");
        fireType = new Fire();
    }

    @Test
    @DisplayName("Debe permitir capturar un Pokemon cuando la lista está vacía")
    void testCapturarPrimerPokemon() {
        Pokemon charmander = new Pokemon(fireType, "Charmander");
        trainer.pokemonCapture(charmander);
        Assertions.assertEquals(1, trainer.getPokemonList().size());
        Assertions.assertTrue(trainer.getPokemonList().contains(charmander));
    }

    @Test
    @DisplayName("No debe permitir capturar el mismo Pokemon dos veces")
    void testNoPuedeCapturarPokemonDuplicado() {
        Pokemon charmander = new Pokemon(fireType, "Charmander");
        trainer.pokemonCapture(charmander);
        trainer.pokemonCapture(charmander);
        Assertions.assertEquals(1, trainer.getPokemonList().size());
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar capturar más de 5 Pokemon")
    void testNoPuedeCapturarMasDe5Pokemons() {
    }
}