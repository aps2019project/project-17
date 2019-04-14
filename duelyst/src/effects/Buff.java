package effects;

public abstract class Buff {
    private String desc;
    private int effectTime;
    private BuffEnum buffData;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setEffectTime(int effectTime) {
        this.effectTime = effectTime;
    }


    public abstract void action();
    public class dectrementDamage extends Buff{
        @Override
        public void action() {
        }
    }
}