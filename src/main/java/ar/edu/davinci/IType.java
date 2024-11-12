package ar.edu.davinci;

    public interface IType {
        float calculateDamage(IType opponentType);
        float receiveDamage(IType opponentType);

        float damageByElectric();
        float damageByWater();
        float damageByFire();
        float damageByPlant();
        float damageByStone();

        float damageToElectric();
        float damageToWater();
        float damageToFire();
        float damageToPlant();
        float damageToStone();

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


