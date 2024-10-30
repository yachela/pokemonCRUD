package ar.edu.davinci;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.Period;

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

}
