package GameGround;

import Data.Player;

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

    boolean isCoordinateAvailable(Battle battle, int x, int y) {
        Player player = battle.whoseTurn();
        for (int i = x - 2; i <= x; i++) {
            for (int j = y - 2; j <= y; j++) {
                if (i > 4 || y > 8 || i < 0 || j < 0)
                    continue;
                Cell cell = battle.getBoard().getCells()[i][j];
                if (cell == null)
                    continue;
                if (cell.getCard() == null || cell.getCard().getUserName() == null)
                    continue;
                if (cell.getCard().getUserName().equals(player.getUserName()))
                    return true;
            }
        }
        return false;
    }
}
