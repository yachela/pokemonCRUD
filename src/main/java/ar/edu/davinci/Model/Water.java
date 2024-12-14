package ar.edu.davinci.Model;

public class Water extends Type {

    @Override
    public String getTypeName() {
        return "Water";
    }

    @Override
    public float damageBy(String type) {
        switch (type) {
            case "Fire":
                return 1.25f;
            default:
                return super.damageBy(type);
        }
    }

    @Override
    public float damageTo(String type) {
        switch (type) {
            case "Electric":
                return 0.5f;
            case "Plant":
                return 1.5f;
            default:
                return super.damageTo(type);
        }
    }
}