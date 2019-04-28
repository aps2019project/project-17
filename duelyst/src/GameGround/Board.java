package GameGround;

import Data.Player;
import effects.Card;
import effects.Minion;

public class Board {
    // Named-constants for the dimensions
    private final int rows = 5;
    private final int cols = 9;
    private Cell[][] cells;// a board composes of rows-by-cols GameGround.Cell instances

    public Board() {
        this.cells = new Cell[rows][cols];  // allocate the array
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                cells[row][col] = new Cell(row, col); // allocate element of the array
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean isCoordinateAvailable(Minion minion, Cell cell, Player player, Battle battle) {
        int x = cell.getRow();
        int y = cell.getCol();

        for (int i = 0; i <= this.getCells().length; i++) {
            for (int j = 0; j < this.getCells()[i].length; j++) {
                Cell cell1 = Cell.getCell(this, i, j);
                if (cell1 == null)
                    continue;

                if (Cell.distance(cell, cell1) <= minion.getMaxRangeToInput()) {
                    Minion minion1 = (Minion) cell1.getCard();
                    if (minion1 == null)
                        continue;
                    if (battle.cardIsMine(cell1.getCard(), player)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

}
