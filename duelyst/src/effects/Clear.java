package effects;

import Cards.Minion;
import effects.enums.TargetDetail;
import effects.enums.TargetRange;
import effects.enums.TargetType;

public class Clear extends Effect {

    public Clear(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail) {
        super(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
    }

    @Override
    public void effect(Object... targets) {
        for (Object target : targets) {
            Minion minion = (Minion) target;
        }
    }

    @Override
    public void remove() {

    }
}
