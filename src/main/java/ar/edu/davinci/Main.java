package ar.edu.davinci;

import DAO.PokemonDAOImplH2;
import DAO.TrainerDAOImplH2;
import Model.Pokemon;
import Model.Trainer;
import ar.edu.davinci.IType;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        PokemonDAOImplH2 pokemonDAO = new PokemonDAOImplH2();
        TrainerDAOImplH2 trainerDAO = new TrainerDAOImplH2();

        Trainer ash = new Trainer("Ash Ketchum", LocalDate.of(1997, 5, 22), "Kanto");
        Trainer misty = new Trainer("Misty", LocalDate.of(1998, 4, 10), "Cerulean");
        trainerDAO.insertTrainer(ash);
        trainerDAO.insertTrainer(misty);

        IType fireType = new Fire();
        IType waterType = new Water();
        IType electricType = new Electric();

        Pokemon pikachu = new Pokemon(electricType, "Pikachu");
        pikachu.setTrainer(ash);
        pokemonDAO.insertPokemon(pikachu);

        Pokemon charmander = new Pokemon(fireType, "Charmander");
        charmander.setTrainer(ash);
        pokemonDAO.insertPokemon(charmander);

        Pokemon squirtle = new Pokemon(waterType, "Squirtle");
        squirtle.setTrainer(misty);
        pokemonDAO.insertPokemon(squirtle);

        System.out.println("\nPokémon capturados por " + ash.getName() + ":");
        ash.capturePokemon(pikachu);
        ash.capturePokemon(charmander);

        System.out.println("\nPokémon capturados por " + misty.getName() + ":");
        misty.capturePokemon(squirtle);


        System.out.println("\nEnfrentamiento entre " + ash.getName() + " y " + misty.getName() + ":");
        String battleResult = ash.faceTrainer(misty);
        System.out.println("Resultado del enfrentamiento: " + battleResult);


        System.out.println("\nLista de todos los Pokémon en la base de datos:");
        for (Pokemon pokemon : pokemonDAO.getAllPokemons()) {
            System.out.println("ID: " + pokemon.getId());
            System.out.println("Especie: " + pokemon.getSpecie());
            System.out.println("Tipo: " + pokemon.getType().getClass().getSimpleName());
            System.out.println("Entrenador: " + (pokemon.getTrainer() != null ? pokemon.getTrainer().getName() : "Sin entrenador"));
            System.out.println("Energía: " + pokemon.getEnergy());
            System.out.println("Power: " + pokemon.getPower());
            System.out.println("------");
        }


        pikachu.setEnergy(50);
        pokemonDAO.updatePokemon(pikachu);

        System.out.println("\nEliminando a Charmander de la base de datos...");
        pokemonDAO.deletePokemon(charmander.getId());
    }
}