package GameGround;

import Data.Player;
import effects.Minion;

public class Board {
    private final int rows = 5;
    private final int cols = 9;
    private Cell[][] cells;

    public Board() {
        this.cells = new Cell[rows][cols];
        for (int row = 0; row < this.cells.length; ++row) {
            for (int col = 0; col < this.cells[row].length; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    Cell[][] getCells() {
        return cells;
    }

    boolean isCoordinateAvailable(Cell cell, Player player, Battle battle) {
        for (int i = 0; i < this.getCells().length; i++) {
            for (int j = 0; j < this.getCells()[i].length; j++) {
                Cell cell1 = Battle.currentBattle.getCellFromBoard(i + 1, j + 1);
                if (cell1 == null || cell1.getCard() == null || cell1.getCard().getUserName().equals("") || cell1.getCard().getUserName() == null)
                    continue;
                if (Cell.distance(cell, cell1) <= 1) {
                    Minion minion1 = (Minion) cell1.getCard();
                    if (minion1 == null)
                        continue;
                    if (battle.cardIsMine(cell1.getCard(), player)) {
                        return true;
                    }
                }

            }
        }
        Cell cell1 = battle.getBoard().getCells()[cell.getRow() - 1 + 1][cell.getCol() - 1 + 1];
        if (cell1 != null && cell1.getCard() != null && cell1.getCard().getUserName() != null && battle.cardIsMine(cell1.getCard(), player))
            return true;
        cell1 = battle.getBoard().getCells()[cell.getRow() - 1 + 1][cell.getCol() - 1 + 1];
        if (cell1 != null && cell1.getCard() != null && cell1.getCard().getUserName() != null && battle.cardIsMine(cell1.getCard(), player))
            return true;
        cell1 = battle.getBoard().getCells()[cell.getRow() - 1 - 1][cell.getCol() - 1 - 1];
        if (cell1 != null && cell1.getCard() != null && cell1.getCard().getUserName() != null && battle.cardIsMine(cell1.getCard(), player))
            return true;
        cell1 = battle.getBoard().getCells()[cell.getRow() - 1 - 1][cell.getCol() - 1 + 1];
        return cell1 != null && cell1.getCard() != null && cell1.getCard().getUserName() != null && battle.cardIsMine(cell1.getCard(), player);
    }
}
