package ar.edu.davinci;

public class Plant implements IType {
    @Override
    public float damageMultiplicator(IType otherType) {
        if (otherType.getClass().getSimpleName().equals("Stone")) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public float takeDamage(IType aType) {
        return 0;
    }

}
