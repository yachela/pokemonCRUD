package ar.edu.davinci.Model;

public class Plant extends Type {

    @Override
    public String getTypeName() {
        return "Plant";
    }

    @Override
    public float damageBy(String type) {
        switch (type) {
            case "Stone":
                return 0.0f;
            default:
                return super.damageBy(type);
        }
    }

    @Override
    public float damageTo(String type) {

        return super.damageTo(type);
    }
}