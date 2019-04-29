package effects;

public class Hero extends Minion {
    private int coolDown;

    public Hero(String name, String id, int price, int manaPoint, int healthPoint, int attackPower, MinionType minionType, int attackRange, int distanceCanMove, int maxRangeToInput, AttackType attackType, int coolDown) {
        super(name, id, price, manaPoint, healthPoint, attackPower, minionType, attackRange, distanceCanMove, maxRangeToInput, attackType);
        this.coolDown = coolDown;
    }

    public void show() {
        System.out.println("Name : " + this.name + " - AP : " + this.attackPower + " - HP : " + this.healthPoint + " - Class : " + this.minionType + " - Special power : " + this.specialPower);
        //todo needs to have toString for minionType and specialPower and if they don't have nothing must be printed
    }
}
