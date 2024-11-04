package ar.edu.davinci;

import Model.Pokemon;
import Model.Trainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class TestPokemon {

    private Pokemon charmander;
    private IType fireType;


    @BeforeEach
    void setUp() {
        charmander = new Pokemon(fireType, 50, "Charmander", 0);
        fireType = new Fire();
    }
    @Test
    @DisplayName("Al crearse un Pokemon, su energia sera por defecto 100")
    public void testAlCrearPokemonSuEnergiaEsCien(){
        Pokemon pokemon = new Pokemon();
        Assertions.assertEquals(100, pokemon.getEnergy());
    }

    @Test
    @DisplayName("Cuando ataca debe restar vida al atacado")
    public void testUnPokemonRestaVidaCuandoAtaca() {
        Pokemon newPokemon = new Pokemon();
        newPokemon.restLife(30);
        Assertions.assertEquals(70, newPokemon.getEnergy());
    }

    @Test
    public void testUnPokemonRestaVidaCuandoAtacaAotroPokemon() {
        Pokemon newPokemon = new Pokemon();
        Pokemon newPokemon2 = new Pokemon();
        newPokemon.attack(newPokemon2);
        Assertions.assertEquals(70, newPokemon2.getEnergy());
    }

    @Test

    public void testUnPokemonSube10PuntosDeEnergiaCuandoSeCura(){
        Pokemon newPokemon = new Pokemon();
        newPokemon.restLife(70);
        newPokemon.healing();
        Assertions.assertEquals(40, newPokemon.getEnergy());
    }



}