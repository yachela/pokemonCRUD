package ar.edu.davinci.Interface;

import ar.edu.davinci.Model.*;

public interface IType {

    float calculateDamage(IType opponentType);

    float receiveDamage(IType opponentType);

    float damageBy(String type);

    float damageTo(String type);
    static IType fromString(String typeStr) {
        switch (typeStr.toLowerCase()) {
            case "fire":
                return new Fire();
            case "water":
                return new Water();
            case "plant":
                return new Plant();
            case "electric":
                return new Electric();
            default:
                throw new IllegalArgumentException("Unknown type: " + typeStr);
        }
    }
}


