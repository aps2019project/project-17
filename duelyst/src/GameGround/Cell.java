package GameGround;

import effects.*;
public class Cell {
    //coordination of the cell
    private int row;
    private int col;
    private Cards card;//show which card is un the cell/if null there no card on the cell
    private boolean hasFlag;//show if there is any flag on the cell
    private Buff buff;//if it was null the cell doesn't have buff

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
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

    public Cards getCard() {
        return card;
    }

    public void setCard(Cards card) {
        this.card = card;
    }

    public boolean isHasFlag() {
        return hasFlag;
    }

    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }
}
