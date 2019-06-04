package GameGround;

import Cards.Card;
import Cards.Item;
import Cards.Minion;
import Effects.*;
import Effects.enums.BuffType;
import Effects.enums.TargetRange;
import Effects.enums.TargetType;


public class Cell {
    private int row;
    private int col;
    private Card card;
    private boolean hasFlag;
    private Item item;
    private boolean isFireCell;
    private boolean isPoison;
    private boolean isHoly;
    private Effect effect = null;

    Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
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


    void exitCell() {
        if (card == null)
            return;
        effect.remove();
        effect = null;
    }

    static int distance(Cell cell, Cell cell1) {
        int x0 = cell.getRow();
        int y0 = cell.getCol();
        int x1 = cell1.getRow();
        int y1 = cell1.getCol();
        return Math.abs(x0 - x1) + Math.abs(y0 - y1);
    }

    void passTurn() {
        if (effect != null && getCard() != null)
            effect.action(this);
    }
}
