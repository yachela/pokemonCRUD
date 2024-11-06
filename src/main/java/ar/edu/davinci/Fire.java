package ar.edu.davinci;

import Model.Pokemon;

public class Fire implements IType{
    @Override
    public float damageMultiplicator(IType otherType) {
        if (otherType.getClass().getSimpleName().equals("Plant")) {
            return 0.2f;
        } else {
            return 1;
        }
    }

    @Override
    public float takeDamage(IType aType) {
        return 0;
    }
}
