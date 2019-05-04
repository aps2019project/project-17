package effects;

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
    private BuffType addBuff;
    private int manaIncreaseValue;

    public BuffDetail(int id, BuffType buffType, int effectTime, TargetType targetType, TargetRange targetRange) {
        this.id = Integer.toString(id);
        this.buffType = buffType;
        this.effectTime = effectTime;
        this.targetType = targetType;
        this.targetRange = targetRange;
        this.isContinuous = effectTime == 100;

    }

    //for anti buffs
    public BuffDetail(int id, BuffType buffType, int effectTime, TargetType targetType, TargetRange targetRange, BuffType antiBuffType) {
        this.id = Integer.toString(id);
        this.buffType = buffType;
        this.effectTime = effectTime;
        this.targetType = targetType;
        this.targetRange = targetRange;
        this.antiBuffType = antiBuffType;
        this.isContinuous = effectTime == 100;
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
        this.isContinuous = effectTime == 100;
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
        this.isContinuous = effectTime == 100;
    }

    //for add  buff
    public BuffDetail(int id, BuffType buffType, TargetType targetType, TargetRange targetRange, int effectTime, BuffType addBuff) {
        this.id = Integer.toString(id);
        this.buffType = buffType;
        this.effectTime = effectTime;
        this.addBuff = addBuff;
        this.targetType = targetType;
        this.targetRange = targetRange;
        this.isContinuous = effectTime == 100;
    }

    public int getEffectTime() {
        return effectTime;
    }

    public boolean increaseEffectTime() {
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
}
