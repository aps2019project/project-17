package effects;

public class Spell extends Card {
    private int manaPoint;
    private Buff buff;

    public Spell(String name, String id, int price, int manaPoint) {
        super(name, id, price);
        this.manaPoint = manaPoint;
    }

    public void addBuff(BuffDetail buffDetail) {
        this.buff.addBuff(buffDetail);
    }

    public void show() {
        System.out.println("Name : " + this.name + " - MP : " + this.manaPoint + " - Desc : " + this.desc);
    }

    public int getManaPoint() {
        return manaPoint;
    }

    public void setManaPoint(int manaPoint) {
        this.manaPoint = manaPoint;
    }

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }
}
