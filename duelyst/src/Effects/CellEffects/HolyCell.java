package Effects.CellEffects;

import Effects.MinionEffects.Holy;
import GameGround.Cell;
import Effects.Effect;
import Effects.enums.TargetDetail;
import Effects.enums.TargetRange;
import Effects.enums.TargetType;

public class HolyCell extends Effect {
    public HolyCell(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail) {
        super(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
    }

    @Override
    public void effect(Object[] targets) {
        for (Object target : targets) {
            Cell cell = (Cell) target;
            cell.setHoly(true);
            Effect effect = new Holy(0, 100, false, TargetRange.ONE, TargetType.MINION, TargetDetail.NONE, 1);
            cell.setEffect(effect);
        }
    }

    @Override
    public void remove() {
        for (Object impact : super.getImpacts()) {
            Cell cell = (Cell) impact;
            cell.setHoly(false);
        }
    }
}
