package GameGround;

public class Board {
    // Named-constants for the dimensions
    private final int rows=5;
    private final int cols=9;
    private Cell[][] cells;// a board composes of rows-by-cols GameGround.Cell instances

    public Board(){
        this.cells = new Cell[rows][cols];  // allocate the array
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                cells[row][col] = new Cell(row, col); // allocate element of the array
            }
        }
    }
}
