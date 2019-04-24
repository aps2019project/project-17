package effects;

public class BuffDetail {
    private BuffType buffType;
    private int effectTime;

    public BuffDetail(BuffType buffType, int effectTime) {
        this.buffType = buffType;
        this.effectTime = effectTime;
    }

    public int getEffectTime() {
        return effectTime;
    }

    public BuffType getBuffType() {
        return buffType;
    }
}
