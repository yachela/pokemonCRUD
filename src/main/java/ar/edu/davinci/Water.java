package ar.edu.davinci;

public class Water implements IType{
    @Override
    public float damageMultiplicator(IType otherType) {
        if (otherType.getClass().getSimpleName().equals("Fire")) {
            return 1.25F;
        } else {
            return 1;
        }
    }

    @Override
    public float takeDamage(IType aType) {
        return 0;
    }

}
