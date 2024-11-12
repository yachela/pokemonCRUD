package ar.edu.davinci;

public class Water implements IType {
    @Override
    public float calculateDamage(IType opponentType) {
        return opponentType.damageByWater();
    }

    @Override
    public float receiveDamage(IType opponentType) {
        return opponentType.damageToWater();
    }

    public float damageByElectric() { return 1.0f; }
    public float damageByWater() { return 1.0f; }
    public float damageByFire() { return 1.25f; }
    public float damageByPlant() { return 1.0f; }
    public float damageByStone() { return 1.0f; }

    public float damageToElectric() { return 0.0f; }
    public float damageToWater() { return 0.0f; }
    public float damageToFire() { return 0.0f; }
    public float damageToPlant() { return 0.0f; }
    public float damageToStone() { return 0.0f; }
}