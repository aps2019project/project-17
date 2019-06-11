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
                try {
                    String result = Battle.getCurrentBattle().insertingCardFromHand(BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCard().getName(), cell.getRow() + 1, cell.getCol() + 1);
                    System.out.println(BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCard().getName());
                    System.out.println(result);
                    if (result.contains("successfully")) {
                        cellRectangle.setFill(new ImagePattern(new Image(new FileInputStream("test.png"))));
                        BattleAppearance.getCurrentBattleAppearance().getHandAppearance().insert();
                        setLightCell();
                    } else ErrorOnBattle.display(result);
                    BattleAppearance.getCurrentBattleAppearance().setManaIconImageLights();
                    BattleAppearance.getCurrentBattleAppearance().getHandAppearance().setSelectedCard(null);
                    BattleAppearance.getCurrentBattleAppearance().getHandAppearance().setSelectedCardIcon(null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                if (BattleAppearance.getCurrentBattleAppearance().getSelectedCell() != null) {
                    changeTargetsLight(BattleAppearance.getCurrentBattleAppearance().getSelectedCell(), 0.2, ColorAppearance.COLOR_RECTANGLE_BOARD);
                    BattleAppearance.getCurrentBattleAppearance().setSelectedCell(null);
                } else {
                    BattleAppearance.getCurrentBattleAppearance().setSelectedCell(this.cell);
                    changeTargetsLight(BattleAppearance.getCurrentBattleAppearance().getSelectedCell(), 0.75, ColorAppearance.COLOR_CELL_CLICKED);
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
}
