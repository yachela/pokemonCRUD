package ar.edu.davinci.Model;

public class Fire extends Type {

    @Override
    public String getTypeName() {
        return "Fire";
    }

    @Override
    public float damageBy(String type) {
        switch (type) {
            case "Plant":
                return 1.2f;
            default:
                return super.damageBy(type);
        }
    }

    @Override
    public float damageTo(String type) {
        return super.damageTo(type);
    }
}