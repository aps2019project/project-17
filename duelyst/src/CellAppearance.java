import Appearance.ColorAppearance;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellAppearance {

    private Rectangle cell;

    public CellAppearance() {
        cell=new Rectangle(Main.WIDTH_OF_WINDOW / 22, Main.HEIGHT_OF_WINDOW/12);
        cell.setArcWidth(20);
        cell.setArcHeight(20);
        dark();
        fill();
    }

    private void changeLight(double value) {
        cell.setOpacity(value);
    }

    private void fill() {
        cell.setFill(ColorAppearance.COLOR_CELL);
    }

    public void light() {// TODO: 6/8/2019
        changeLight(0.7);
        cell.setFill(ColorAppearance.COLOR_CELL_CLICKED);
    }

    public void dark() {
        changeLight(0.3);
        cell.setFill(ColorAppearance.COLOR_CELL);
    }

    public void add(Group group) {// TODO: 6/8/2019
        group.getChildren().addAll(cell);
    }

    public Rectangle getCell() {
        return cell;
    }

    public void handleEvents(){
        cell.setOnMouseEntered(event -> light());
        cell.setOnMouseExited(event -> dark());
    }
}
