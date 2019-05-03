package GameGround;

import effects.*;

import java.util.regex.Matcher;

public class Cell {
    private int row;
    private int col;
    private Card card;
    private boolean hasFlag;
    private Item item;

    Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Cell getCell(Board board, int x, int y) {
        for (int i = 0; i < board.getCells().length; i++) {
            for (int j = 0; j < board.getCells()[i].length; j++) {
                Cell cell = board.getCells()[i][j];
                if (i == x && j == y)
                    return cell;
            }
        }
        return null;
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

    static int distance(Cell cell, Cell cell1) {
        int x0 = cell.getRow();
        int y0 = cell.getCol();
        int x1 = cell1.getRow();
        int y1 = cell1.getCol();
        return Math.abs(x0 - x1) + Math.abs(y0 - y1);
    }
}
