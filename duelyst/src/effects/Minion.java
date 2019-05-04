package effects;

import java.util.ArrayList;

public class Minion extends Card {
    protected Buff specialPower;
    protected Buff attack;
    protected Buff buff;
    protected int attackPower;
    protected int healthPoint;
    protected int manaPoint;
    protected int attackRange;
    protected int xCoordinate;
    protected int yCoordinate;
    protected int distanceCanMove;
    protected int maxRangeToInput;
    protected int holyBuffState;
    protected boolean canMove;
    protected boolean canCounterAttack;
    protected boolean isStun;
    protected boolean canAttack;
    protected MinionType minionType;
    protected boolean hasFlag;
    protected AttackType attackType;
    protected int numberOfAttack;
    protected BuffType antiBuff;

    public Minion(String name, String id, int price, int manaPoint, int healthPoint, int attackPower, MinionType minionType, int attackRange, int distanceCanMove, int maxRangeToInput, AttackType attackType) {
        super(name, id, price);
        this.attackPower = attackPower;
        this.healthPoint = healthPoint;
        this.manaPoint = manaPoint;
        this.attackRange = attackRange;
        this.distanceCanMove = distanceCanMove;
        this.maxRangeToInput = maxRangeToInput;
        this.hasFlag = false;
        this.minionType = minionType;
        this.attackType = attackType;
    }

    public void init() {
        specialPower = new Buff();
        this.xCoordinate = 0;
        this.yCoordinate = 0;
        this.holyBuffState = 0;
        this.canMove = true;
        this.canCounterAttack = true;
        this.distanceCanMove = 2;
        this.maxRangeToInput = 1;
        this.isStun = false;
        this.canAttack = true;
        this.numberOfAttack = 0;
        makeAttackBuff(attackPower);
    }

    private void makeAttackBuff(int attackPower) {
        this.attack = new Buff();
        BuffDetail buffDetail = new BuffDetail(-1, BuffType.CHANGE_ATTACK_POWER_OR_HEALTH_BUFF, TargetType.ENEMY, TargetRange.ONE, 0, 0, -attackPower);
        this.attack.addBuff(buffDetail);
    }

    protected String minionTypeShow() {
        switch (minionType) {
            case MELEE:
                return "MELEE";
            case RANGED:
                return "RANGED";
            case HYBRID:
                return "HYBRID";
            default:
                return "MELEE";
        }
    }

    public boolean isHasFlag() {
        return hasFlag;
    }

    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public BuffType getAntiBuff() {
        return antiBuff;
    }

    public void addSpecialPowerBuff(BuffDetail buffDetail) {
        this.specialPower.addBuff(buffDetail);
    }

    public boolean getCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        if (isStun)
            return;
        this.canAttack = canAttack;
    }

    public int getManaPoint() {
        return manaPoint;
    }

    public int getMaxRangeToInput() {
        return maxRangeToInput;
    }

    public void setMaxRangeToInput(int maxRangeToInput) {
        this.maxRangeToInput = maxRangeToInput;
    }

    public int getDistanceCanMove() {
        return distanceCanMove;
    }

    public void setDistanceCanMove(int distanceCanMove) {
        this.distanceCanMove = distanceCanMove;
    }

    public void attack(Minion minion) {
        numberOfAttack++;
    }

    public void move(int x, int y) {
    }

    public boolean isStun() {
        return isStun;
    }

    public void setStun(boolean stun) {
        isStun = stun;
        this.setCanMove(false);
        this.setCanAttack(false);
    }

    public void activeHolyBuff(int holyBuffState) {
        this.holyBuffState = holyBuffState;
    }

    public void setCanMove(boolean canMove) {
        if (isStun)
            return;
        this.canMove = canMove;
    }

    public void setCanCounterAttack(boolean canCounterAttack) {
        this.canCounterAttack = canCounterAttack;
    }

    public void changeHealth(int changingValue) {
        this.healthPoint += changingValue;
    }

    public void changeAttackPower(int changingValue) {
        this.attackPower += changingValue;
    }

    public void setCoordinate(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void show() {
        System.out.println("Name : " + this.name + " - Class : " + this.minionTypeShow() + " - AP : " + this.attackPower + " - HP : " + this.healthPoint + " - MP : " + this.manaPoint + " - Special Power : " + this.specialPower);
    }

    public boolean CanMove() {
        return canMove;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public boolean isCanCounterAttack() {
        return canCounterAttack;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public MinionType getMinionType() {
        return minionType;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public int getNumberOfAttack() {
        return numberOfAttack;
    }

    public int getHolyBuffState() {
        return holyBuffState;
    }

    public void setAntiBuff(BuffType antiBuff) {
        this.antiBuff = antiBuff;
    }

    public Buff getBuff() {
        return buff;
    }

    public void addBuff(BuffDetail buffDetail) {
        this.buff.addBuff(buffDetail);
    }
}
