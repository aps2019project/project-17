package Effects.MinionEffects;

import Cards.Minion;
import Effects.Effect;
import Effects.enums.TargetDetail;
import Effects.enums.TargetRange;
import Effects.enums.TargetType;

public class ChangeProperties extends Effect {
    private int changeHealthValue;
    private int changePowerValue;
    private boolean returnEffect;

    public ChangeProperties(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail, int changeHealthValue, int changePowerValue, boolean returnEffect) {
        super(startTime, endTime, isContinues, targetRange, targetType, targetDetail);
        this.changeHealthValue = changeHealthValue;
        this.changePowerValue = changePowerValue;
        this.returnEffect = returnEffect;
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
        if (!returnEffect)
            return;
        for (Object impact : super.getImpacts()) {
            Minion minion = (Minion) impact;
            minion.changeAttackPower(-changePowerValue);
            minion.changeHealth(-changeHealthValue);
        }
    }

    public int getChangeHealthValue() {
        return changeHealthValue;
    }

    public int getChangePowerValue() {
        return changePowerValue;
    }

    public boolean isReturnEffect() {
        return returnEffect;
    }
}
