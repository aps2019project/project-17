package Cards;

import GameGround.Battle;
import Effects.*;
import Effects.enums.*;

import java.util.ArrayList;

public class Minion extends Card {
    private Buff specialPower;
    protected Buff attack;
    protected Buff buff;
    private BuffDetail specialSituationBuff;
    private SpecialSituation specialSituation;
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
    private BuffType antiBuff;

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
        specialPower = new Buff();
        buff = new Buff();
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
        makeAttackBuff(attackPoint);
    }

    private void makeAttackBuff(int attackPower) {
        this.attack = new Buff();
        BuffDetail buffDetail = new BuffDetail(-1, BuffType.CHANGE_ATTACK_POWER_OR_HEALTH_BUFF, TargetType.NONE, TargetRange.ONE, -2, 0, -attackPower);
        this.attack.addBuff(buffDetail);
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

    BuffType getAntiBuff() {
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

    public int getDistanceCanMove() {
        return distanceCanMove;
    }

    public void attack(Minion minion) {
        if (!canAttack)
            return;
        ArrayList<BuffDetail> specialSituationBuffs = new ArrayList<>();
        specialSituationBuffs.add(specialSituationBuff);
        increaseNumberOfAttack();
        if (specialSituationBuff != null && specialSituation.equals(SpecialSituation.ATTACK))
            this.buff.action(minion.getXCoordinate(), minion.yCoordinate, specialSituationBuffs);
        this.attack.action(minion.xCoordinate, minion.yCoordinate, attack.getBuffDetails());
        if (this.attackType.equals(AttackType.ON_ATTACK))
            useSpecialPower(minion.xCoordinate, minion.yCoordinate);
    }

    public void counterAttack(Minion minion) {
        if (!canCounterAttack)
            return;
        this.attack.action(minion.xCoordinate, minion.yCoordinate, attack.getBuffDetails());
        if (this.attackType.equals(AttackType.ON_DEFEND))
            useSpecialPower(minion.xCoordinate, minion.yCoordinate);
    }

    public void useSpecialPower(int x, int y) {
        if (desc.equals("nothing"))
            return;
        for (BuffDetail buffDetail : specialPower.getBuffDetails()) {
            ArrayList<BuffDetail> buffDetails = new ArrayList<>();
            buffDetails.add(buffDetail);
            if (buffDetail.getTargetType() == null)
                continue;
            if ((buffDetail.getTargetType().equals(TargetType.SELF_NOT_MELEE) && !this.minionType.equals(MinionType.MELEE)) || buffDetail.getTargetRange().equals(TargetRange.SELF)) {
                specialPower.action(this.xCoordinate, this.yCoordinate, buffDetails);
                return;
            }
            if (buffDetail.getChangeHealth() == -105)
                buffDetail.setChangeHealth(((Minion) Battle.getCurrentBattle().getCellFromBoard(x, y).getCard()).getNumberOfAttack());
        }
        specialPower.action(x, y, specialPower.getBuffDetails());
    }

    private void increaseNumberOfAttack() {
        this.numberOfAttack++;
    }

    void setStun(boolean stun) {
        isStun = stun;
        this.canMove = false;
        this.canAttack = false;
    }

    void activeHolyBuff(int holyBuffState) {
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

    void changeHealth(int changingValue) {
        this.healthPoint += changingValue;
    }

    void changeAttackPower(int changingValue) {
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

    int getHolyBuffState() {
        return holyBuffState;
    }

    void setHolyBuffState(int holyBuffState) {
        this.holyBuffState = holyBuffState;
    }

    public void setAntiBuff(BuffType antiBuff) {
        this.antiBuff = antiBuff;
    }

    public Buff getBuff() {
        return buff;
    }

    void setSpecialSituationBuff(BuffDetail specialSituationBuff) {
        this.specialSituationBuff = specialSituationBuff;
    }

    void setSpecialSituation(SpecialSituation specialSituation) {
        this.specialSituation = specialSituation;
    }

    public void addBuff(BuffDetail buffDetail) {
        this.buff.addBuff(buffDetail);
    }

    public Buff getAttack() {
        return attack;
    }

    public void passTurn() {
        if (this.buff == null || this.buff.getBuffDetails() == null)
            return;
        buff.action(this.xCoordinate, this.yCoordinate, buff.getBuffDetails());
    }


}
