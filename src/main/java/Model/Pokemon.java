package Model;
import ar.edu.davinci.IType;
public class Pokemon {
    private IType type;
    private float energy;
    private final float maxEnergy = 100;
    private float power;
    private String specie;
    private int id;

    public Pokemon(IType type, float power, String specie, int id) {
        this.type = type;
        this.energy = 100;
        this.power = power;
        this.specie = specie;
        this.id = id;
    }

    public Pokemon() {
        this.energy = 100;
        this.power = 30;
    }

    public int getId() {
        return id;
    }

    public IType getType() {
        return type;
    }

    public Float getEnergy() {
        return energy;
    }

    public float getPower() {
        return this.power;
    }

    public String getSpecie() {
        return this.specie;
    }

    public void attack(Pokemon otherPokemon) {

        otherPokemon.restLife(this.power);
    }

    public void restLife(float cant) {
        if(cant<0){
            throw new IllegalArgumentException("El dano no puede ser negativo");
        }
        this.energy -= cant;
    }

    public void healing() {
        if (this.energy < 100) {
            this.energy += 10;
            if (this.energy > 100) {
                this.energy = 100;
            }
        }
    }
}