package effects;

public class Hero extends Minion {

    private int attackPower;
    private int healthPoint;
    private Spell specialPower;
    private Enum classType;

    public Hero(String name, String id, int price, int attackPower, int healthPoint) {
        super(name, id, price);
        this.attackPower = attackPower;
        this.healthPoint = healthPoint;
    }
    public void showHero(){
        System.out.println("Name : "+this.name+" - AP : "+this.attackPower+" - HP : "+this.healthPoint+" - Class : "+this.classType+" - Special power : "+this.specialPower);
        //todo needs to have toString for classType and specialPower
    }
}
