package effects;

public class BuffData {
    private boolean haveHolyBuff;
    private boolean havePoisonBuff;
    private boolean haveWeaknessBuff;
    private boolean haveStunBuff;
    private boolean haveDisarmBuff;
    private PowerBuff powerBuff;
    enum HealthOrAttack{HEALTH,ATTACK}
    enum TypeOfEffect{DECREMENT,INCREMENT}
    public boolean isHaveHolyBuff() {
        return haveHolyBuff;
    }

    public void setHaveHolyBuff(boolean haveHolyBuff) {
        this.haveHolyBuff = haveHolyBuff;
    }

    public PowerBuff getPowerBuff() {
        return powerBuff;
    }

    public void setHaveDisarmBuff(boolean haveDisarmBuff) {
        this.haveDisarmBuff = haveDisarmBuff;
    }

    public void setHavePoisonBuff(boolean havePoisonBuff) {
        this.havePoisonBuff = havePoisonBuff;
    }

    public void setHaveWeaknessBuff(boolean haveWeaknessBuff) {
        this.haveWeaknessBuff = haveWeaknessBuff;
    }

    public void setHaveStunBuff(boolean haveStunBuff) {
        this.haveStunBuff = haveStunBuff;
    }

    public class PowerBuff{
        private int value;
        private HealthOrAttack healthOrAttack;
        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public HealthOrAttack getHealthOrAttack() {
            return healthOrAttack;
        }

        public void setHealthOrAttack(HealthOrAttack healthOrAttack) {
            this.healthOrAttack = healthOrAttack;
        }
    }
}
