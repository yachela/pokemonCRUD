package Model;
import ar.edu.davinci.IType;
public class Pokemon {
    private int id;
    private IType type;
    private float energy;
    private final float maxEnergy = 100;
    private float power;
    private String specie;
    private Trainer trainer;


    public Pokemon(IType type, String specie) {
        if (type == null) {
            throw new IllegalArgumentException("El tipo no puede ser null");
        }
        this.type = type;
        this.energy = 100;
        this.power = 20;
        this.specie = specie;
        this.trainer = null;
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

    public void setTrainer(Trainer trainer) {
            this.trainer = trainer;
            trainer.pokemonCapture(this);
    }
    public Trainer getTrainer() {
        return trainer;
    }

    public String getSpecie() {
        return this.specie;
    }

    public String getTrainerToString() {
        return trainer.getName();
    }

    public void attack(Pokemon otherPokemon) {
        otherPokemon.restLife(this.power * this.type.damageMultiplicator(otherPokemon.type));
        this.restLife(this.energy * this.type.takeDamage(otherPokemon.type));
        if (otherPokemon.isCapturable()){
            this.trainer.pokemonCapture(otherPokemon);
        }
    }

    public boolean isCapturable(){
        return this.getEnergy() <= 0.0;
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