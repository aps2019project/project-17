package effects;

import Cards.Minion;

public class Holy extends Effect {
    private int holyBuffState;

    public Holy(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail, int holyBuffState) {
        super(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
        this.holyBuffState = holyBuffState;
    }

    @Override
    public void effect(Object... targets) {
        for (Object target : targets) {
            Minion minion = (Minion) target;
            if (!minion.getAntiBuff().equals(BuffType.HOLY))
                minion.activeHolyBuff(holyBuffState);
        }
    }

    @Override
    public void remove() {

    }
}
