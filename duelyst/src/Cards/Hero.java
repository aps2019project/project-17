package Cards;

import Effects.enums.AttackType;
import Effects.enums.MinionType;

public class Hero extends Minion {
    private int coolDown;

    public Hero(String name, String id, int price, int manaPoint, int healthPoint, int attackPower, MinionType minionType, int attackRange, int distanceCanMove, int maxRangeToInput, AttackType attackType, int coolDown,String desc) {
        super(name, id, price, manaPoint, healthPoint, attackPower, minionType, attackRange, distanceCanMove, maxRangeToInput, attackType,desc);
        this.coolDown = coolDown;
    }

    public void show() {
        System.out.println("Name : " + this.name + " - AP : " + this.attackPoint + " - HP : " + this.healthPoint + " - Class : " + super.getMinionType() + " - Special power : " + this.desc);
    }

    @Override
    public void resetMinion(int turn) {
        super.resetMinion(turn);
    }

    public int getCoolDown() {
        return coolDown;
    }

    public static Hero heroCopy(Hero hero, int coolDown) {
        Hero hero1 = new Hero(hero.name, hero.id, hero.price, hero.getManaPoint(), hero.healthPoint ,hero.attackPoint, hero.getMinionType(), hero.getAttackRange(), hero.getDistanceCanMove(), hero.getMaxRangeToInput(), hero.getAttackType(), hero.coolDown, hero.desc);
        hero1.coolDown = coolDown;
        return hero1;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Hero))
            return false;
        return ((Hero) obj).getName().equals(this.name);
    }
}
