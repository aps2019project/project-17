package effects;

public class Anti extends Effect {
    private BuffType buffType;

    public Anti(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail, BuffType buffType) {
        super(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
        this.buffType = buffType;
    }

    @Override
    public void effect(Object... targets) {
        for (Object target : targets) {
            Minion minion = (Minion) target;
            minion.setAntiBuff(buffType);
        }
    }

    @Override
    public void remove() {

    }
}
