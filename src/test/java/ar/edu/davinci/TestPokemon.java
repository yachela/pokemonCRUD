package ar.edu.davinci;

import Model.Pokemon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestPokemon {
    @Test
    public void testAlCrearPokemonSuEnergiaEsCien(){
        Pokemon pokemon = new Pokemon();
        Assertions.assertEquals(100, pokemon.getEnergy());
    }

    @Test
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