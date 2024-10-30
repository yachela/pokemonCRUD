package ar.edu.davinci;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.Period;

import java.util.ArrayList;
import java.util.List;

public class Entrenador {

    private String name;
    private final LocalDate birthDate;
    private int age;
    private String nacionality;
    private List<Pokemon> pokemonList;
    public Entrenador(String nombre, LocalDate birthDate, String nacionality) {
        this.name = nombre;
        this.birthDate = birthDate;
        this.nacionality = nacionality;
        this.pokemonList = new ArrayList<>();
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

    public void capturarPokemon(Pokemon pokemon) {
        if (this.pokemonList.size() < 5) {
            this.pokemonList.add(pokemon);
        } else {
            throw new IllegalStateException("No se puede tener más de 5 Pokemons");
        }
    }

}
