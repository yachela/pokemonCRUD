package ar.edu.davinci;

import Model.Pokemon;
import Model.Trainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
class TestPokemon {
    Trainer ash = new Trainer( "Ash Ketchum", LocalDate.of(2000, 5, 22), "Kanto");
    private IType fireType = new Fire();
    private IType electricType = new Electric();;
    private IType waterType = new Water();
    private IType stoneType = new Stone();
    private IType plantType = new Plant();
    Pokemon waterPokemon = new Pokemon(waterType, "Bulbasur");
    Pokemon electricPokemon = new Pokemon(electricType, "Pikachu");
    Pokemon stonePokemon = new Pokemon(stoneType, "Geodude");
    Pokemon firePokemon = new Pokemon(fireType, "Charmander");
    Pokemon plantPokemon = new Pokemon(plantType, "Oddish");


    @Test
    @DisplayName("Al crearse un Pokemon, su energia sera por defecto 100")
    public void testAlCrearPokemonSuEnergiaEsCien(){
        Pokemon pokemon = new Pokemon(waterType, "Bulbasur");
        Assertions.assertEquals(100, pokemon.getEnergy());
    }

    @Test
    @DisplayName("Cuando ataca debe restar vida al atacado")
    public void testUnPokemonRestaVidaCuandoAtaca() {
        firePokemon.restLife(30);
        Assertions.assertEquals(70, firePokemon.getEnergy());
    }

    @Test
    public void testUnPokemonRestaVidaCuandoAtacaAotroPokemon() {
        stonePokemon.attack(plantPokemon);
        Assertions.assertEquals(80, plantPokemon.getEnergy());
    }

    @Test
    public void testUnPokemonSube10PuntosDeEnergiaCuandoSeCura(){
        stonePokemon.restLife(70);
        stonePokemon.healing();
        Assertions.assertEquals(40, stonePokemon.getEnergy());
    }
    @Test
    @DisplayName("Si un tipo eléctrico ataca a uno de tipo agua, le produce un 50% más de daño")
    public void testUnPokemonElectricoAtacaUnPokemonAgua(){
        electricPokemon.attack(waterPokemon);
        Assertions.assertEquals(70, waterPokemon.getEnergy());
    }

    @Test
    @DisplayName("Si un tipo eléctrico ataca a uno de tipo agua, se hace a sí mismo un 5% de daño.cado")
    public void testUnPokemonElectricoAtacaUnPokemonAguaSeDania(){
        Pokemon waterPokemon = new Pokemon(waterType, "Bulbasur");
        Pokemon electricPokemon = new Pokemon(electricType, "Pikachu");
        electricPokemon.attack(waterPokemon);
        Assertions.assertEquals(95, electricPokemon.getEnergy());
    }

    @Test
    @DisplayName("Si un tipo agua ataca a un tipo fuego, le hace un 25% de daño extra.")
    public void testUnPokemonAguaAtacaUnPokemonFuego(){
        waterPokemon.attack(firePokemon);
        Assertions.assertEquals(75, firePokemon.getEnergy());
    }

    @Test
    @DisplayName("Si un tipo fuego ataca a un Pokémon de tipo planta, le hace un 20% de daño.")
    public void testUnPokemonFuegoAtacaUnPokemonPlanta(){
        firePokemon.attack(plantPokemon);
        Assertions.assertEquals(96, plantPokemon.getEnergy());
    }

    @Test
    @DisplayName("Si un tipo planta ataca a un tipo piedra, no le produce daño")
    public void testUnPokemonPlantaAtacaUnPokemonPiedra(){
        plantPokemon.attack(stonePokemon);
        Assertions.assertEquals(100, stonePokemon.getEnergy());
    }

    @Test
    @DisplayName("Un pokemon puede ser capturado al llegar a 0 de energia")
    public void testUnPokemonPuedeSerCapturado(){
        waterPokemon.setTrainer(ash);
        waterPokemon.attack(firePokemon);
        waterPokemon.attack(firePokemon);
        waterPokemon.attack(firePokemon);
        waterPokemon.attack(firePokemon);
        Assertions.assertEquals(2, ash.getPokemonList().size());
    }
}