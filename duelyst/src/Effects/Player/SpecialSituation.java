package Effects.Player;

import Data.Player;
import Effects.Effect;
import Effects.enums.TargetDetail;
import Effects.enums.TargetRange;
import Effects.enums.TargetType;

public class SpecialSituation extends Effect {
    private SpecialSituation specialSituation;

    public SpecialSituation(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail, SpecialSituation specialSituation) {
        super(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
        this.specialSituation = specialSituation;
    }

    @Override
    public void effect(Object... targets) {
        for (Object target : targets) {
            Player player = (Player) target;
            player.setSpecialSituation(specialSituation);
        }
    }

    @Override
    public void remove() {

    }
}
