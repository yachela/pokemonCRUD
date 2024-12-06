package ar.edu.davinci.Model;

public class Stone extends Type {

    @Override
    public String getTypeName() {
        return "Stone";
    }

    @Override
    public float damageBy(String type) {

        return super.damageBy(type);
    }

    @Override
    public float damageTo(String type) {
        switch (type) {
            case "Plant":
                return 0.0f;
            default:
                return super.damageTo(type);
        }
    }
}