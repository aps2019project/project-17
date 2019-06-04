package Effects.MinionEffects;

import Cards.Minion;
import Effects.Effect;
import Effects.enums.BuffType;
import Effects.enums.TargetDetail;
import Effects.enums.TargetRange;
import Effects.enums.TargetType;

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
        for (Object impact : super.getImpacts()) {
            Minion minion = (Minion) impact;
            minion.setAntiBuff(null);
        }
    }
}
