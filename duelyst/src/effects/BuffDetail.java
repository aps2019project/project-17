package effects;

public class BuffDetail {
    private BuffType buffType;
    private int effectTime;
    private int changeAttackPowerValue;
    private int changeHealthValue;

    public BuffDetail(BuffType buffType, int effectTime) {
        this.buffType = buffType;
        this.effectTime = effectTime;
    }

    public BuffDetail(BuffType buffType, int effectTime, int changeAttackPowerValue, int changeHealthValue) {
        this.buffType = buffType;
        this.effectTime = effectTime;
        this.changeAttackPowerValue = changeAttackPowerValue;
        this.changeHealthValue = changeHealthValue;
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
}
