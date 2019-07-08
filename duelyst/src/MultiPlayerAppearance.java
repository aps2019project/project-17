import Appearance.FontAppearance;
import Client.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MultiPlayerAppearance {
    private Group root = new Group();
    private Text text = new Text("MULTI PLAYER MODE\nwaiting for opponent ...");
    private Scene scene;
    private ImageView imageViewBG;

    public MultiPlayerAppearance() {
        initBackGround();
        display();
    }

    private void initBackGround() {
        scene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
        try {
            Image image = new Image(new FileInputStream("bg7.jpg"));
            imageViewBG = new ImageView(image);
            imageViewBG.fitWidthProperty().bind(scene.widthProperty());
            imageViewBG.fitHeightProperty().bind(scene.heightProperty());
            text.relocate(500, 500);
            root.getChildren().add(imageViewBG);
            root.getChildren().add(text);
            text.setFont(FontAppearance.FONT_MULTIPLAYER);
            text.setFill(Color.WHITE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void display() {
        Main.getWindow().setScene(scene);
        handleEvents();
    }

    private void handleEvents() {
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                Client.send(new Message("multi player"));
                Object object = Client.get();
                new MainMenu();
            }
        });
    }
}
