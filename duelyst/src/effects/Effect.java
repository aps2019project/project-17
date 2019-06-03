package effects;

import Cards.Minion;
import GameGround.Battle;
import GameGround.Cell;
import effects.enums.TargetDetail;
import effects.enums.TargetRange;
import effects.enums.TargetType;

import java.util.ArrayList;

public abstract class Effect {
    private int startTime;
    private int endTime;
    private boolean isContinues;
    private String ownerUserName;
    private TargetRange targetRange;
    private TargetType targetType;
    private TargetDetail targetDetail;
    private ArrayList<Object> impacts = new ArrayList<>();

    public Effect(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isContinues = isContinues;
        this.targetRange = targetRange;
        this.targetType = targetType;
        this.targetDetail = targetDetail;
    }

    public void action(Cell cell) {
        if (this.targetType == TargetType.PLAYER) {
            if (this.targetDetail.equals(TargetDetail.INSIDER)) {
                effect(Battle.getCurrentBattle().whoseTurn());
                impacts.add(Battle.getCurrentBattle().whoseTurn());
            } else {
                effect(Battle.getCurrentBattle().theOtherPlayer());
                impacts.add(Battle.getCurrentBattle().theOtherPlayer());
            }
            return;
        }
        boolean isCell;
        if (targetType == TargetType.CELL)
            isCell = true;
        else
            isCell = false;
        ArrayList<Cell> cells = new ArrayList<>();
        ArrayList<Minion> minions = new ArrayList<>();
        switch (this.targetRange) {
            case ONE:
            case SELF:
                cells.add(cell);
                break;
            case TWO:
                cells = Battle.getCurrentBattle().getMinionsSquare(cell.getRow(), cell.getCol());
                break;
            case THREE:
                cells = Battle.getCurrentBattle().getMinionsCube(cell.getRow(), cell.getCol());
                break;
            case ALL:
                cells = Battle.getCurrentBattle().getAllCells();
                break;
            case ALL_IN_COLUMN:
                cells = Battle.getCurrentBattle().returnCellsInColumn(cell.getRow(), cell.getCol());
                break;
            case AROUND:
                cells = Battle.getCurrentBattle().getCellsAroundCell(cell.getRow(), cell.getCol());
                break;
            case DISTANCE_TWO:
                cells = Battle.getCurrentBattle().getCellsWhichDistance(cell.getRow(), cell.getCol());
                break;
            case CLOSEST_RANDOM:
                minions.add(Battle.getCurrentBattle().closestRandomMinion(cell.getRow(), cell.getCol()));
                break;
            case RANDOM:
                minions.add(Battle.getCurrentBattle().returnRandomMinion(cell.getRow(), cell.getCol()));
                break;
            case ENEMY_HERO:
                minions.add(Battle.getCurrentBattle().theOtherPlayer().getMainDeck().getHero())
                break;
            case INSIDER_HERO:
                break;
        }
        if (isCell) {
            effect(cells);
            impacts.addAll(cells);
            return;
        }
        for (Cell targetCell : cells) {
            if (targetCell.getCard() != null && targetCell.getCard() instanceof Minion) {
                Minion targetMinion = (Minion) targetCell.getCard();
                switch (targetDetail) {
                    case ENEMY:
                        if (targetMinion.getUserName().equals(ownerUserName))
                            continue;
                        break;
                    case INSIDER:
                        if (!targetMinion.getUserName().equals(ownerUserName))
                            continue;
                        break;
                    case MELEE:
                        break;
                    case INSIDER_NOT_MELEE:
                        break;
                    case NONE:
                        break;
                }
                minions.add((Minion) targetCell.getCard());
            }
        }
        effect(minions);
        impacts.addAll(minions);
    }

    public void checkForRemove() {

    }

    public abstract void effect(Object... targets);

    public abstract void remove();

    public void addToImpacts(Object object) {
        impacts.add(object);
    }

    public ArrayList<Object> getImpacts() {
        return impacts;
    }

    public boolean isContinues() {
        return isContinues;
    }
}
