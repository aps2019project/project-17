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

    Cell(int row, int col) {
        this.row = row;
        this.col = col;
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

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean hasFlag() {
        return hasFlag;
    }

    public void setFlag(boolean hasFlag) {
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

    static int distance(Cell cell, Cell cell1) {
        int x0 = cell.getRow();
        int y0 = cell.getCol();
        int x1 = cell1.getRow();
        int y1 = cell1.getCol();
        return Math.abs(x0 - x1) + Math.abs(y0 - y1);
    }
}
