package effects;

public class BuffDetail {
    private int id;
    private BuffType buffType;
    private int effectTime;
    private int changeAttackPowerValue;
    private int changeHealthValue;
    private TargetType targetType;
    private TargetRange targetRange;
    private BuffType antiBuffType;
    private boolean isContinuous;

    public BuffDetail(int id, BuffType buffType, int effectTime, TargetType targetType, TargetRange targetRange) {
        this.id = id;
        this.buffType = buffType;
        this.effectTime = effectTime;
        this.targetType = targetType;
        this.targetRange = targetRange;
        this.isContinuous = effectTime == 100;

    }

    public BuffDetail(int id, BuffType buffType, int effectTime, TargetType targetType, TargetRange targetRange,BuffType antiBuffType) {
        this.id = id;
        this.buffType = buffType;
        this.effectTime = effectTime;
        this.targetType = targetType;
        this.targetRange = targetRange;
        this.antiBuffType = antiBuffType;
        this.isContinuous = effectTime == 100;
    }

    public BuffDetail(int id,BuffType buffType, TargetType targetType, TargetRange targetRange, int effectTime, int changeAttackPowerValue, int changeHealthValue) {
        this.id = id;
        this.buffType = buffType;
        this.effectTime = effectTime;
        this.changeAttackPowerValue = changeAttackPowerValue;
        this.changeHealthValue = changeHealthValue;
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

    public int getId() {
        return id;
    }
}
