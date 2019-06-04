package Effects.CellEffects;

import Effects.MinionEffects.ChangeProperties;
import GameGround.Cell;
import Effects.Effect;
import Effects.enums.TargetDetail;
import Effects.enums.TargetRange;
import Effects.enums.TargetType;

public class FireCell extends Effect {
    public FireCell(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail) {
        super(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
    }

    @Override
    public void effect(Object... targets) {
        for (Object target : targets) {
            Cell cell = (Cell) target;
            cell.setFireCell(true);
            Effect effect = new ChangeProperties(0, 0, false, TargetRange.ONE, TargetType.MINION, TargetDetail.NONE, -2, 0, false);
            cell.setEffect(effect);
        }
    }

    @Override
    public void remove() {
        for (Object impact : super.getImpacts()) {
            Cell cell = (Cell) impact;
            cell.setFireCell(false);
        }
    }
}
