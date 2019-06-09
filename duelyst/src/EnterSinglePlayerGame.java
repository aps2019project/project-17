import Appearance.FontAppearance;
import controller.GameController;
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

public class EnterSinglePlayerGame {
    private static Stage firstWindow = new Stage();
    private static Text customMode = new Text("Custom Mode");
    private static Text storyMode = new Text("Story Mode");
    private static Text modeChoose = new Text("select which type of Single Play you want to play");
    private static VBox root = new VBox();
    private static Scene scene = new Scene(root);

    static {
        try {
            Rectangle rectangle = new Rectangle(210, 60);
            Rectangle rectangle1 = new Rectangle(210, 60);
            rectangle.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            rectangle1.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            StackPane stackPane1 = new StackPane(rectangle, customMode);
            StackPane stackPane2 = new StackPane(rectangle1, storyMode);
            HBox hBox = new HBox(stackPane1, stackPane2);
            hBox.setSpacing(100);
            root.setSpacing(65);
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
        firstWindow.setMinHeight(Main.HEIGHT_OF_WINDOW / 2.5);
        customMode.setFont(FontAppearance.FONT_BUTTON);
        storyMode.setFont(FontAppearance.FONT_BUTTON);
        modeChoose.setFont(FontAppearance.FONT_BUTTON);
        modeChoose.setFill(Color.WHITE);
        customMode.setFill(Color.WHITE);
        storyMode.setFill(Color.WHITE);
        firstWindow.setScene(scene);
    }

    static {
        try {
            Image image = new Image(new FileInputStream("background0.jpg"));
            BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(bgImage);
            root.setBackground(background);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        storyMode.setOnMouseClicked(e -> {
            GameController.initializeAIStory();
            SelectModeStorySingle.disPlay();
            firstWindow.close();
        });
    }

    public static void disPlay() {
        firstWindow.show();
    }
}
