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
                case NONE:
                case ENEMY_HERO:
                case ENEMY:
                    actionForMinion((Minion) object, true);
                    break;
                case INSIDER:
                case INSIDER_HERO:
                    actionForMinion((Minion) object, false);
                    break;
                case CELL:
                    break;
                case PLAYER:
                    break;
            }
        }
    }

    private void actionForMinion(Minion minion, boolean isEnemy) {
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
                case CLEAR:
                    clearBuff(minion, isEnemy);
                    break;
            }
        }
    }

    private void removeBuff(BuffDetail buffDetail) {
        if (buffDetail.getBuffType() == BuffType.WEAKNESS) {
            for (Minion targetMinion : targetMinions) {
                deletingWeaknessBuff(targetMinion, buffDetail.getEffectTime());
            }
        }
        buffDetails.remove(buffDetail);
    }

    public void removeBuffs() {
        for (BuffDetail buffDetail : buffDetails) {
            if (buffDetail.getEffectTime() <= 0)
                removeBuff(buffDetail);
        }
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

    public void clearBuff(Minion minion, boolean isEnemy) {
        for (BuffDetail buffDetail : minion.getBuff().buffDetails) {
            if (!isEnemy && (buffDetail.getBuffType() == BuffType.DISARM || buffDetail.getBuffType() == BuffType.STUN || buffDetail.getBuffType() == BuffType.WEAKNESS))
                removeBuff(buffDetail);
            else if (isEnemy && (buffDetail.getBuffType() == BuffType.CHANGE_ATTACK_POWER_OR_HEALTH_BUFF && (buffDetail.getChangeAttackPowerValue() > 0 || buffDetail.getChangeHealthValue() > 0)))
                removeBuff(buffDetail);
        }
    }
}