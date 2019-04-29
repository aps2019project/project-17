package effects;
public class Spell extends Card {
    private int manaPoint;
    private String desc;
    private Buff buff;;

    public Spell(String name, String id, int price, int manaPoint, String desc) {
        super(name, id, price);
        this.desc = desc;
        this.manaPoint = manaPoint;
    }
    public void addBuff(BuffDetail buffDetail)
    {
        this.buff.addBuff(buffDetail);
    }

    public void show(){
        System.out.println("Name : "+this.name+" - MP : "+this.manaPoint+" - Desc : "+this.desc);
    }
}
