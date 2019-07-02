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
    public void effect(Object[] targets) {
        for (int i = 0; i < targets.length; i++) {
            if (targets[i] instanceof Minion) {
                Minion minion = (Minion) targets[i];
                minion.changeAttackPower(changePowerValue);
                minion.changeHealth(changeHealthValue);
                if (super.getEndTime() != 0)
                    super.addToImpacts(minion);
            }
        }
    }

    @Override
    public void remove() {
        for (Object impact : super.getImpacts()) {
            if (!returnEffect)
                continue;
            Minion minion = (Minion) impact;
            if (changeHealthValue == -123)
                minion.changeHealth(-minion.getNumberOfAttack() + 5);
            else
                minion.changeHealth(-changeHealthValue);
            minion.changeAttackPower(-changePowerValue);
            minion.getEffects().remove(this);
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
