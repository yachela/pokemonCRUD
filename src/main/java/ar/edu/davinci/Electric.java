package ar.edu.davinci;

import Model.Pokemon;

public class Electric implements IType {
    @Override
    public float damageMultiplicator(IType otherType) {
        if (otherType.getClass().getSimpleName().equals("Water")) {
            return 1.5f;
        } else {
            return 1;
        }
    }
    @Override
    public float takeDamage(IType otherType) {
        if (otherType.getClass().getSimpleName().equals("Water")) {
            return 0.05f;
        } else {
            return 0;
        }
    }
}
