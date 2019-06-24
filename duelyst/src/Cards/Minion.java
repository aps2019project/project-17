package Cards;

import Effects.Effect;
import Effects.MinionEffects.ChangeProperties;
import Effects.enums.*;
import GameGround.Battle;

import java.util.ArrayList;

public class Minion extends Card {
    protected ArrayList<Effect> specialPower = new ArrayList<>();
    protected Effect attack;
    protected ArrayList<Effect> effects = new ArrayList<>();
    protected SpecialSituation specialSituation = SpecialSituation.NONE;
    protected ArrayList<Effect> specialSituationBuff = new ArrayList<>();
    private AttackType attackType;
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
    private boolean canMove = true;
    private boolean canCounterAttack = true;
    boolean isStun = false;
    private boolean canAttack = true;
    private MinionType minionType;
    private boolean hasFlag;
    private int numberOfAttack;

    public Minion(String name, String id, int price, String desc, ArrayList<Effect> specialPower, Effect attack, ArrayList<Effect> effects, SpecialSituation specialSituation, ArrayList<Effect> specialSituationBuff, AttackType attackType, BuffType antiBuff, int attackPoint, int healthPoint, int manaPoint, int attackRange, int xCoordinate, int yCoordinate, int distanceCanMove, int maxRangeToInput, int holyBuffState, boolean canMove, boolean canCounterAttack, boolean isStun, boolean canAttack, MinionType minionType, boolean hasFlag, int numberOfAttack) {
        super(name, id, price, desc);
        this.specialPower = specialPower;
        this.attack = attack;
        this.effects = effects;
        this.specialSituation = specialSituation;
        this.specialSituationBuff = specialSituationBuff;
        this.attackType = attackType;
        this.antiBuff = antiBuff;
        this.attackPoint = attackPoint;
        this.healthPoint = healthPoint;
        this.manaPoint = manaPoint;
        this.attackRange = attackRange;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.distanceCanMove = distanceCanMove;
        this.maxRangeToInput = maxRangeToInput;
        this.holyBuffState = holyBuffState;
        this.canMove = canMove;
        this.canCounterAttack = canCounterAttack;
        this.isStun = isStun;
        this.canAttack = canAttack;
        this.minionType = minionType;
        this.hasFlag = hasFlag;
        this.numberOfAttack = numberOfAttack;
    }

    public Minion(String name, String id, int price, int manaPoint, int healthPoint, int attackPoint, MinionType minionType, int attackRange, int distanceCanMove, int maxRangeToInput, AttackType attackType, String desc) {
        super(name, id, price, desc);
        this.attackPoint = attackPoint;
        this.healthPoint = healthPoint;
        this.manaPoint = manaPoint;
        this.attackRange = attackRange;
        this.distanceCanMove = distanceCanMove;
        this.maxRangeToInput = maxRangeToInput;
        this.hasFlag = false;
        this.minionType = minionType;
        this.attackType = attackType;
        this.canAttack = true;
        this.canMove = true;
        this.canCounterAttack = true;
        this.isStun = false;
    }

    public void init() {
        this.canMove = true;
        this.canCounterAttack = true;
        this.distanceCanMove = 2;
        this.maxRangeToInput = 1;
        this.isStun = false;
        this.canAttack = true;
        this.numberOfAttack = 0;
        effects = new ArrayList<>();
        if (minionType.equals(MinionType.MELEE))
            attackRange = 1;
        makeAttackBuff();
    }

    private void makeAttackBuff() {
        Effect changeProperties = new ChangeProperties(0, 0, false, TargetRange.ONE, TargetType.MINION, TargetDetail.ENEMY, -this.attackPoint, 0, false);
        changeProperties.setOwnerUserName(getUserName());
        this.attack = changeProperties;
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
        this.attack.action(Battle.getCurrentBattle().getCellFromBoard(minion.xCoordinate, minion.yCoordinate));
        if (this.attackType.equals(AttackType.ON_ATTACK))
            useSpecialPower(minion.xCoordinate, minion.yCoordinate);
        if (specialSituationBuff != null && specialSituation.equals(SpecialSituation.ATTACK)) {
            useSpecialSituationBuff(minion.xCoordinate, minion.yCoordinate);
        }
    }

    public void addSpecialPower(Effect effect) {
        if (specialPower == null)
            specialPower = new ArrayList<>();
        specialPower.add(effect);
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void addSpecialSituationBuff(Effect effect) {
        if (specialSituationBuff == null)
            specialSituationBuff = new ArrayList<>();
        specialSituationBuff.add(effect);
    }

    public ArrayList<Effect> getSpecialSituationBuff() {
        return specialSituationBuff;
    }

    private void useSpecialSituationBuff(int x, int y) {
        for (Effect effect : specialSituationBuff) {
            effect.action(Battle.getCurrentBattle().getCellFromBoard(x, y));
        }
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
        if (changingValue < 0) {
            healthPoint += holyBuffState;
        }
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

    public boolean isCanMove() {
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

    public int getMaxRangeToInput() {
        return maxRangeToInput;
    }

    public void setAntiBuff(BuffType antiBuff) {
        this.antiBuff = antiBuff;
    }

    public void setSpecialSituation(SpecialSituation specialSituation) {
        this.specialSituation = specialSituation;
    }

    public void passTurn() {
        if (attackType.equals(AttackType.PASSIVE))
            useSpecialSituationBuff(xCoordinate, yCoordinate);
        for (int j = 0; j < effects.size(); j++) {
            Effect effect = effects.get(j);
            effect.action(Battle.getCurrentBattle().getCellFromBoard(xCoordinate, yCoordinate));
            effect.checkForRemove();
        }
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            if (effect.isDisable())
                effects.remove(effect);
        }
    }

    public ArrayList<Effect> getSpecialPower() {
        if (specialPower == null)
            specialPower = new ArrayList<>();
        return specialPower;
    }

    public boolean isCanCounterAttack() {
        return canCounterAttack;
    }

    public static Minion minionCopy(Minion minion) {
        return new Minion(minion.name, minion.id, minion.price, minion.desc, minion.specialPower, minion.attack, minion.effects, minion.specialSituation, minion.specialSituationBuff, minion.attackType, minion.antiBuff, minion.attackPoint, minion.healthPoint, minion.manaPoint, minion.attackRange, minion.xCoordinate, minion.yCoordinate, minion.distanceCanMove, minion.maxRangeToInput, minion.holyBuffState, minion.canMove, minion.canCounterAttack, minion.isStun, minion.canAttack, minion.minionType, minion.hasFlag, minion.numberOfAttack);
    }
}
