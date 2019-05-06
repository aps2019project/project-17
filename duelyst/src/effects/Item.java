package effects;

import Data.Player;
import GameGround.Battle;
import GameGround.Cell;

import java.util.ArrayList;

public class Item {
    private String name;
    private String id;
    private String desc;
    private int price;
    private Buff buff;
    private BuffDetail specialSituationBuff;

    public Item(String name, String id, int price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public void init() {
        this.buff = new Buff();
    }

    public void action(int x, int y) {
        for (BuffDetail buffDetail : this.buff.getBuffDetails()) {
            ArrayList<Cell> targetCells = new ArrayList<>();
            Player targetPlayer = null;
            ArrayList<Minion> targetMinions = new ArrayList<>();
            ArrayList<Minion> searchMinions = new ArrayList<>();
            switch (buffDetail.getTargetRange()) {
                case ONE:
                    switch (buffDetail.getTargetType()) {
                        case ENEMY:
                        case INSIDER:
                        case NONE:
                            targetMinions.add((Minion) Battle.getCurrentBattle().getCellFromBoard(x, y).getCard());
                            break;
                        case ENEMY_HERO:
                            targetMinions.add(Battle.getCurrentBattle().theOtherPlayer().getMainDeck().getHero());
                            break;
                        case INSIDER_HERO:
                        case INSIDER_HERO_NOT_MELEE:
                            targetMinions.add(Battle.getCurrentBattle().whoseTurn().getMainDeck().getHero());
                            break;
                        case CELL:
                            targetCells.add(Battle.getCurrentBattle().getCellFromBoard(x, y));
                            break;
                        case PLAYER:
                            targetPlayer = Battle.getCurrentBattle().whoseTurn();
                            break;
                        case SELF_NOT_MELEE:
                        case INSIDER_NOT_MELEE:
                            if (!((Minion) Battle.getCurrentBattle().getCellFromBoard(x, y).getCard()).getMinionType().equals(MinionType.MELEE))
                                targetMinions.add((Minion) Battle.getCurrentBattle().getCellFromBoard(x, y).getCard());
                            break;

                        case MELEE:
                            if (((Minion) Battle.getCurrentBattle().getCellFromBoard(x, y).getCard()).getMinionType().equals(MinionType.MELEE))
                                targetMinions.add((Minion) Battle.getCurrentBattle().getCellFromBoard(x, y).getCard());
                            break;
                    }
                    break;
                case TWO:
                    searchMinions = Battle.getCurrentBattle().getMinionsSquare(x, y);
                    break;
                case THREE:
                    searchMinions = Battle.getCurrentBattle().getMinionsCube(x, y);
                    break;
                case ALL:
                    searchMinions = Battle.getCurrentBattle().getAllMinion();
                    break;
                case ALL_IN_COLUMN:
                    searchMinions = Battle.getCurrentBattle().returnMinionsInColumn(x, y);
                    break;
                case AROUND:
                    searchMinions = Battle.getCurrentBattle().minionsAroundCell(x, y);
                    break;
                case DISTANCE_TWO:
                    searchMinions = Battle.getCurrentBattle().returnMinionsWhichDistance(x, y);
                    break;
                case CLOSEST_RANDOM:
                    searchMinions.add(Battle.getCurrentBattle().closestRandomMinion(x, y));
                    break;
                case RANDOM:
                    searchMinions.add(Battle.getCurrentBattle().returnRandomMinion(x, y));
                    break;
            }
            for (Minion minion : searchMinions) {
                switch (buffDetail.getTargetType()) {
                    case ENEMY:
                        if (!Battle.getCurrentBattle().whoseTurn().getUserName().equals(minion.getUserName()))
                            targetMinions.add(minion);
                        break;
                    case INSIDER:
                        if (Battle.getCurrentBattle().whoseTurn().getUserName().equals(minion.getUserName()))
                            targetMinions.add(minion);
                        break;
                    case NONE:
                        targetMinions.add(minion);
                        break;
                    case CELL:
                        break;

                }
            }
            if (buffDetail.getTargetType().equals(TargetType.PLAYER))
                buff.action(targetPlayer, TargetType.PLAYER, buffDetail);
        }
    }

    public void setSpecialSituationBuff(BuffDetail buffDetail) {
        this.specialSituationBuff = buffDetail;
    }

    public Buff getBuff() {
        return buff;
    }

    public void addBuff(BuffDetail buffDetail) {
        this.buff.addBuff(buffDetail);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void show() {
        System.out.println("Name : " + this.name + " - Desc : " + this.desc);
    }

    public int getPrice() {
        return price;
    }
}
