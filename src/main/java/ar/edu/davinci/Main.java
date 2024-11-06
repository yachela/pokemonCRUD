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
        Trainer trainer = new Trainer("Ash Ketchum", LocalDate.of(1997, 5, 22), "Kanto");
        TrainerDAOImplH2 trainerDAO = new TrainerDAOImplH2();
        trainerDAO.insertTrainer(trainer);

        IType fireType = new Fire();
        Pokemon pikachu = new Pokemon(fireType, "Pikachu");
        pikachu.setTrainer(trainer);

        pokemonDAO.insertPokemon(pikachu);

        System.out.println("Lista de Pokemon:");
        for (Pokemon pokemon : pokemonDAO.getAllPokemons()) {
            System.out.println("ID: " + pokemon.getId());
            System.out.println("Especie: " + pokemon.getSpecie());
            System.out.println("Tipo: " + pokemon.getType().getClass().getSimpleName());
            System.out.println("Entrenador: " + (pokemon.getTrainer() != null ? pokemon.getTrainer().getName() : "Sin entrenador"));
            System.out.println("Energía: " + pokemon.getEnergy());
            System.out.println("Power: " + pokemon.getPower());
        }

    }
}