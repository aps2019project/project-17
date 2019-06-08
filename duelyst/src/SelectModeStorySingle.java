import Appearance.FontAppearance;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SelectModeStorySingle {
    private static Stage firstWindow = new Stage();
    private static Text title = new Text("Select  Mode Of Single Game - Story");
    private static Text killHero = new Text("Kill  Hero  Of  Opponent");
    private static Text captureFlag = new Text("Capture  Flag  Of  Ground");
    private static Text holdFlag = new Text("Hold  Flag  Of  Ground");
    private static Text modeChoose = new Text("select which type of Single Play you want to play");
    private static VBox root = new VBox();
    private static Scene scene = new Scene(root);

    static {
        try {
            Rectangle rectangle = new Rectangle(300, 60);
            Rectangle rectangle1 = new Rectangle(300, 60);
            Rectangle rectangle2 = new Rectangle(300, 60);
            rectangle.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            rectangle1.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            rectangle2.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            StackPane stackPane1 = new StackPane(rectangle, killHero);
            StackPane stackPane2 = new StackPane(rectangle1, captureFlag);
            StackPane stackPane3 = new StackPane(rectangle2, holdFlag);
            root.setSpacing(25);
            root.setAlignment(Pos.CENTER);
            root.getChildren().addAll(title, stackPane1, stackPane2, stackPane3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        firstWindow.initModality(Modality.APPLICATION_MODAL);
        firstWindow.setMinWidth(Main.WIDTH_OF_WINDOW / 2.5);
        firstWindow.setMinHeight(Main.HEIGHT_OF_WINDOW / 2.5);
        killHero.setFont(FontAppearance.FONT_BUTTON);
        captureFlag.setFont(FontAppearance.FONT_BUTTON);
        holdFlag.setFont(FontAppearance.FONT_BUTTON);
        modeChoose.setFont(FontAppearance.FONT_BUTTON);
        killHero.setFill(Color.WHITE);
        captureFlag.setFill(Color.WHITE);
        holdFlag.setFill(Color.WHITE);
        firstWindow.setScene(scene);
        title.setFont(FontAppearance.FONT_BUTTON);
    }

    static {
        try {
            Image image = new Image(new FileInputStream("play.jpg"));
            BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(bgImage);
            root.setBackground(background);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void disPlay() {

        firstWindow.showAndWait();
        killHero.setOnMouseClicked(e -> {
            // TODO: single player type!
        });
    }
}
