package Model;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Trainer {
    private int id;
    private String name;
    private final LocalDate birthDate;
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

    public int getAge() {
        if (birthDate == null) {
            return 0;
        } else {
            return Period.between(birthDate, LocalDate.now()).getYears();
        }
    }

    public String getNacionality() {
        return nacionality;
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

    public String getNationality() {
        return nacionality;
    }

    public void pokemonCapture(Pokemon pokemon) {
        if (this.pokemonList.size() < 5) {
            if (!this.pokemonList.contains(pokemon)) {
                this.pokemonList.add(pokemon);
                System.out.println("Pokemon capturado");
            } else {
                System.out.println("Este Pokemon ya está en tu equipo");
            }
        } else {
            throw new IllegalStateException("No podes tener más de 5 Pokemons");
        }
    }

    public void faceTrainer(Trainer otherTrainer) {
        System.out.println(this.name + " esta enfrentando a  " + otherTrainer.getName());

    }


}
