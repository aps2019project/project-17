package Effects;

import Cards.Minion;
import Data.Player;
import Effects.enums.SpecialSituation;
import Effects.enums.TargetDetail;
import Effects.enums.TargetRange;
import Effects.enums.TargetType;

public class SpecialSituationBuff extends Effect {
    private SpecialSituation specialSituation;

    public SpecialSituationBuff(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail, SpecialSituation specialSituation) {
        super(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
        this.specialSituation = specialSituation;
    }

    @Override
    public void effect(Object... targets) {
        for (Object target : targets) {
            if (target instanceof Player) {
                Player player = (Player) target;
                player.setSpecialSituation(specialSituation);
            }
            if (target instanceof Minion) {
                Minion minion = (Minion) target;
                minion.setSpecialSituation(specialSituation);
            }
        }
    }

    @Override
    public void remove() {
        for (Object target : super.getImpacts()) {
            if (target instanceof Player) {
                Player player = (Player) target;
                player.setSpecialSituation(SpecialSituation.NONE);
            }
            if (target instanceof Minion) {
                Minion minion = (Minion) target;
                minion.setSpecialSituation(SpecialSituation.NONE);
                minion.getSpecialSituationBuff().clear();
            }
        }
    }

    public SpecialSituation getSpecialSituation() {
        return specialSituation;
    }
}
