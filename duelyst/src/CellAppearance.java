import Appearance.ColorAppearance;
import GameGround.Battle;
import GameGround.Cell;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class CellAppearance {

    private Rectangle cellRectangle;
    private Cell cell;

    public CellAppearance(Cell cell) {
        cellRectangle = new Rectangle(Main.WIDTH_OF_WINDOW / 17, Main.HEIGHT_OF_WINDOW / 10);
        cellRectangle.setArcWidth(10);
        cellRectangle.setArcHeight(10);
        this.cell = cell;
        fill();
        eventHandle();
    }

    private void fill() {
        cellRectangle.setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
        setLightCell();
    }

    private void eventHandle() {
        cellRectangle.setOnMouseEntered(e -> {
            if (BattleAppearance.getCurrentBattleAppearance().getSelectedCell() != null)
                return;
            if (cell.getCard() != null || cell.hasFlag() || cell.getItem() != null)
                return;
            cellRectangle.setOpacity(0.75);
        });
        cellRectangle.setOnMouseExited(e -> {
            if (BattleAppearance.getCurrentBattleAppearance().getSelectedCell() != null) {
                return;
            }
            if (cell.getCard() != null || cell.hasFlag() || cell.getItem() != null)
                return;
            cellRectangle.setOpacity(0.2);
        });
    }

    public void add(Group group) {// TODO: 6/8/2019
        group.getChildren().addAll(cellRectangle);
    }

    public Rectangle getCellRectangle() {
        return cellRectangle;
    }

    public void handleEvents() {
        cellRectangle.setOnMouseClicked(event -> {
            if (BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCard() != null) {
                // in this case the action equals inserting card from hand in to the ground!
                try {
                    String result = Battle.getCurrentBattle().insertingCardFromHand(BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCard().getName(), cell.getRow() + 1, cell.getCol() + 1);
                    System.out.println(BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCard().getName());
                    System.out.println(result);

                    if (result.contains("successfully")) {
                        // insert successfully done!
                        if (result.contains("capture the flag"))
                            this.cellRectangle.setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
                        // destination has flag!
                        if (cell.getItem() != null)
                            this.cellRectangle.setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
                        // destination has item

                        // should fill with current photo
                        cellRectangle.setFill(new ImagePattern(new Image(new FileInputStream("gifs/gif".concat(Integer.toString(new Random().nextInt(10)).concat(".gif"))))));
                        // set hand new!
                        BattleAppearance.getCurrentBattleAppearance().getHandAppearance().insert();
                        // set color of cells
                        setLightCell();
                    } else ErrorOnBattle.display(result);
                    // set mana icons
                    BattleAppearance.getCurrentBattleAppearance().setManaIconImageLights();
                    // selected card should set Null at the end!
                    BattleAppearance.getCurrentBattleAppearance().getHandAppearance().setSelectedCard(null);
                    BattleAppearance.getCurrentBattleAppearance().getHandAppearance().setSelectedCardIcon(null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                if (BattleAppearance.getCurrentBattleAppearance().getSelectedCell() != null) {
                    Cell cell = BattleAppearance.getCurrentBattleAppearance().getSelectedCell();
                    // in this case we probably want to move or attack
                    if (this.cell.getCard() != null) {
                        // in this case we attack!
                        int x = BattleAppearance.getCurrentBattleAppearance().getSelectedCell().getRow();
                        int y = BattleAppearance.getCurrentBattleAppearance().getSelectedCell().getCol();
                        try {
                            this.cellRectangle.setFill(new ImagePattern(new Image(new FileInputStream("gifs/gif".concat(Integer.toString(new Random().nextInt(10)).concat(".gif"))))));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        BattleAppearance.getCurrentBattleAppearance().setSelectedCell(null);
                        BattleAppearance.getCurrentBattleAppearance().getBoard()[x][y].cellRectangle.setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
                    } else {
                        // in this case we move
                        if (Battle.getCurrentBattle().getSelectedCard() == null)
                            return;
                        String result = Battle.getCurrentBattle().movingCard(this.cell.getRow() + 1, this.cell.getCol() + 1);
                        System.out.println(result);
                        if (result.contains("successfully")) {
                            // in this case move has successfully done!
                            if (result.contains("captured the flag")){
                                // in this case a flag has captured
                                this.cellRectangle.setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
                            }
                            changeTargetsLight(BattleAppearance.getCurrentBattleAppearance().getSelectedCell(), 0.2, ColorAppearance.COLOR_RECTANGLE_BOARD);
                        }
                        else ErrorOnBattle.display(result);
                            // in this case move has failed!
                    }
                    BattleAppearance.getCurrentBattleAppearance().setSelectedCell(null);
                    Battle.getCurrentBattle().setSelectedCardNull();
                } else {
                    // in this case we select a cell! ( set selected cell in the other way )
                    if (this.cell.getCard() != null) {
                        BattleAppearance.getCurrentBattleAppearance().setSelectedCell(this.cell);
                        String result = Battle.getCurrentBattle().selectCardOrItem(cell.getCard().getId());
                        System.out.println(result);
                        if (result.contains("successfully selected"))
                            changeTargetsLight(BattleAppearance.getCurrentBattleAppearance().getSelectedCell(), 0.75, ColorAppearance.COLOR_CELL_CLICKED);
                        else
                            ErrorOnBattle.display(result);
                    }
                }
            }
        });
    }

    private void setLightCell() {
        if (cell.hasFlag() || cell.getCard() != null)
            cellRectangle.setOpacity(1);
        else cellRectangle.setOpacity(0.2);
    }

    private void changeTargetsLight(Cell cell, double value, Color color) {
        int x = cell.getRow() - 1;
        int y = cell.getCol() - 1;

        for (int i = x; i <= x + 2; i++) {
            for (int j = y; j <= y + 2; j++) {
                if ((i == x + 1 && j == y + 1))
                    continue;
                if (i < 0 || i > 4 || j < 0 || j > 8)
                    continue;
                if (Battle.getCurrentBattle().getBoard().getCells()[i][j].getCard() != null || Battle.getCurrentBattle().getBoard().getCells()[i][j].hasFlag()) {
                    BattleAppearance.getCurrentBattleAppearance().getBoardBackGround()[i][j].setFill(color);
                    continue;
                }
                BattleAppearance.getCurrentBattleAppearance().getBoard()[i][j].cellRectangle.setFill(color);
                BattleAppearance.getCurrentBattleAppearance().getBoard()[i][j].cellRectangle.setOpacity(value);
            }
        }
    }

    // TODO: 2019-06-11 a function that fill for items and flags!
}
