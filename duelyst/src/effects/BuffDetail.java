package effects;

import java.util.ArrayList;

public class BuffDetail {
    private String id;
    private BuffType buffType;
    private int effectTime;
    private int changeAttackPowerValue;
    private int changeHealthValue;
    private TargetType targetType;
    private TargetRange targetRange;
    private BuffType antiBuffType;
    private boolean isContinuous;
    private int holyBuffState;
    private int manaIncreaseValue;
    private SpecialSituation situation;
    private ArrayList<Object> target;

    public void init() {
        this.isContinuous = effectTime == 100;
        if (buffType.equals(BuffType.POISON))
            changeHealthValue = -1;
    }

    public BuffDetail(int id, BuffType buffType, int effectTime, TargetType targetType, TargetRange targetRange) {
        this.id = Integer.toString(id);
        this.buffType = buffType;
        this.effectTime = effectTime;
        this.targetType = targetType;
        this.targetRange = targetRange;

    }

    //for anti buffs
    public BuffDetail(int id, BuffType buffType, int effectTime, TargetType targetType, TargetRange targetRange, BuffType antiBuffType) {
        this.id = Integer.toString(id);
        this.buffType = buffType;
        this.effectTime = effectTime;
        this.targetType = targetType;
        this.targetRange = targetRange;
        this.antiBuffType = antiBuffType;
    }

    //for change attack power or health buff
    public BuffDetail(int id, BuffType buffType, TargetType targetType, TargetRange targetRange, int effectTime, int changeAttackPowerValue, int changeHealthValue) {
        this.id = Integer.toString(id);
        this.buffType = buffType;
        this.effectTime = effectTime;
        this.changeAttackPowerValue = changeAttackPowerValue;
        this.changeHealthValue = changeHealthValue;
        this.targetType = targetType;
        this.targetRange = targetRange;
    }

    //for holy buff and change mana
    public BuffDetail(int id, BuffType buffType, TargetType targetType, TargetRange targetRange, int effectTime, int buffState) {
        this.id = Integer.toString(id);
        this.buffType = buffType;
        this.effectTime = effectTime;
        if (buffType.equals(BuffType.HOLY))
            this.holyBuffState = buffState;
        else if (buffType.equals(BuffType.CHANGE_MANA))
            this.manaIncreaseValue = buffState;
        this.targetType = targetType;
        this.targetRange = targetRange;
    }

    public int getEffectTime() {
        return effectTime;
    }

    public boolean increaseEffectTime() {
        if (effectTime == 100)
            return false;
        if (effectTime >= 10) {
            effectTime -= 10;
            return false;
        }
        effectTime--;
        return effectTime == 0;
    }

    public int getChangeAttackPowerValue() {
        return changeAttackPowerValue;
    }

    public int getChangeHealthValue() {
        return changeHealthValue;
    }

    public BuffType getBuffType() {
        return buffType;
    }

    public BuffType getAntiBuffType() {
        return antiBuffType;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Object> getTarget() {
        return target;
    }

    public void addTarget(Object object) {
        if (target == null)
            target = new ArrayList<>();
        this.target.add(object);
    }

    public TargetRange getTargetRange() {
        return targetRange;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public int getHolyBuffState() {
        return holyBuffState;
    }

    public SpecialSituation getSituation() {
        return situation;
    }
}
