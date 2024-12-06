package ar.edu.davinci.Model;

import ar.edu.davinci.Interface.IType;

public abstract class Type implements IType {

    @Override
    public float calculateDamage(IType opponentType) {
        return opponentType.damageBy(getTypeName());
    }

    @Override
    public float receiveDamage(IType opponentType) {
        return opponentType.damageTo(getTypeName());
    }

    public float damageBy(String type) {
        return 1.0f;
    }

    public float damageTo(String type) {
        return 0.0f;
    }

    public abstract String getTypeName();
}