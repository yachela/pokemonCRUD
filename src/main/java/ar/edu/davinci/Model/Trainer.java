package ar.edu.davinci.Model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Trainer {
    private int id;
    private String name;
    private final LocalDate birthDate;
    private String nationality;
    private List<Pokemon> pokemonList;

    public Trainer(String name, LocalDate birthDate, String nationality) {
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.pokemonList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void capturePokemon(Pokemon pokemon) {
        if (this.pokemonList.size() < 5) {
            if (!this.pokemonList.contains(pokemon)) {
                this.pokemonList.add(pokemon);
                pokemon.setTrainer(this);
                System.out.println("Pokemon capturado!");
            } else {
                System.out.println("Ya tenes este Pokemon.");
            }
        } else {
            throw new IllegalStateException("No podes tener mas de 5 pokemons.");
        }
    }


    public String faceTrainer(Trainer otherTrainer) {
        System.out.println(this.name + " esta enfrentando a  " + otherTrainer.getName());

        while (this.hasAlivePokemon() && otherTrainer.hasAlivePokemon()) {

            Pokemon myPokemon = this.getFirstActivePokemon();
            Pokemon opponentPokemon = otherTrainer.getFirstActivePokemon();

            if (myPokemon == null || opponentPokemon == null) break;

            System.out.println(this.name + " utiliza " + myPokemon.getSpecie() + " contra " + otherTrainer.getName() + " con su pokemon " + opponentPokemon.getSpecie());


            myPokemon.attack(opponentPokemon);
            if (opponentPokemon.getEnergy() > 0) {
                opponentPokemon.attack(myPokemon);
            }


            System.out.println(myPokemon.getSpecie() + " estado: energia = " + myPokemon.getEnergy());
            System.out.println(opponentPokemon.getSpecie() + " estado: energia = " + opponentPokemon.getEnergy());
        }


        if (this.hasAlivePokemon() && !otherTrainer.hasAlivePokemon()) {

            return this.name + " gano la batalla!";
        } else if (!this.hasAlivePokemon() && otherTrainer.hasAlivePokemon()) {

            return otherTrainer.getName() + " gano la batalla!";
        } else {
            System.out.println("La batalla termino, en empate!");
            return "La batalla termino, en empate!";
        }
    }

    public boolean hasAlivePokemon() {
        return this.pokemonList.stream().anyMatch(pokemon -> pokemon.getEnergy() > 0);
    }


    private Pokemon getFirstActivePokemon() {
        return this.pokemonList.stream().filter(pokemon -> pokemon.getEnergy() > 0).findFirst().orElse(null);
    }
}