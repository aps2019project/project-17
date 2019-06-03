package Effects.MinionEffects;

import Cards.Minion;
import Effects.Effect;
import Effects.enums.TargetRange;
import Effects.enums.TargetType;

public class ChangeProperties extends Effect {
    private int changeHealthValue;
    private int changePowerValue;

    public ChangeProperties(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, int changeHealthValue, int changePowerValue) {
        super(startTime, endTime, isContinues, targetRange, targetType);
        this.changeHealthValue = changeHealthValue;
        this.changePowerValue = changePowerValue;
    }

    @Override
    public void effect(Object... targets) {
        for (Object target : targets) {
            Minion minion = (Minion) target;
            minion.changeAttackPower(changePowerValue);
            minion.changeHealth(changeHealthValue);
            super.addToImpacts(minion);
        }
    }

    @Override
    public void remove() {

    }
}
