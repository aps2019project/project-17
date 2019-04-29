package effects;

public class Hero extends Minion {

    private int attackPower;
    private int healthPoint;
    private Spell specialPower;

    public Hero(String name, String id, int price, int attackPower, int healthPoint, int manaPoint, int attackRange, int distanceCanMove, int maxRangeToInput, int attackPower1, int healthPoint1, Spell specialPower) {
        super(name, id, price, attackPower, healthPoint, manaPoint, attackRange, distanceCanMove, maxRangeToInput);
        this.attackPower = attackPower1;
        this.healthPoint = healthPoint1;
        this.specialPower = specialPower;
    }

    public void show() {
        System.out.println("Name : " + this.name + " - AP : " + this.attackPower + " - HP : " + this.healthPoint + " - Class : " + this.minionType + " - Special power : " + this.specialPower);
        //todo needs to have toString for minionType and specialPower and if they don't have nothing must be printed
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public Spell getSpecialPower() {
        return specialPower;
    }
}
