package effects;

import Data.Player;

import java.util.ArrayList;

public class Buff {
    private ArrayList<BuffDetail> buffDetails;
    private ArrayList<Minion> targetMinionsForWeakness;
    private ArrayList<Minion> targetMinionsForHoly;

    public Buff() {
        this.targetMinionsForWeakness = new ArrayList<>();
        this.buffDetails = new ArrayList<>();
    }

    private void action(Object object, TargetType targetType) {
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
                actionForPlayer((Player) object);
        }
    }

    public void addBuff(BuffDetail buffDetail) {
        this.buffDetails.add(buffDetail);
    }

    private void actionForPlayer(Player player) {
        for (BuffDetail buffDetail : buffDetails) {
            switch (buffDetail.getBuffType()) {
                case CHANGE_MANA:
                    player.changeMana(+1);
            }
        }
    }

    private void actionForMinion(Minion minion, boolean isEnemy) {
        for (BuffDetail buffDetail : this.buffDetails) {
            switch (buffDetail.getBuffType()) {
                case HOLY:
                    targetMinionsForWeakness.add(minion);
                    if (buffDetail.getId() == 137)
                        holyBuff(minion, 12);
                    else
                        holyBuff(minion, 1);
                    break;
                case DE_HOLY:
                    targetMinionsForWeakness.add(minion);
                    holyBuff(minion, -1);
                    break;
                case STUN:
                    stunBuff(minion);
                    break;
                case DISARM:
                    disarmBuff(minion);
                    break;
                case WEAKNESS:
                    targetMinionsForWeakness.add(minion);
                case CHANGE_ATTACK_POWER_OR_HEALTH_BUFF:
                    changeHealthOrAttack(minion, buffDetail.getChangeHealthValue(), buffDetail.getChangeAttackPowerValue());
                    break;
                case CLEAR:
                    clearBuff(minion, isEnemy);
                    break;
                case ANTI:
                    antiBuff(minion, buffDetail.getAntiBuffType());
                    break;
                case ANTI_ALL_NEGATIVE:
                    break;
                case ANTI_ALL_POSITIVE:
            }
        }
    }

    private void removeBuff(BuffDetail buffDetail) {
        if (buffDetail.getBuffType().equals(BuffType.WEAKNESS)) {
            for (Minion targetMinion : targetMinionsForWeakness) {
                deletingWeaknessBuff(targetMinion, buffDetail.getEffectTime());
            }
        } else if (buffDetail.getBuffType().equals(BuffType.HOLY)) {

        }
        buffDetails.remove(buffDetail);
    }

    public void removeBuffs() {
        for (BuffDetail buffDetail : buffDetails) {
            if (buffDetail.increaseEffectTime())
                removeBuff(buffDetail);
        }
    }

    public void deletingWeaknessBuff(Minion minion, int time) {
        minion.changeHealth(time);
    }


    public void holyBuff(Minion minion, int holyBuffState) {
        if (!minion.getAntiBuff().equals(BuffType.HOLY))
            minion.activeHolyBuff(holyBuffState);
    }

    public void changeHealthOrAttack(Minion minion, int changeHealthValue, int changeAttackValue) {
        changeHealthValue += minion.getHolyBuffState();
        minion.changeHealth(changeHealthValue);
        minion.changeAttackPower(changeAttackValue);
    }

    public void stunBuff(Minion minion) {
        if (!minion.getAntiBuff().equals(BuffType.STUN)) {
            minion.setStun(true);
        }
    }

    public void disarmBuff(Minion minion) {
        if (!minion.getAntiBuff().equals(BuffType.DISARM))
            minion.setCanCounterAttack(false);
    }

    public void clearBuff(Minion minion, boolean isEnemy) {
        for (BuffDetail buffDetail : minion.getTakenBuff().buffDetails) {
            if (!isEnemy && (buffDetail.getBuffType() == BuffType.DISARM || buffDetail.getBuffType() == BuffType.STUN || buffDetail.getBuffType() == BuffType.WEAKNESS))
                removeBuff(buffDetail);
            else if (isEnemy && (buffDetail.getBuffType() == BuffType.CHANGE_ATTACK_POWER_OR_HEALTH_BUFF && (buffDetail.getChangeAttackPowerValue() > 0 || buffDetail.getChangeHealthValue() > 0)))
                removeBuff(buffDetail);
        }
    }

    public void antiBuff(Minion minion, BuffType buffType) {
        minion.setAntiBuff(buffType);
    }
}