package effects;

public abstract class Buff {
    private String desc;
    private int effectTime;
    private BuffData buffData;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setEffectTime(int effectTime) {
        this.effectTime = effectTime;
    }

    public class HolyBuff extends Buff {
        public HolyBuff() {
            super.buffData.setHaveHolyBuff(true);
        }
    }

    public class PowerBuff extends Buff {
        public PowerBuff(int value, BuffData.HealthOrAttack healthOrAttack) {
            super.buffData.getPowerBuff().setValue(value);
            super.buffData.getPowerBuff().setHealthOrAttack(healthOrAttack);
        }
    }

    public class PoisonBuff extends Buff {
        public PoisonBuff() {
            super.buffData.setHavePoisonBuff(true);
        }
    }

    public class WeaknessBuff extends Buff {
        public WeaknessBuff() {
            super.buffData.setHaveWeaknessBuff(true);
        }
    }

    public class StunBuff extends Buff {
        public StunBuff() {
            super.buffData.setHaveStunBuff(true);
        }
    }

    public class Disarm extends Buff {
        public Disarm() {
            super.buffData.setHaveDisarmBuff(true);
        }
    }
}