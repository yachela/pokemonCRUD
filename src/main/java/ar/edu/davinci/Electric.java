package ar.edu.davinci;

public class Electric implements IType {
    @Override
    public float calculateDamage(IType opponentType) {
        return opponentType.damageByElectric();
    }

    @Override
    public float receiveDamage(IType opponentType) {
        return opponentType.damageToElectric();
    }

    public float damageByElectric() { return 1.0f; }
    public float damageByWater() { return 1.5f; } // 50% más de daño a agua
    public float damageByFire() { return 1.0f; }
    public float damageByPlant() { return 1.0f; }
    public float damageByStone() { return 1.0f; }

    public float damageToElectric() { return 0.05f; } // Recibe 5% de daño de tipo agua
    public float damageToWater() { return 0.0f; }
    public float damageToFire() { return 0.0f; }
    public float damageToPlant() { return 0.0f; }
    public float damageToStone() { return 0.0f; }
}