import Appearance.FontAppearance;
import Data.Account;
import GameGround.SinglePlayerModes;
import controller.GameController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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

public class CustomCardPreview {
    private static Stage firstWindow = new Stage();
    private static Text title = new Text("Select Type Of Card You Want To Build");
    private static Text hero = new Text("HERO");
    private static Text minion = new Text("MINION");
    private static Text item = new Text("ITEM");
    private static Text spell = new Text("SPELL");
    private static Text buff = new Text("BUFF");
    private static VBox root = new VBox();
    private static Scene scene = new Scene(root);

    static {
        try {
            Rectangle rectangle = new Rectangle(300, 60);
            Rectangle rectangle1 = new Rectangle(300, 60);
            Rectangle rectangle2 = new Rectangle(300, 60);
            Rectangle rectangle3 = new Rectangle(300, 60);
            Rectangle rectangle4 = new Rectangle(300, 60);
            rectangle.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            rectangle1.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            rectangle2.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            rectangle3.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            rectangle4.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            StackPane stackPane1 = new StackPane(rectangle, hero);
            StackPane stackPane2 = new StackPane(rectangle1, minion);
            StackPane stackPane3 = new StackPane(rectangle2, item);
            StackPane stackPane4 = new StackPane(rectangle3, spell);
            StackPane stackPane5 = new StackPane(rectangle4, buff);
            // TODO: 6/11/2019 will be completed
            rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
            rectangle1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
            rectangle2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
            rectangle3.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    new CustomSpellWindow();
                }
            });
            rectangle4.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
            root.setSpacing(25);
            root.setAlignment(Pos.CENTER);
            root.getChildren().addAll(title, stackPane1, stackPane2, stackPane4,stackPane3,stackPane5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        hero.setFont(FontAppearance.FONT_BUTTON);
        minion.setFont(FontAppearance.FONT_BUTTON);
        item.setFont(FontAppearance.FONT_BUTTON);
        spell.setFont(FontAppearance.FONT_BUTTON);
        buff.setFont(FontAppearance.FONT_BUTTON);
        hero.setFill(Color.WHITE);
        minion.setFill(Color.WHITE);
        item.setFill(Color.WHITE);
        spell.setFill(Color.WHITE);
        buff.setFill(Color.WHITE);
        title.setFont(FontAppearance.FONT_BUTTON);
        title.setFill(Color.WHITE);
        firstWindow.initModality(Modality.APPLICATION_MODAL);
        firstWindow.setMinWidth(Main.WIDTH_OF_WINDOW / 2.5);
        firstWindow.setMinHeight(Main.HEIGHT_OF_WINDOW / 2.5);
        firstWindow.setScene(scene);
    }

    static {
        try {
            Image image = new Image(new FileInputStream("play.jpg"));
            Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
            root.setBackground(background);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        hero.setOnMouseClicked(e -> {

        });
        minion.setOnMouseClicked(e -> {

        });
        item.setOnMouseClicked(e -> {

        });
        spell.setOnMouseClicked(e -> {
            closeWindow();
            new CustomSpellWindow();
        });

        buff.setOnMouseClicked(e -> {

        });
    }

    public static void disPlay() {
        firstWindow.showAndWait();
    }

    private static void closeWindow() {
        firstWindow.close();
    }
}
