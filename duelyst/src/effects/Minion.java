package effects;

public class Minion {
    private Enum type;
    private Spell specialPower;
    private Enum classType;
    private int attackPower;
    private int healthPoint;
    private int manaPoint;
    private String desc;
    private int attackRange;
    private boolean isHolyBuffActive;
    private boolean canMove;
    private boolean canCounterAttack;

    public void attack(String id) {
    }

    public void move(int x, int y) {
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
}
