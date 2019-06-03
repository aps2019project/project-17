package effects;

import Cards.Minion;
import effects.enums.BuffType;
import effects.enums.TargetDetail;
import effects.enums.TargetRange;
import effects.enums.TargetType;

public class Disarm extends Effect {

    public Disarm(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail) {
        super(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
    }

    @Override
    public void effect(Object... targets) {
        for (Object target : targets) {
            Minion minion = (Minion) target;
            if (!minion.getAntiBuff().equals(BuffType.DISARM))
                minion.setCanCounterAttack(false);
        }
    }

    @Override
    public void remove() {

    }
}
