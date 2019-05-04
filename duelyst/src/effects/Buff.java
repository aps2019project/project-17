package effects;

import Data.Player;
import GameGround.Battle;
import GameGround.Cell;

import java.util.ArrayList;

//-1 in effect time: for ever
//0 in effect time: this turn
//for clear buff: get cell to action function
public class Buff {
    private ArrayList<BuffDetail> buffDetails;

    private void action(Object object, TargetType targetType) {
        switch (targetType) {
            case NONE:
            case ENEMY_HERO:
            case ENEMY:
            case INSIDER:
            case INSIDER_HERO:
                actionForMinion((Minion) object);
                break;
            case CELL:
                actionForCell((Cell) object);
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

    private void actionForCell(Cell cell) {
        for (BuffDetail buffDetail : buffDetails) {
            buffDetail.addTarget(cell);
            switch (buffDetail.getBuffType()) {
                case CLEAR:
                    if (Battle.getCurrentBattle().cardIsMine(cell.getCard(), Battle.getCurrentBattle().whoseTurn()))
                        clearBuff((Minion) cell.getCard(), false);
                    else
                        clearBuff((Minion) cell.getCard(), true);
                    break;
                case FIRE_CELL:
                    cell.setFireCell(true);
                    break;
                case POISON_CELL:
                    cell.setPoison(true);
            }
        }
    }

    private void actionForMinion(Minion minion) {
        for (BuffDetail buffDetail : this.buffDetails) {
            buffDetail.addTarget(minion);
            minion.addBuff(buffDetail);
            switch (buffDetail.getBuffType()) {
                case HOLY:
                    if (buffDetail.getId().equals("137"))
                        holyBuff(minion, 12);
                    else
                        holyBuff(minion, buffDetail.getHolyBuffState());
                    break;
                case DE_HOLY:
                    holyBuff(minion, -1);
                    break;
                case STUN:
                    stunBuff(minion);
                    break;
//
                case DISARM:
                    disarmBuff(minion);
                    break;
//
                case WEAKNESS:
                case POISON:
                case CHANGE_ATTACK_POWER_OR_HEALTH_BUFF:
                    changeHealthOrAttack(minion, buffDetail.getChangeHealthValue(), buffDetail.getChangeAttackPowerValue());
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
        for (Object target : buffDetail.getTarget()) {
            switch (buffDetail.getBuffType()) {
                case HOLY:
                    break;
                case DE_HOLY:
                    break;
                case WEAKNESS:
                    break;
                case STUN:
                    break;
                case DISARM:
                    ((Minion) target).setCanAttack(true);
                    break;
                case FIRE_CELL:
                    ((Cell) target).setFireCell(false);
                    break;
                case POISON_CELL:
                    ((Cell) target).setPoison(false);
                case ANTI:
                    break;
                case ANTI_ALL_NEGATIVE:
                    break;
                case ANTI_ALL_POSITIVE:
                    break;
                case CHANGE_MANA:
                    break;
                case ADD_BUFF:
                    break;
                case SPECIAL_SITUATION_BUFF:
                    break;
            }
        }
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
        for (BuffDetail buffDetail : minion.getBuff().buffDetails) {
            if (isEnemy) {
                switch (buffDetail.getBuffType()) {
                    case HOLY:
                        removeBuff(buffDetail);
                        break;
                    case CHANGE_ATTACK_POWER_OR_HEALTH_BUFF:
                        if (buffDetail.getChangeHealthValue() > 0 || buffDetail.getChangeAttackPowerValue() > 0)
                            removeBuff(buffDetail);
                        break;
                }
            } else {
                switch (buffDetail.getBuffType()) {
                    case DE_HOLY:
                    case WEAKNESS:
                    case STUN:
                    case DISARM:
                        removeBuff(buffDetail);
                        break;
                    case CHANGE_ATTACK_POWER_OR_HEALTH_BUFF:
                        if (buffDetail.getChangeHealthValue() < 0 || buffDetail.getChangeAttackPowerValue() < 0)
                            removeBuff(buffDetail);
                        break;
                }

            }
        }
    }

    public void antiBuff(Minion minion, BuffType buffType) {
        minion.setAntiBuff(buffType);
    }
}