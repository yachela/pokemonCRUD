package ar.edu.davinci.Model;

public class Electric extends Type {

    @Override
    public String getTypeName() {
        return "Electric";
    }

    @Override
    public float damageBy(String type) {
        switch (type) {
            case "Water":
                return 1.5f;
            default:
                return super.damageBy(type);
        }
    }

    @Override
    public float damageTo(String type) {
        if ("Electric".equals(type)) {
            return 0.05f;
        }
        return super.damageTo(type);
    }
}