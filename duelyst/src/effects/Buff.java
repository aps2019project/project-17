package effects;

import Data.Player;
import GameGround.Battle;
import GameGround.Cell;

import java.util.ArrayList;

//-1 in effect time: attack
//0 in effect time: this turn
//1000 in effect time: for ever
//for clear buff: get cell to action function
//-105 in change health: numberOfAttack + 5
public class Buff {
    private ArrayList<BuffDetail> buffDetails;

    public void action(ArrayList<Object> objects, TargetType targetType) {
        for (Object object : objects) {
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
    }

    public void addBuff(BuffDetail buffDetail) {
        if (this.buffDetails == null)
            this.buffDetails = new ArrayList<>();
        this.buffDetails.add(buffDetail);
    }

    public void addBufftominion(Minion minion) {
        for (BuffDetail buffDetail : buffDetails) {
            buffDetail.addTarget(minion);
            minion.addBuff(buffDetail);
        }
    }

    public void addBuffToCell(Cell cell) {
        for (BuffDetail buffDetail : buffDetails) {
            buffDetail.addTarget(cell);
        }
    }

    private void actionForPlayer(Player player) {
        for (BuffDetail buffDetail : buffDetails) {
            if (buffDetail.getBuffType() == BuffType.CHANGE_MANA) {
                player.changeMana(+1);
            }
        }
    }

    private void actionForCell(Cell cell) {
        for (BuffDetail buffDetail : buffDetails) {
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
                case HOLY_CELL:
                    cell.setHoly(true);
            }
        }
    }


    private void actionForMinion(Minion minion) {
        for (BuffDetail buffDetail : this.buffDetails) {
            switch (buffDetail.getBuffType()) {
                case HOLY:
                    holyBuff(minion, buffDetail.getHolyBuffState());
                    break;
                case DE_HOLY:
                    holyBuff(minion, -1);
                    break;
                case STUN:
                    stunBuff(minion);
                    break;
                case DISARM:
                    disarmBuff(minion);
                    break;
                case WEAKNESS:
                case POISON:
                case CHANGE_ATTACK_POWER_OR_HEALTH_BUFF:
                    changeHealthOrAttack(minion, buffDetail.getChangeHealthValue(), buffDetail.getChangeAttackPowerValue());
                    break;
                case ANTI:
                    antiBuff(minion, buffDetail.getAntiBuffType());
                    break;
                case SPECIAL_SITUATION_BUFF:

            }
        }
    }

    private void removeBuff(BuffDetail buffDetail) {
        for (Object target : buffDetail.getTarget()) {
            switch (buffDetail.getBuffType()) {
                case HOLY:
                case DE_HOLY:
                    ((Minion) target).setHolyBuffState(0);
                    ((Minion) target).getBuff().removeBuff(buffDetail);
                    break;
                case WEAKNESS:
                case CHANGE_ATTACK_POWER_OR_HEALTH_BUFF:
                    if (buffDetail.getEffectTime() != -1) {
                        ((Minion) target).changeHealth(-buffDetail.getChangeHealthValue());
                        ((Minion) target).changeAttackPower(-buffDetail.getChangeAttackPowerValue());
                        ((Minion) target).getBuff().removeBuff(buffDetail);
                    }
                    break;
                case STUN:
                    break;
                case DISARM:
                    ((Minion) target).setCanAttack(true);
                    ((Minion) target).getBuff().removeBuff(buffDetail);
                    break;
                case FIRE_CELL:
                    ((Cell) target).setFireCell(false);
                    break;
                case POISON_CELL:
                    ((Cell) target).setPoison(false);
                case HOLY_CELL:
                    ((Cell) target).setHoly(false);
                case ANTI:
                    ((Minion) target).setAntiBuff(null);
                    ((Minion) target).getBuff().removeBuff(buffDetail);
                    break;
            }
            buffDetails.remove(buffDetail);
        }
    }

    public void passTurn() {
        for (BuffDetail buffDetail : buffDetails) {
            if (buffDetail.increaseEffectTime())
                removeBuff(buffDetail);
        }
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

    public ArrayList<BuffDetail> getBuffDetails() {
        return buffDetails;
    }
}