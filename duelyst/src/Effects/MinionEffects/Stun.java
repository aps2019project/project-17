package Effects.MinionEffects;

import Cards.Minion;
import Effects.Effect;
import Effects.enums.TargetDetail;
import Effects.enums.TargetRange;
import Effects.enums.TargetType;

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
        for (Object impact : super.getImpacts()) {
            Minion minion = (Minion) impact;
            minion.setStun(false);
        }
    }
}
