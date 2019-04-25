package effects;

public class Spell extends Card {
    private int manaPoint;
    private String desc;
    private Buff buff;
    private TargetRange target;
    private TargetType targetType;

    public Spell(String name, String id, int price) {
        super(name, id, price);
    }
}
