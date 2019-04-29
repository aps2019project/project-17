package effects;

public class Minion extends Card {
    private Enum type;
    private Spell specialPower;
    private Buff buff;
    private Enum classType;
    private int attackPower;
    private int healthPoint;
    private int manaPoint;
    private String desc;
    private int attackRange;
    private int xCoordinate;
    private int yCoordinate;
    private int distanceCanMove;
    private int maxRangeToInput;
    private boolean isHolyBuffActive;
    private boolean isStun;
    private boolean canMove;
    private boolean canAttack;
    private boolean canCounterAttack;


    public Minion(String name, String id, int price, Enum type, Buff buff, Enum classType, int attackPower, int healthPoint, int manaPoint, int attackRange) {
        super(name, id, price);
        this.type = type;
        this.buff = buff;
        this.classType = classType;
        this.attackPower = attackPower;
        this.healthPoint = healthPoint;
        this.manaPoint = manaPoint;
        this.attackRange = attackRange;
        this.xCoordinate = 0;
        this.yCoordinate = 0;
        this.isHolyBuffActive = true;
        this.canMove = true;
        this.canCounterAttack = true;
        this.distanceCanMove = 2;
        this.maxRangeToInput = 1;
        this.isStun = false;
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

    public void attack(String id) {
    }

    public void move(int x, int y) {
    }

    public boolean isStun() {
        return isStun;
    }

    public void setStun(boolean stun) {
        isStun = stun;
    }

    public Buff getBuff() {
        return buff;
    }

    public void activeHolyBuff() {
        isHolyBuffActive = true;
    }

    public void setCanMove(boolean canMove) {
        if (isStun)
            return;
        this.canMove = canMove;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        if (isStun)
            return;
        this.canAttack = canAttack;
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


}
