import Appearance.FontAppearance;
import GameGround.Battle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EndGameAppearance {
    private Stage endGameWindow = new Stage();
    private Text report = new Text(Battle.getSituationOfGame());
    private Group root = new Group();
    private Scene scene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);

    public EndGameAppearance() {
        setBackground();
        addReport();
        disPlay();
    }

    private void setBackground() {
        Image image;
        try {
            image = new Image(new FileInputStream("end_game.jpg"));
            ImageView imageOfBackGround = new ImageView(image);
            imageOfBackGround.fitWidthProperty().bind(scene.widthProperty());
            imageOfBackGround.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(imageOfBackGround);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addReport() {
        report.setFont(FontAppearance.FONT_ENDING_GAME);
        report.setFill(Color.WHITE);
        report.relocate(Main.WIDTH_OF_WINDOW / 7, Main.HEIGHT_OF_WINDOW * 9 / 11);
        root.getChildren().add(report);
    }

    private void toClose() {
        endGameWindow.setScene(scene);
        endGameWindow.setOnCloseRequest(e -> {
            new MainMenu();
            endGameWindow.close();
        });
    }

    private void disPlay() {
        toClose();
        endGameWindow.showAndWait();
    }
}
