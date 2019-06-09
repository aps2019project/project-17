import Appearance.ColorAppearance;
import Appearance.MinionAppearance;
import GameGround.Battle;
import GameGround.Cell;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CellAppearance {

    private Rectangle cellRectangle;
    private Cell cell;
    private double valueExit = 0.6;
    private double valueEnter = 0.1;

    public CellAppearance(Cell cell) {
        cellRectangle = new Rectangle(Main.WIDTH_OF_WINDOW / 19, Main.HEIGHT_OF_WINDOW / 10);
        cellRectangle.setArcWidth(10);
        cellRectangle.setArcHeight(10);
        this.cell = cell;
        fill();
        eventHandle();
    }

    private void fill() {
        cellRectangle.setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
        cellRectangle.setOpacity(valueEnter);
    }

    private void eventHandle() {
        cellRectangle.setOnMouseEntered(e -> cellRectangle.setOpacity(valueExit));
        cellRectangle.setOnMouseExited(e -> cellRectangle.setOpacity(valueEnter));
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
                        valueExit = 1;
                        valueEnter = 1;
                        cellRectangle.setOpacity(valueExit);
                    } else ErrorOnBattle.display(result);
                    BattleAppearance.getCurrentBattleAppearance().setManaIconImageLights();
                    BattleAppearance.getCurrentBattleAppearance().getHandAppearance().setSelectedCard(null);
                    BattleAppearance.getCurrentBattleAppearance().getHandAppearance().setSelectedCardIcon(null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}