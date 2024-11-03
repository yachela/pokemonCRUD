package ar.edu.davinci;

public interface IType {
public void damageMultiplicator();
    static IType fromString(String typeStr) {
        switch (typeStr.toLowerCase()) {
            case "fire":
                return new Fire();
            case "water":
                return new Water();
            case "stone":
                return new Stone();
            default:
                throw new IllegalArgumentException("Unknown type: " + typeStr);
        }
    }
}


