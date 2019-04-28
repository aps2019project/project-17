package effects;

import java.util.ArrayList;

public class Spell extends Card {
    private int manaPoint;
    private String desc;
    private Buff buff;
    private TargetRange targetRange;
    private TargetType targetType;
    private ArrayList<BuffType> buffTypes;
    private int effectTime;

    public Spell(String name, String id, int price, int manaPoint, String desc, TargetType targetType, TargetRange targetRange, ArrayList<BuffType> buffTypes, int effectTime) {
        super(name, id, price);
        this.desc = desc;
        this.manaPoint = manaPoint;
        this.targetRange = targetRange;
        this.targetType = targetType;
        this.buffTypes = new ArrayList<>();
        this.buffTypes.addAll(buffTypes);
        this.effectTime = effectTime;
        for (BuffType buffType : this.buffTypes) {
            BuffDetail buffDetail = new BuffDetail(buffType, effectTime);
            this.buff.addBuff(buffDetail);
        }
    }
}
