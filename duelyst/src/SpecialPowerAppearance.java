import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SpecialPowerAppearance {

    private boolean isSelected;
    private Rectangle specialPower = new Rectangle(Main.WIDTH_OF_WINDOW / 15, Main.HEIGHT_OF_WINDOW / 9);
    private Group root;

    public SpecialPowerAppearance(Group root) {
        this.isSelected = false;
        this.root = root;
        locateAndAdd();
        eventHandler();
    }

    private void locateAndAdd() {
        try {
            specialPower.setFill(new ImagePattern(new Image(new FileInputStream("special.gif"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        specialPower.setOpacity(0.35);
        specialPower.setArcWidth(20);
        specialPower.setArcHeight(20);
        specialPower.setLayoutX(Main.WIDTH_OF_WINDOW * 8.3 / 10);
        specialPower.setLayoutY(Main.HEIGHT_OF_WINDOW * 6 / 10);
        root.getChildren().add(specialPower);
    }

    public void setSelectedFalse() {
        this.isSelected = false;
        if (specialPower.getOpacity() == 1) {
            specialPower.setOpacity(0.35);
        }
    }

    private void eventHandler() {

        specialPower.setOnMouseClicked(event -> {
            if (!isSelected) {
                isSelected = true;
                specialPower.setOpacity(1);
            } else {
                isSelected = false;
                specialPower.setOpacity(0.35);
            }
            BattleAppearance.getCurrentBattleAppearance().getItemAppearance().setSelectedItemNull();
            BattleAppearance.getCurrentBattleAppearance().getHandAppearance().setSelectedCardNull();

        });
    }

    public boolean isSelected() {
        return isSelected;
    }
}
