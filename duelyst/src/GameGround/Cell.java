package GameGround;

import effects.*;


public class Cell {
    private int row;
    private int col;
    private Card card;
    private boolean hasFlag;
    private Item item;
    private boolean isFireCell;
    private boolean isPoison;
    private boolean isHoly;
    private BuffDetail poison;
    private BuffDetail fire;
    private BuffDetail holy;
    private Buff buff;

    Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Buff getBuff() {
        return buff;
    }

    public void addBuff(BuffDetail buffDetail) {
        if (buff == null)
            buff = new Buff();
        buff.addBuff(buffDetail);
    }

    public int getRow() {
        return row;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCol() {
        return col;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    boolean hasFlag() {
        return hasFlag;
    }

    void setFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public void setFireCell(boolean fireCell) {
        isFireCell = fireCell;
    }

    public void setPoison(boolean poison) {
        isPoison = poison;
    }

    public void setHoly(boolean holy) {
        isHoly = holy;
    }

    void enterCell() {
        if (card == null)
            return;
        if (this.isPoison) {
            poison = new BuffDetail(-2, BuffType.POISON, -1, TargetType.NONE, TargetRange.ONE);
            ((Minion) getCard()).addBuff(poison);
        }
        if (this.isFireCell) {
            fire = new BuffDetail(-2, BuffType.CHANGE_ATTACK_POWER_OR_HEALTH_BUFF, TargetType.NONE, TargetRange.ONE, 0, 0, -2);
            ((Minion) getCard()).addBuff(fire);
        }
        if (this.isHoly) {
            holy = new BuffDetail(-2, BuffType.HOLY, TargetType.NONE, TargetRange.ONE, -1, 1);
            ((Minion) getCard()).addBuff(holy);
        }
    }

    void exitCell() {
        if (card == null)
            return;
        if (this.isPoison) {
            ((Minion) getCard()).getBuff().removeBuff(poison);
        }
        if (this.isFireCell) {
            ((Minion) getCard()).getBuff().removeBuff(fire);
        }
        if (this.isHoly) {
            ((Minion) getCard()).getBuff().removeBuff(holy);
        }

    }

    static int distance(Cell cell, Cell cell1) {
        int x0 = cell.getRow();
        int y0 = cell.getCol();
        int x1 = cell1.getRow();
        int y1 = cell1.getCol();
        return Math.abs(x0 - x1) + Math.abs(y0 - y1);
    }

    void passTurn() {
        if (this.buff == null || this.buff.getBuffDetails() == null)
            return;
        buff.action(3, 3, buff.getBuffDetails());
    }
}
