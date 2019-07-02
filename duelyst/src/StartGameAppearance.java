import GameGround.Battle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StartGameAppearance {

    private Stage startGameWindow = new Stage();
    private Text playerOneName=new Text(Battle.getCurrentBattle().getPlayerOne().getUserName());
    private Text playerTwoName=new Text(Battle.getCurrentBattle().getPlayerTwo().getUserName());
    private ImageView loading;
    private Group root = new Group();
    private Scene scene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);

    public StartGameAppearance() {
        setBackground();
        setLoading();
        addNames();
        eventHandler();
        disPlay();
    }

    private void setBackground() {
        Image image;
        try {
            image = new Image(new FileInputStream("start.png"));
            ImageView imageOfBackGround = new ImageView(image);
            imageOfBackGround.fitWidthProperty().bind(scene.widthProperty());
            imageOfBackGround.fitHeightProperty().bind(scene.heightProperty());
            root.getChildren().add(imageOfBackGround);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setLoading(){
        Image image;
        try {
            image = new Image(new FileInputStream("loading.gif"));
            loading=new ImageView(image);
            loading.relocate((1.35)*Main.WIDTH_OF_WINDOW/3,Main.HEIGHT_OF_WINDOW*3/4 );
            loading.setFitWidth(scene.getWidth()/10);
            loading.setFitHeight(scene.getHeight()/7);
            loading.setOpacity(0.8);
            root.getChildren().addAll(loading);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addNames(){
        playerOneName.setFont(Font.font("Phosphate",30));
        playerTwoName.setFont(Font.font("Phosphate",30));
        playerOneName.setFill(Color.WHITE);
        playerTwoName.setFill(Color.WHITE);
        playerOneName.relocate(Main.WIDTH_OF_WINDOW/9, 5*Main.HEIGHT_OF_WINDOW/6);
        playerTwoName.relocate(7*Main.WIDTH_OF_WINDOW/10, 5*Main.HEIGHT_OF_WINDOW/6);
        root.getChildren().addAll(playerOneName,playerTwoName);
    }

    private void eventHandler(){
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> startGameWindow.close());
    }

    private void disPlay() {
        startGameWindow.setScene(scene);
        startGameWindow.show();
    }
}
