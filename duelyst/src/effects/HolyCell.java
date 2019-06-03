package effects;

import GameGround.Cell;
import effects.enums.TargetDetail;
import effects.enums.TargetRange;
import effects.enums.TargetType;

public class HolyCell extends Effect {
    public HolyCell(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail) {
        super(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
    }

    @Override
    public void effect(Object... targets) {
        for (Object target : targets) {
            Cell cell = (Cell) target;
            cell.setHoly(true);
        }
    }

    @Override
    public void remove() {

    }
}
