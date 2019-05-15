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
        if (Battle.getCurrentBattle() == null)
            return;
        for (int i = 0; i < buffDetails.size(); i++) {
            Player targetPlayer = null;
            ArrayList<Object> targets = new ArrayList<>();
            ArrayList<Minion> targetMinions = new ArrayList<>();
            ArrayList<Cell> targetCells = new ArrayList<>();
            if (buffDetails.get(i) != null) {
                switch (buffDetails.get(i).getTargetType()) {
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
            }
            switch (buffDetails.get(i).getTargetRange()) {
                case ONE:
                    if (buffDetails.get(i).getTargetType() == TargetType.CELL)
                        targets.add(Battle.getCurrentBattle().getCellFromBoard(x, y));
                    else
                        targets.add((Battle.getCurrentBattle().getCellFromBoard(x, y)).getCard());
                    break;
                case TWO:
                    for (Cell cell : Battle.getCurrentBattle().getMinionsSquare(x, y)) {
                        if (buffDetails.get(i).getTargetType() == TargetType.CELL)
                            targets.add(cell);
                        else
                            targets.add(cell.getCard());
                    }
                    break;
                case THREE:
                    for (Cell cell : Battle.getCurrentBattle().getMinionsCube(x, y)) {
                        if (buffDetails.get(i).getTargetType() == TargetType.CELL)
                            targets.add(cell);
                        else
                            targets.add(cell.getCard());
                    }
                    break;
                case ALL:
                    targets.addAll(Battle.getCurrentBattle().getAllMinion());
                    break;
                case ALL_IN_COLUMN:
                    targets.addAll(Battle.getCurrentBattle().returnMinionsInColumn(x, y));
                    break;
                case AROUND:
                    targets.addAll(Battle.getCurrentBattle().minionsAroundCell(x, y));
                    break;
                case DISTANCE_TWO:
                    targets.addAll(Battle.getCurrentBattle().returnMinionsWhichDistance(x, y));
                    break;
                case CLOSEST_RANDOM:
                    targets.add(Battle.getCurrentBattle().closestRandomMinion(x, y));
                    break;
                case RANDOM:
                    targets.add(Battle.getCurrentBattle().returnRandomMinion(x, y));
                    break;
            }
            for (Object target : targets) {
                switch (buffDetails.get(i).getTargetType()) {
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
                action(targetMinion, TargetType.INSIDER, buffDetails.get(i));
                addBuffToMinion(targetMinion, buffDetails.get(i));
            }
            for (Cell targetCell : targetCells) {
                action(targetCell, TargetType.CELL, buffDetails.get(i));
                addBuffToCell(targetCell, buffDetails.get(i));
            }
            if (targetPlayer != null) {
                action(targetPlayer, TargetType.PLAYER, buffDetails.get(i));
                addBuffToPlayer(targetPlayer, buffDetails.get(i));
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

    private void addBuffToMinion(Minion minion, BuffDetail buffDetail) {
        buffDetail.addTarget(minion);
        minion.addBuff(buffDetail);
    }

    private void addBuffToCell(Cell cell, BuffDetail buffDetail) {
        buffDetail.addTarget(cell);
        cell.addBuff(buffDetail);
    }

    private void addBuffToPlayer(Player player, BuffDetail buffDetail) {
        buffDetail.addTarget(player);
        player.getBuff().addBuff(buffDetail);
    }

    private void actionForPlayer(Player player, BuffDetail buffDetail) {

        if (buffDetail.getBuffType() == BuffType.CHANGE_MANA) {
            player.changeMana(+1);
        }
        if (buffDetail.getBuffType() == BuffType.SPECIAL_SITUATION_BUFF && buffDetail.getSituation() == SpecialSituation.PUTT_IN_GROUND)
            player.setPutInGroundAttackEnemyHero(true);
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
                changeHealthOrAttack(minion, buffDetail.getChangeHealth(), buffDetail.getChangeAttackPower());
                break;
            case ANTI:
                antiBuff(minion, buffDetail.getAntiBuffType());
                break;
            case CLEAR:
                clearBuff(minion, true);
        }
    }

    public void removeBuff(BuffDetail buffDetail) {
        if (buffDetail.getTarget() == null)
            return;
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
                        ((Minion) target).changeHealth(-buffDetail.getChangeHealth());
                        ((Minion) target).changeAttackPower(-buffDetail.getChangeAttackPower());
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

    private void passTurn() {
        for (int i = 0; i < buffDetails.size(); i++) {
            if (buffDetails.get(i).increaseEffectTime())
                removeBuff(buffDetails.get(i));
        }
    }

    private void holyBuff(Minion minion, int holyBuffState) {
        if (minion.getAntiBuff() == null || !minion.getAntiBuff().equals(BuffType.HOLY))
            minion.activeHolyBuff(holyBuffState);
    }

    private void changeHealthOrAttack(Minion minion, int changeHealthValue, int changeAttackValue) {
        changeHealthValue += minion.getHolyBuffState();
        minion.changeHealth(changeHealthValue);
        minion.changeAttackPower(changeAttackValue);
    }

    private void stunBuff(Minion minion) {
        if (minion.getAntiBuff() != null) {
            if (!minion.getAntiBuff().equals(BuffType.STUN)) {
                minion.setStun(true);
            }
        }
    }

    private void disarmBuff(Minion minion) {
        if (minion.getAntiBuff() == null)
            return;
        if (!minion.getAntiBuff().equals(BuffType.DISARM))
            minion.setCanCounterAttack(false);
    }

    private void clearBuff(Minion minion, boolean isEnemy) {
        for (BuffDetail buffDetail : minion.getBuff().buffDetails) {
            if (isEnemy) {
                switch (buffDetail.getBuffType()) {
                    case HOLY:
                        removeBuff(buffDetail);
                        break;
                    case CHANGE_ATTACK_POWER_OR_HEALTH_BUFF:
                        if (buffDetail.getChangeHealth() > 0 || buffDetail.getChangeAttackPower() > 0)
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
                        if (buffDetail.getChangeHealth() < 0 || buffDetail.getChangeAttackPower() < 0)
                            removeBuff(buffDetail);
                        break;
                }

            }
        }
    }

    private void antiBuff(Minion minion, BuffType buffType) {
        minion.setAntiBuff(buffType);
    }

    public ArrayList<BuffDetail> getBuffDetails() {
        return buffDetails;
    }
}