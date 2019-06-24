package Effects;

import Cards.Minion;
import Data.Player;
import GameGround.Battle;
import GameGround.Cell;
import Effects.enums.MinionType;
import Effects.enums.TargetDetail;
import Effects.enums.TargetRange;
import Effects.enums.TargetType;

import java.util.ArrayList;
import java.util.Arrays;

public class Effect {
    private String id;
    private int startTime;
    private int endTime;
    private boolean isContinues;
    private String ownerUserName;
    private TargetRange targetRange;
    private TargetType targetType;
    private TargetDetail targetDetail;
    private ArrayList<Object> impacts = new ArrayList<>();
    private boolean isDisable;

    public Effect() {
    }

    public Effect(int startTime, int endTime, boolean isContinues, TargetRange targetRange, TargetType targetType, TargetDetail targetDetail) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isContinues = isContinues;
        this.targetRange = targetRange;
        this.targetType = targetType;
        this.targetDetail = targetDetail;
    }

    public void action(Cell cell) {
        if (startTime < 0)
            return;
        if (this.targetType == TargetType.PLAYER) {
            if (this.targetDetail.equals(TargetDetail.INSIDER)) {
                effect(new Player[]{Battle.getCurrentBattle().whoseTurn()});
                impacts.add(Battle.getCurrentBattle().whoseTurn());
            } else {
                effect(new Player[]{Battle.getCurrentBattle().theOtherPlayer()});
                impacts.add(Battle.getCurrentBattle().theOtherPlayer());
            }
            return;
        }
        boolean isCell;
        isCell = targetType == TargetType.CELL;
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
                minions.add(Battle.getCurrentBattle().theOtherPlayer().getMainDeck().getHero());
                break;
            case INSIDER_HERO:
                break;
        }
        if (isCell) {
            effect(cells.toArray());
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
                        if (!targetMinion.getMinionType().equals(MinionType.MELEE))
                            continue;
                        break;
                    case INSIDER_NOT_MELEE:
                        if (targetMinion.getMinionType().equals(MinionType.MELEE) || !targetMinion.getUserName().equals(ownerUserName))
                            continue;
                        break;
                }
                Minion minion = (Minion) targetCell.getCard();
                minions.add(minion);
                minion.addEffect(this);
            }
        }
        if (minions.size() != 0) {
            effect(minions.toArray());
        }
    }

    public void checkForRemove() {
        if (startTime > 0)
            startTime--;
        if (endTime > 0)
            endTime--;
        if (endTime == 0) {
            remove();
            this.isDisable = true;
        }
    }

    public void effect(Object[] targets) {
    }

    public void remove() {
    }

    public void addToImpacts(Object object) {
        if (impacts == null)
            impacts = new ArrayList<>();
        if(impacts.contains(object))
            return;
        impacts.add(object);
    }

    public ArrayList<Object> getImpacts() {
        if (impacts == null)
            impacts = new ArrayList<>();
        return impacts;
    }

    public boolean isContinues() {
        return isContinues;
    }

    public boolean isDisable() {
        return isDisable;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public TargetRange getTargetRange() {
        return targetRange;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public TargetDetail getTargetDetail() {
        return targetDetail;
    }
}
