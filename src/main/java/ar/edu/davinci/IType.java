package ar.edu.davinci;

public interface IType {
public float damageMultiplicator(IType aType);
public float takeDamage(IType aType);
    static IType fromString(String typeStr) {
        switch (typeStr.toLowerCase()) {
            case "fire":
                return new Fire();
            case "water":
                return new Water();
            case "stone":
                return new Stone();
            case "plant":
                return new Plant();
            case "electric":
                return new Electric();
            default:
                throw new IllegalArgumentException("Unknown type: " + typeStr);
        }
    }
}


