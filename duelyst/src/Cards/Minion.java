package Cards;

import Effects.MinionEffects.ChangeProperties;
import GameGround.Battle;
import Effects.*;
import Effects.enums.*;

import java.util.ArrayList;

public class Minion extends Card {
    protected ArrayList<Effect> specialPower = new ArrayList<>();
    protected Effect attack;
    protected ArrayList<Effect> effects = new ArrayList<>();
    protected ArrayList<Effect> specialSituationBuff = new ArrayList<>();
    protected SpecialSituation specialSituation;
    private BuffType antiBuff;
    int attackPoint;
    int healthPoint;
    private int manaPoint;
    private int attackRange;
    private int xCoordinate;
    private int yCoordinate;
    private int distanceCanMove;
    private int maxRangeToInput;
    private int holyBuffState;
    private boolean canMove;
    private boolean canCounterAttack;
    boolean isStun;
    private boolean canAttack;
    private MinionType minionType;
    private boolean hasFlag;
    private AttackType attackType;
    private int numberOfAttack;

    public Minion(String name, String id, int price, int manaPoint, int healthPoint, int attackPoint, MinionType minionType, int attackRange, int distanceCanMove, int maxRangeToInput, AttackType attackType) {
        super(name, id, price);
        this.attackPoint = attackPoint;
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
        if (minionType.equals(MinionType.MELEE))
            attackRange = 1;
        makeAttackBuff();
    }

    private void makeAttackBuff() {
        this.attack = new ChangeProperties(0, 0, false, TargetRange.ONE, TargetType.MINION, TargetDetail.ENEMY, -this.attackPoint, 0, false);
    }

    private String minionTypeShow() {
        switch (minionType) {
            case MELEE:
                return "MELEE";
            case RANGED:
                return "RANGED";
            case HYBRID:
                return "HYBRID";
            default:
                return null;
        }
    }

    public void resetMinion() {
        if (isStun) {
            canMove = false;
            canAttack = false;
            canCounterAttack = false;
        } else {
            canMove = true;
            canAttack = true;
            canCounterAttack = true;
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

    public int getDistanceCanMove() {
        return distanceCanMove;
    }

    public void attack(Minion minion) {
        if (!canAttack)
            return;
        increaseNumberOfAttack();

        if (specialSituation.equals(SpecialSituation.ATTACK)) {
            for (Effect effect : this.specialPower) {
                effect.action(Battle.getCurrentBattle().getCellFromBoard(this.xCoordinate, this.yCoordinate));
            }
        }
        this.attack.action(Battle.getCurrentBattle().getCellFromBoard(this.xCoordinate, this.yCoordinate));
        if (this.attackType.equals(AttackType.ON_ATTACK))
            useSpecialPower(minion.xCoordinate, minion.yCoordinate);
    }

    public void counterAttack(Minion minion) {
        if (!canCounterAttack)
            return;
        this.attack.action(Battle.getCurrentBattle().getCellFromBoard(minion.xCoordinate, minion.yCoordinate));
        if (this.attackType.equals(AttackType.ON_DEFEND))
            useSpecialPower(minion.xCoordinate, minion.yCoordinate);
    }

    public void useSpecialPower(int x, int y) {
        if (desc.equals("nothing"))
            return;
        for (Effect effect : specialPower) {
            effect.action(Battle.getCurrentBattle().getCellFromBoard(x, y));
        }
    }

    private void increaseNumberOfAttack() {
        this.numberOfAttack++;
    }

    public void setStun(boolean stun) {
        isStun = stun;
        this.canMove = false;
        this.canAttack = false;
    }

    public void activeHolyBuff(int holyBuffState) {
        this.holyBuffState = holyBuffState;
    }

    public void deActiveHolyBuff() {
        this.holyBuffState = 0;
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
        this.attackPoint += changingValue;
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
        System.out.println("Name : " + this.name + " - Class : " + this.minionTypeShow().toLowerCase() + " - AP : " + this.attackPoint + " - HP : " + this.healthPoint + " - MP : " + this.manaPoint + " - Special Power : " + this.desc);
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public boolean CanMove() {
        return canMove;
    }

    public MinionType getMinionType() {
        return minionType;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    private int getNumberOfAttack() {
        return numberOfAttack;
    }

    public void setAntiBuff(BuffType antiBuff) {
        this.antiBuff = antiBuff;
    }


    void setSpecialSituation(SpecialSituation specialSituation) {
        this.specialSituation = specialSituation;
    }


}
