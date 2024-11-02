package Model;
import ar.edu.davinci.IType;

public class Pokemon{

    ///TODO CAMBIAR VARIABLES A INGLES
    private IType type;
    private Float energy;
    private int power;
    private String specie;
    private int id;

    public Pokemon(IType type, Float energy, int power, String specie, int id) {
        this.type = type;
        this.energy = energy;
        this.power = power;
        this.specie = specie;
        this.id = id;
    }

    public Pokemon(){
    }


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public IType getType() {
        return type;
    }

    public void setType(IType type) {
        this.type = type;
    }

    public Float getEnergy() {
        return energy;
    }
    public void setEnergy(Float energy) {
        this.energy = energy;
    }

    public int getPower() {
        return this.power;
    }
    public void setPower(int power) {
        this.power = power;
    }

    public String getSpecie() {
        return this.specie;
    }
    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public void attack(Pokemon otherPokemon) {
        otherPokemon.restLife(this.power);
    }

    public void restLife(float cant) {
        this.energy -= cant;
    }

    public void healing(float cant) {
        this.energy += cant;
    }



}
