import Appearance.ColorAppearance;
import Appearance.FontAppearance;
import javafx.geometry.Insets;
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

public class StartingBattleAppearance {
    private static Stage firstWindow = new Stage();
    private static Text singlePlayer = new Text("Single Player");
    private static Text multiPlayer = new Text("multi Player");
    private static Text modeChoose = new Text("select which type of battle you want to play");
    private static VBox root = new VBox();
    private static Scene scene = new Scene(root);
    private static Rectangle rectangle = new Rectangle(210, 60);
    private static Rectangle rectangle1 = new Rectangle(210, 60);

    static {
        try {
            ;
            rectangle.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            rectangle1.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            StackPane stackPane1 = new StackPane(rectangle, singlePlayer);
            StackPane stackPane2 = new StackPane(rectangle1, multiPlayer);
            HBox hBox = new HBox(stackPane1, stackPane2);
            hBox.setSpacing(100);
            root.setSpacing(30);
            hBox.setAlignment(Pos.CENTER);
            root.setAlignment(Pos.CENTER);
            root.getChildren().addAll(modeChoose, hBox);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        firstWindow.initModality(Modality.APPLICATION_MODAL);
        firstWindow.setMinWidth(Main.WIDTH_OF_WINDOW / 2.5);
        firstWindow.setMinHeight(Main.HEIGHT_OF_WINDOW / 5);
        singlePlayer.setFont(FontAppearance.FONT_BUTTON);
        multiPlayer.setFont(FontAppearance.FONT_BUTTON);
        modeChoose.setFont(FontAppearance.FONT_BUTTON);
        singlePlayer.setFill(Color.WHITE);
        multiPlayer.setFill(Color.WHITE);
        firstWindow.setScene(scene);
    }

    static {
        BackgroundFill bgFill = new BackgroundFill(ColorAppearance.COLOR_TITLES_OF_SHOP, CornerRadii.EMPTY, Insets.EMPTY);
        root.setBackground(new Background(bgFill));
    }

    static {
        singlePlayer.setOnMouseClicked(e -> {
            action();
        });
        rectangle.setOnMouseClicked(e -> {
            action();
        });
    }

    private static void action() {
        EnterSinglePlayerGame.disPlay();
        closeWindow();
    }

    private static void closeWindow() {
        firstWindow.close();
    }

    public static void disPlay() {
        firstWindow.showAndWait();
    }
}
