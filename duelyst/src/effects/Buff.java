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

    public void action(int x, int y, ArrayList<BuffDetail> buffDetails) {
        for (BuffDetail buffDetail : buffDetails) {
            Player targetPlayer = null;
            ArrayList<Object> targets = new ArrayList<>();
            ArrayList<Minion> targetMinions = new ArrayList<>();
            ArrayList<Cell> targetCells = new ArrayList<>();
            switch (buffDetail.getTargetType()) {
                case ENEMY_HERO:
                    targetMinions.add(Battle.getCurrentBattle().theOtherPlayer().getMainDeck().getHero());
                    break;
                case INSIDER_HERO_NOT_MELEE:
                    if (Battle.getCurrentBattle().whoseTurn().getMainDeck().getHero().getMinionType().equals(MinionType.MELEE))
                        return;
                case INSIDER_HERO:
                    targetMinions.add(Battle.getCurrentBattle().whoseTurn().getMainDeck().getHero());
                    break;
                case PLAYER:
                    targetPlayer = Battle.getCurrentBattle().whoseTurn();
                    break;
            }
            switch (buffDetail.getTargetRange()) {
                case ONE:
                    targets.add(Battle.getCurrentBattle().getCellFromBoard(x, y).getCard());
                    break;
                case TWO:
                    for (Cell cell : Battle.getCurrentBattle().getMinionsSquare(x, y)) {
                        targets.add(cell.getCard());
                    }
                    break;
                case THREE:
                    for (Cell cell : Battle.getCurrentBattle().getMinionsCube(x, y)) {
                        targets.add(cell.getCard());
                    }
                    break;
                case ALL:
                    targets.add(Battle.getCurrentBattle().getAllMinion());
                    break;
                case ALL_IN_COLUMN:
                    targets.add(Battle.getCurrentBattle().returnMinionsInColumn(x, y));
                    break;
                case AROUND:
                    targets.add(Battle.getCurrentBattle().minionsAroundCell(x, y));
                    break;
                case DISTANCE_TWO:
                    targets.add(Battle.getCurrentBattle().returnMinionsWhichDistance(x, y));
                    break;
                case CLOSEST_RANDOM:
                    targets.add(Battle.getCurrentBattle().closestRandomMinion(x, y));
                    break;
                case RANDOM:
                    targets.add(Battle.getCurrentBattle().returnRandomMinion(x, y));
                    break;
            }
            for (Object target : targets) {
                switch (buffDetail.getTargetType()) {
                    case ENEMY:
                        if (target instanceof Minion)
                            if (!((Minion) target).getUserName().equals(Battle.getCurrentBattle().whoseTurn().getUserName()))
                                targetMinions.add((Minion) target);
                        break;
                    case INSIDER:
                        if (target instanceof Minion)
                            if (((Minion) target).getUserName().equals(Battle.getCurrentBattle().whoseTurn().getUserName()))
                                targetMinions.add((Minion) target);
                        break;
                    case NONE:
                        if (target instanceof Minion)
                            targetMinions.add((Minion) target);
                        break;
                    case CELL:
                        if (target instanceof Cell)
                            targetCells.add((Cell) target);
                        break;
                    case INSIDER_NOT_MELEE:
                        if (target instanceof Minion)
                            if (((Minion) target).getUserName().equals(Battle.getCurrentBattle().whoseTurn().getUserName()) && !((Minion) target).getMinionType().equals(MinionType.MELEE))
                                targetMinions.add((Minion) target);
                        break;
                    case MELEE:
                        if (target instanceof Minion)
                            if (((Minion) target).getMinionType().equals(MinionType.MELEE))
                                targetMinions.add((Minion) target);
                        break;
                }
            }
            for (Minion targetMinion : targetMinions) {
                action(targetMinion, TargetType.INSIDER, buffDetail);
                addBuffToMinion(targetMinion, buffDetail);
            }
            for (Cell targetCell : targetCells) {
                action(targetCell, TargetType.CELL, buffDetail);
                addBuffToCell(targetCell, buffDetail);
            }
            if (targetPlayer != null) {
                action(targetPlayer, TargetType.PLAYER, buffDetail);
                addBuffToPlayer(targetPlayer, buffDetail);
            }
        }
        passTurn();
    }

    private void action(Object object, TargetType targetType, BuffDetail buffDetail) {
        switch (targetType) {
            case NONE:
            case ENEMY_HERO:
            case ENEMY:
            case INSIDER:
            case INSIDER_HERO:
                actionForMinion((Minion) object, buffDetail);
                break;
            case CELL:
                actionForCell((Cell) object, buffDetail);
                break;
            case PLAYER:
                actionForPlayer((Player) object, buffDetail);
        }
    }

    public void addBuff(BuffDetail buffDetail) {
        if (this.buffDetails == null)
            this.buffDetails = new ArrayList<>();
        this.buffDetails.add(buffDetail);
    }

    public void addBuffToMinion(Minion minion, BuffDetail buffDetail) {
        buffDetail.addTarget(minion);
        minion.addBuff(buffDetail);
    }

    public void addBuffToCell(Cell cell, BuffDetail buffDetail) {
        buffDetail.addTarget(cell);
        cell.getBuff().addBuff(buffDetail);
    }

    public void addBuffToPlayer(Player player, BuffDetail buffDetail) {
        buffDetail.addTarget(player);
        player.getBuff().addBuff(buffDetail);
    }

    private void actionForPlayer(Player player, BuffDetail buffDetail) {

        if (buffDetail.getBuffType() == BuffType.CHANGE_MANA) {
            player.changeMana(+1);
        }

    }

    private void actionForCell(Cell cell, BuffDetail buffDetail) {
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


    private void actionForMinion(Minion minion, BuffDetail buffDetail) {
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
            case CLEAR:
                clearBuff(minion, true);
        }
    }

    public void removeBuff(BuffDetail buffDetail) {
        for (Object target : buffDetail.getTarget()) {
            if (buffDetail.getId().equals("-1"))
                continue;
            switch (buffDetail.getBuffType()) {
                case HOLY:
                case DE_HOLY:
                    ((Minion) target).setHolyBuffState(0);
                    ((Minion) target).getBuff().removeBuff(buffDetail);
                    return;
                case WEAKNESS:
                case CHANGE_ATTACK_POWER_OR_HEALTH_BUFF:
                    if (buffDetail.getEffectTime() != -1) {
                        ((Minion) target).changeHealth(-buffDetail.getChangeHealthValue());
                        ((Minion) target).changeAttackPower(-buffDetail.getChangeAttackPowerValue());
                        ((Minion) target).getBuff().removeBuff(buffDetail);
                        return;
                    }
                    break;
                case STUN:
                    break;
                case DISARM:
                    ((Minion) target).setCanAttack(true);
                    ((Minion) target).getBuff().removeBuff(buffDetail);
                    return;
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
                    return;
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