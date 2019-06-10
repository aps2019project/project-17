

import Appearance.FontAppearance;
import CardCollections.Deck;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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

import static Data.MODE.*;

public class CustomDeckWindow {

    private static Deck selectedDeck;
    private static Stage firstWindow = new Stage();
    private static Text showDecks = new Text("Show My Decks");
    private static Text enterBattle = new Text("Enter Battle");
    private static TextField numOfFlags = new TextField();
    private static VBox root = new VBox();
    private static Scene scene = new Scene(root);


    static {
        try {
            Rectangle rectangle = new Rectangle(300, 60);
            Rectangle rectangle1 = new Rectangle(300, 60);
            rectangle.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            rectangle1.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            StackPane stackPane1 = new StackPane(rectangle, showDecks);
            StackPane stackPane2 = new StackPane(rectangle1, enterBattle);
            root.setSpacing(25);
            root.setAlignment(Pos.CENTER);
            root.getChildren().addAll(stackPane1, stackPane2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        firstWindow.initModality(Modality.APPLICATION_MODAL);
        firstWindow.setMinWidth(Main.WIDTH_OF_WINDOW / 2.5);
        firstWindow.setMinHeight(Main.HEIGHT_OF_WINDOW / 2.5);
        showDecks.setFont(FontAppearance.FONT_BUTTON);
        enterBattle.setFont(FontAppearance.FONT_BUTTON);
        showDecks.setFill(Color.WHITE);
        enterBattle.setFill(Color.WHITE);
        firstWindow.setScene(scene);

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

    static {
        showDecks.setOnMouseClicked(e -> {
            new CustomDeckTable();
        });
        enterBattle.setOnMouseClicked(e -> {
            closeWindow();

            new BattleAppearance();
        });

    }

    public static void disPlay() {
        firstWindow.showAndWait();
    }

    private static void closeWindow() {
        firstWindow.close();
    }

    public static void setSelectedDeck(Deck selectedDeck) {
        CustomDeckWindow.selectedDeck = selectedDeck;
    }
}
