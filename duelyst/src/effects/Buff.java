package effects;

import java.util.ArrayList;

public class Buff {
    private ArrayList<BuffDetail> buffDetails;
    private ArrayList<Minion> targetMinions;

    public Buff() {
        this.targetMinions = new ArrayList<>();
        this.buffDetails = new ArrayList<>();
    }

    public void action(ArrayList<Object> objects, TargetType targetType) {
        for (Object object : objects) {
            switch (targetType) {
                case ENEMY:
                case INSIDER:
                case ENEMY_HERO:
                case INSIDER_HERO:
                    actionForMinion((Minion) object);
                    break;
                case CELL:
                    break;
                case PLAYER:
                    break;
            }
        }
    }

    private void actionForMinion(Minion minion) {
        for (BuffDetail buffDetail : this.buffDetails) {
            switch (buffDetail.getBuffType()) {
                case HOYL:
                    holyBuff(minion);
                    break;
                case STUN:
                    stunBuff(minion);
                    break;
                case DISARM:
                    disarmBuff(minion);
                    break;
                case WEAKNESS:
                    targetMinions.add(minion);
                case CHANGE_ATTACK_POWER_OR_HEALTH_BUFF:
                    changeHealthOrAttack(minion, buffDetail.getChangeHealthValue(), buffDetail.getChangeAttackPowerValue());
                    break;
                case CLEAR://mosbat manfi boodan lahaz nashode
                    clearBuff(minion);
                    break;
            }
        }

    }


    private void removeBuffs(ArrayList<BuffDetail> buffDetails) {
        for (BuffDetail buffDetail : buffDetails) {
            if (buffDetail.getBuffType() == BuffType.WEAKNESS) {
                for (Minion targetMinion : targetMinions) {
                    deletingWeaknessBuff(targetMinion, buffDetail.getEffectTime());
                }
            }
        }
        this.buffDetails.removeAll(buffDetails);
    }

    public void deletingWeaknessBuff(Minion minion, int time) {
        minion.changeHealth(time);
    }

    public void addBuff(BuffDetail buffDetail) {
        this.buffDetails.add(buffDetail);
    }

    public void holyBuff(Minion minion) {
        minion.activeHolyBuff();
    }

    public void changeHealthOrAttack(Minion minion, int changeHealthValue, int changeAttackValue) {
        minion.changeHealth(changeHealthValue);
        minion.changeAttackPower(changeAttackValue);
    }

    public void stunBuff(Minion minion) {
        minion.setStun(true);
        minion.setCanMove(false);
        minion.setCanAttack(false);
    }

    public void disarmBuff(Minion minion) {
        minion.setCanCounterAttack(false);
    }

    public void clearBuff(Minion minion) {
        minion.getBuff().clear();
    }

    private void clear() {
        this.buffDetails.clear();
    }
}