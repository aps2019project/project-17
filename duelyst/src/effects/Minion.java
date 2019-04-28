package effects;

public class Minion extends Card{
    protected Spell specialPower;
    protected Buff buff;
    protected int attackPower;
    protected int healthPoint;
    protected int manaPoint;
    protected String desc;
    protected int attackRange;
    protected int xCoordinate;
    protected int yCoordinate;
    protected int distanceCanMove;
    protected int maxRangeToInput;
    protected boolean isHolyBuffActive;
    protected boolean canMove;
    protected boolean canCounterAttack;
    protected boolean canAttack;
    protected MinionType minionType;


    public Minion(String name, String id, int price, int attackPower, int healthPoint, int manaPoint, int attackRange, int distanceCanMove, int maxRangeToInput) {
        super(name, id, price);
        this.attackPower = attackPower;
        this.healthPoint = healthPoint;
        this.manaPoint = manaPoint;
        this.attackRange = attackRange;
        this.xCoordinate = 0;
        this.yCoordinate = 0;
        this.isHolyBuffActive = true;
        this.canMove = true;
        this.canCounterAttack = true;
        this.canAttack = true;
        this.distanceCanMove = distanceCanMove;
        this.maxRangeToInput = maxRangeToInput;
        setMinionType();
    }

    private void setMinionType() {
        if (this.attackRange == 2)
            this.minionType = MinionType.MELLEE;
        else if (this.attackRange == 100)
            this.minionType = MinionType.HYBRID;
        else
            this.minionType = MinionType.RANGED;
    }

    public boolean getCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
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

    public void attack(String id) {
    }

    public void move(int x, int y) {
    }

    public Buff getBuff() {
        return buff;
    }

    public void activeHolyBuff() {
        isHolyBuffActive = true;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public void setCanCounterAttack(boolean canCounterAttack) {
        this.canCounterAttack = canCounterAttack;
    }

    public void changeHealth(int changingValue)
    {
        this.healthPoint += changingValue;
    }

    public void changeAttackPower(int changingValue)
    {
        this.attackPower += changingValue;
    }

    public void setCoordinate(int x, int y){
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
