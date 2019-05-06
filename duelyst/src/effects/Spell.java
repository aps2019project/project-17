package effects;

import GameGround.Battle;

import java.util.ArrayList;

public class Spell extends Card {
    private int manaPoint;
    private Buff buff;

    public Spell(String name, String id, int price, int manaPoint) {
        super(name, id, price);
        this.manaPoint = manaPoint;
    }

    public void addBuff(BuffDetail buffDetail) {
        if (this.buff == null)
            this.buff = new Buff();
        this.buff.addBuff(buffDetail);
    }

    public void show() {
        System.out.println("Name : " + this.name + " - MP : " + this.manaPoint + " - Desc : " + this.desc);
    }

    public void action(int x, int y) {
        for (BuffDetail buffDetail : this.buff.getBuffDetails()) {
            ArrayList<Minion> targetMinions = new ArrayList<>();
            ArrayList<Minion> searchMinions = new ArrayList<>();
            switch (buffDetail.getTargetRange()) {
                case ONE:
                    switch (buffDetail.getTargetType()) {
                        case CELL:
                            break;
                        case ENEMY:
                        case INSIDER:
                        case NONE:
                            targetMinions.add((Minion) Battle.getCurrentBattle().getCellFromBoard(x, y).getCard());
                            break;
                        case ENEMY_HERO:
                            targetMinions.add(Battle.getCurrentBattle().theOtherPlayer().getMainDeck().getHero());
                            break;
                        case INSIDER_HERO:
                            targetMinions.add(Battle.getCurrentBattle().whoseTurn().getMainDeck().getHero());
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
                }
            }
            for (Minion targetMinion : targetMinions) {
                buff.action(targetMinion, TargetType.NONE, buffDetail);
            }
        }
    }

    public int getManaPoint() {
        return manaPoint;
    }

    public void setManaPoint(int manaPoint) {
        this.manaPoint = manaPoint;
    }

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }
}
