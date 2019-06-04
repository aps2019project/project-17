package Cards;

import Effects.enums.AttackType;
import Effects.enums.MinionType;

public class Hero extends Minion {
    private int coolDown;

    public Hero(String name, String id, int price, int manaPoint, int healthPoint, int attackPower, MinionType minionType, int attackRange, int distanceCanMove, int maxRangeToInput, AttackType attackType, int coolDown) {
        super(name, id, price, manaPoint, healthPoint, attackPower, minionType, attackRange, distanceCanMove, maxRangeToInput, attackType);
        this.coolDown = coolDown;
    }

    public void show() {
        System.out.println("Name : " + this.name + " - AP : " + this.attackPoint + " - HP : " + this.healthPoint + " - Class : " + super.getMinionType() + " - Special power : " + this.desc);
    }

    @Override
    public void resetMinion() {
        if (isStun) {
            setCanMove(false);
            setCanAttack(false);
            setCanCounterAttack(false);
        } else {
            setCanMove(true);
            setCanAttack(true);
            setCanCounterAttack(true);
        }
    }

    public int getCoolDown() {
        return coolDown;
    }
}
