package effects;

import Cards.Minion;

public class Stun extends Effect {
    public Stun(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail) {
        super(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
    }

    @Override
    public void effect(Object... targets) {
        for (Object target : targets) {
            Minion minion = (Minion) target;
            minion.setStun(true);
        }
    }

    @Override
    public void remove() {

    }
}
