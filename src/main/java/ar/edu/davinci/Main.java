package ar.edu.davinci;

import ar.edu.davinci.DAO.PokemonDAOImplH2;
import ar.edu.davinci.DAO.TrainerDAOImplH2;
import ar.edu.davinci.Model.Pokemon;
import ar.edu.davinci.Model.Trainer;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        PokemonDAOImplH2 pokemonDAO = new PokemonDAOImplH2();
        TrainerDAOImplH2 trainerDAO = new TrainerDAOImplH2();

        Trainer casia = new Trainer("Casia", LocalDate.of(2006, 4, 22), "Argentina");
        Trainer kuru = new Trainer("Kuru", LocalDate.of(1998, 4, 10), "Peru");
        trainerDAO.insertTrainer(casia);
        trainerDAO.insertTrainer(kuru);

        IType fireType = new Fire();
        IType waterType = new Water();
        IType electricType = new Electric();

        Pokemon pikachu = new Pokemon(electricType, "Pikachu");
        pikachu.setTrainer(casia);
        pokemonDAO.insertPokemon(pikachu);

        Pokemon charmander = new Pokemon(fireType, "Charmander");
        charmander.setTrainer(casia);
        pokemonDAO.insertPokemon(charmander);

        Pokemon squirtle = new Pokemon(waterType, "Squirtle");
        squirtle.setTrainer(kuru);
        pokemonDAO.insertPokemon(squirtle);

        System.out.println("\nPokémon capturados por " + casia.getName() + ":");
        casia.capturePokemon(pikachu);
        casia.capturePokemon(charmander);

        System.out.println("\nPokémon capturados por " + kuru.getName() + ":");
        kuru.capturePokemon(squirtle);


        System.out.println("\nEnfrentamiento entre " + casia.getName() + " y " + kuru.getName() + ":");
        String battleResult = casia.faceTrainer(kuru);
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