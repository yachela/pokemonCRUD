package ar.edu.davinci.Model;

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
        this.energy = maxEnergy;
        this.power = 20;
        this.specie = specie;
        this.trainer = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public IType getType() {
        return type;
    }

    public void setType(IType type) {
        if (type == null) {
            throw new IllegalArgumentException("El tipo no puede ser null");
        }
        this.type = type;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        if (energy < 0 || energy > maxEnergy) {
            throw new IllegalArgumentException("La energía debe estar entre 0 y " + maxEnergy);
        }
        this.energy = energy;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        if (power < 0) {
            throw new IllegalArgumentException("El poder no puede ser negativo");
        }
        this.power = power;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        if (specie == null || specie.isEmpty()) {
            throw new IllegalArgumentException("La especie no puede ser null o vacía");
        }
        this.specie = specie;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void attack(Pokemon otherPokemon) {
        float damage = this.power * this.type.calculateDamage(otherPokemon.getType());
        otherPokemon.restLife(damage);

        float selfDamage = this.energy * this.type.receiveDamage(otherPokemon.getType());
        this.restLife(selfDamage);

        if (otherPokemon.isCapturable()) {
            this.trainer.capturePokemon(otherPokemon);
        }
    }

    public boolean isCapturable() {
        return this.energy <= 0.0;
    }

    public void restLife(float amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("El daño no puede ser negativo");
        }
        this.energy = Math.max(0, this.energy - amount);
    }

    public void healing(float amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("La cantidad de curación no puede ser negativa");
        }
        this.energy = Math.min(maxEnergy, this.energy + amount);
    }


    public static class Water implements IType {
        @Override
        public float calculateDamage(IType opponentType) {
            return opponentType.damageByWater();
        }

        @Override
        public float receiveDamage(IType opponentType) {
            return opponentType.damageToWater();
        }

        public float damageByElectric() {
            return 1.0f;
        }

        public float damageByWater() {
            return 1.0f;
        }

        public float damageByFire() {
            return 1.25f;
        }

        public float damageByPlant() {
            return 1.0f;
        }

        public float damageByStone() {
            return 1.0f;
        }

        public float damageToElectric() {
            return 0.0f;
        }

        public float damageToWater() {
            return 0.0f;
        }

        public float damageToFire() {
            return 0.0f;
        }

        public float damageToPlant() {
            return 0.0f;
        }

        public float damageToStone() {
            return 0.0f;
        }
    }
}