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

        IType fireType = new Fire() {};
        Pokemon charmander = new Pokemon(fireType, 50, "Charmander", 0);
        pokemonDAO.insertPokemon(charmander);


        pokemonDAO.getAllPokemons().forEach(pokemon -> {
            System.out.println("ID: " + pokemon.getId() + ", Especie: " + pokemon.getSpecie() + ", Tipo: " + pokemon.getType());
        });


        Trainer ash = new Trainer(0, "Ash Ketchum", LocalDate.of(2000, 5, 22), "Kanto");
        trainerDAO.insertTrainer(ash);


        trainerDAO.getAllTrainers().forEach(trainer -> {
            System.out.println("ID: " + trainer.getId() + ", Nombre: " + trainer.getName() + ", Nacionalidad: " + trainer.getNationality());
        });

        trainerDAO.deleteTrainer(1);


    }
}