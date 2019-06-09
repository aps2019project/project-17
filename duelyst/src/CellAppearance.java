import Appearance.ColorAppearance;
import GameGround.Battle;
import GameGround.Cell;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellAppearance {

    private Rectangle cellRectangle;
    private Cell cell;

    public CellAppearance(Cell cell) {
        cellRectangle = new Rectangle(Main.WIDTH_OF_WINDOW / 22, Main.HEIGHT_OF_WINDOW / 12);
        cellRectangle.setArcWidth(20);
        cellRectangle.setArcHeight(20);
        this.cell = cell;
        dark();
        fill();
    }

    private void changeLight(double value) {
        cellRectangle.setOpacity(value);
    }

    private void fill() {
        cellRectangle.setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
        cellRectangle.setFill(ColorAppearance.COLOR_CELL);
    }

    public void light() {// TODO: 6/8/2019
        changeLight(0.7);
//        cellRectangle.setFill(ColorAppearance.COLOR_CELL_CLICKED);
    }

    public void insertCard() {
        changeLight(0.9);
        cellRectangle.setFill(ColorAppearance.COLOR_CELL_CLICKED);
    }

    public void dark() {
        changeLight(0.3);
//        cellRectangle.setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
//        cellRectangle.setFill(ColorAppearance.COLOR_CELL);
    }

    public void add(Group group) {// TODO: 6/8/2019
        group.getChildren().addAll(cellRectangle);
    }

    public Rectangle getCellRectangle() {
        return cellRectangle;
    }

    public void handleEvents() {
        cellRectangle.setOnMouseClicked(event -> {
            insertCard();
            if (BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCard() != null) {
                cellRectangle.setFill(BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCardIcon().getFill());
            }
        });
        cellRectangle.setOnMouseExited(event -> dark());
        cellRectangle.setOnMouseEntered(event -> light());
    }


}
