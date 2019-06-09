import Appearance.ColorAppearance;
import GameGround.Battle;
import GameGround.Cell;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class CellAppearance {

    private Rectangle cellRectangle;
    private Cell cell;

    public CellAppearance(Cell cell) {
        cellRectangle = new Rectangle(Main.WIDTH_OF_WINDOW / 19, Main.HEIGHT_OF_WINDOW / 10);
        cellRectangle.setArcWidth(10);
        cellRectangle.setArcHeight(10);
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

    public void light() {
        changeLight(0.7);
    }

    public void insertCard() {
        changeLight(0.9);
    }

    public void dark() {
        changeLight(0.3);
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
                System.out.println(Battle.getCurrentBattle().insertingCardFromHand(BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCard().getName(), cell.getRow() + 1, cell.getCol() + 1));
                BattleAppearance.getCurrentBattleAppearance().getHandAppearance().reLoad();
            }
        });
        cellRectangle.setOnMouseExited(event -> dark());
        cellRectangle.setOnMouseEntered(event -> light());
    }


}
