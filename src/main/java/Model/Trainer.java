package Model;

import java.time.LocalDate;
import java.time.Period;

import java.util.ArrayList;
import java.util.List;
///TODO CAMBIAR VARIABLES A INGLES
public class Trainer {
    private int id;
    private String name;
    private LocalDate birthDate;
    private int age;
    private String nacionality;
    private List<Pokemon> pokemonList;

    public Trainer(int id, String name, LocalDate birthDate, String nacionality) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.nacionality = nacionality;
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
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        if (birthDate == null) {
            return 0;
        }else{
            return Period.between(birthDate, LocalDate.now()).getYears();
        }
    }
    public String getNacionality() {
        return nacionality;
    }
    public void setNacionality(String nacionality) {
        this.nacionality = nacionality;
    }
    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }
    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public String getNationality() {
        return nacionality;
    }
    public void pokemonCapture(Pokemon pokemon) {
        if (this.pokemonList.size() < 5) {
            this.pokemonList.add(pokemon);
        } else {
            throw new IllegalStateException("No se puede tener más de 5 Pokemons");
        }
    }

    public void faceTrainer(Trainer otherTrainer) {
        System.out.println(this.name + " is facing " + otherTrainer.getName() + "!");
       ///TODO completar la logica de enfrentamiento con otros trainers
    }



}
