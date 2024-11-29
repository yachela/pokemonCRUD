package ar.edu.davinci;

import ar.edu.davinci.Model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class TestPokemon {
    private Trainer ash;
    private IType fireType, electricType, waterType, stoneType, plantType;
    private Pokemon waterPokemon, electricPokemon, stonePokemon, firePokemon, plantPokemon;

    @BeforeEach
    void setUp() {
        ash = new Trainer("Ash Ketchum", LocalDate.of(2000, 5, 22), "Kanto");
        fireType = new Fire();
        electricType = new Electric();
        waterType = new Pokemon.Water();
        stoneType = new Electric.Stone();
        plantType = new Plant();

        waterPokemon = new Pokemon(waterType, "Bulbasaur");
        electricPokemon = new Pokemon(electricType, "Pikachu");
        stonePokemon = new Pokemon(stoneType, "Geodude");
        firePokemon = new Pokemon(fireType, "Charmander");
        plantPokemon = new Pokemon(plantType, "Oddish");
    }

    @Test
    @DisplayName("Al crearse un Pokemon, su energía será por defecto 100")
    public void testPokemonEnergyOnCreation() {
        Assertions.assertEquals(100, waterPokemon.getEnergy());
    }

    @Test
    @DisplayName("Cuando ataca, debe restar vida al atacado")
    public void testPokemonAttackDecreasesEnergy() {
        firePokemon.restLife(30);
        Assertions.assertEquals(70, firePokemon.getEnergy());
    }

    @Test
    @DisplayName("Cuando un Pokémon de piedra ataca a uno de planta, resta vida")
    public void testStonePokemonAttacksPlantPokemon() {
        stonePokemon.attack(plantPokemon);
        Assertions.assertEquals(80, plantPokemon.getEnergy());
    }

    @Test
    @DisplayName("Un Pokémon sube 10 puntos de energía cuando se cura")
    public void testPokemonHealsEnergy() {
        stonePokemon.restLife(70);
        stonePokemon.healing(10);
        Assertions.assertEquals(40, stonePokemon.getEnergy());
    }

    @Test
    @DisplayName("Si un tipo eléctrico ataca a uno de tipo agua, le produce un 50% más de daño")
    public void testElectricPokemonAttacksWaterPokemon() {
        electricPokemon.attack(waterPokemon);
        Assertions.assertEquals(70, waterPokemon.getEnergy());
    }

    @Test
    @DisplayName("Si un tipo eléctrico ataca a uno de tipo agua, se hace un 5% de daño a sí mismo")
    public void testElectricPokemonSelfDamage() {
        electricPokemon.attack(waterPokemon);
        Assertions.assertEquals(95, electricPokemon.getEnergy());
    }

    @Test
    @DisplayName("Si un tipo agua ataca a un tipo fuego, le hace un 25% de daño extra.")
    public void testWaterPokemonAttacksFirePokemon() {
        waterPokemon.attack(firePokemon);
        Assertions.assertEquals(75, firePokemon.getEnergy());
    }

    @Test
    @DisplayName("Si un tipo fuego ataca a un Pokémon de tipo planta, le hace un 20% de daño.")
    public void testFirePokemonAttacksPlantPokemon() {
        firePokemon.attack(plantPokemon);
        Assertions.assertEquals(96, plantPokemon.getEnergy());
    }

    @Test
    @DisplayName("Si un tipo planta ataca a un tipo piedra, no le produce daño")
    public void testPlantPokemonAttacksStonePokemon() {
        plantPokemon.attack(stonePokemon);
        Assertions.assertEquals(100, stonePokemon.getEnergy());
    }

    @Test
    @DisplayName("Un Pokémon puede ser capturado al llegar a 0 de energía")
    public void testPokemonCanBeCaptured() {
        ash.capturePokemon(waterPokemon);
        firePokemon.restLife(100);
        if (firePokemon.isCapturable()) {
            ash.capturePokemon(firePokemon);
        }
        Assertions.assertEquals(2, ash.getPokemonList().size());
    }
}