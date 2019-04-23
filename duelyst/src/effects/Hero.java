package effects;

public class Hero extends Cards {

    private int attackPower;
    private int healthPoint;
    private Spell specialPower;
    private Enum classType;

    public Hero(String name, String id, int price, int attackPower, int healthPoint) {
        super(name, id, price);
        this.attackPower = attackPower;
        this.healthPoint = healthPoint;
    }
}
