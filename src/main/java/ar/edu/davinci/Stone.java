package ar.edu.davinci;

public class Stone implements IType{
    @Override
    public float damageMultiplicator(IType otherType) {
            return 1;
    }

    @Override
    public float takeDamage(IType aType) {
        return 0;
    }

}
