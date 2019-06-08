import Appearance.ColorAppearance;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellAppearance {

    private Rectangle cellRectangle;

    public CellAppearance() {
        cellRectangle = new Rectangle(Main.WIDTH_OF_WINDOW / 22, Main.HEIGHT_OF_WINDOW / 12);
        cellRectangle.setArcWidth(20);
        cellRectangle.setArcHeight(20);
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
        cellRectangle.setOnMouseClicked(event -> light());
        cellRectangle.setOnMouseExited(event -> dark());
        cellRectangle.setOnMouseEntered(event -> light());
    }


}
