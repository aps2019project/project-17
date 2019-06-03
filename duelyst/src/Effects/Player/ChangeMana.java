package Effects.Player;

import Data.Player;
import Effects.Effect;
import Effects.enums.TargetDetail;
import Effects.enums.TargetRange;
import Effects.enums.TargetType;

public class ChangeMana extends Effect {
    private int manaChangeValue;

    public ChangeMana(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail, int manaChangeValue) {
        super(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
        this.manaChangeValue = manaChangeValue;
    }

    @Override
    public void effect(Object... targets) {
        for (Object target : targets) {
            Player player = (Player) target;
            player.changeMana(manaChangeValue);
        }
    }

    @Override
    public void remove() {

    }
}
