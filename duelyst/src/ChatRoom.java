import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ChatRoom {

    private Stage stage = new Stage();
    private VBox chat;
    private Scene chatRoomScene;
    private ScrollPane scrollPane = new ScrollPane();
    private TextField textField = new TextField();
    private Rectangle sendButton = new Rectangle(100, 20);

    public ChatRoom() {
        locateAndAdd();
    }

    private void locateAndAdd() {
        chatRoomScene = new Scene(chat, 500, 500);
        textField.setMinWidth(400);
        textField.setMinHeight(20);
        try {
            sendButton.setFill(new ImagePattern(new Image(new FileInputStream("send.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sendButton.setWidth(100);
        sendButton.setHeight(20);
        HBox hBox = new HBox(textField, sendButton);
        chat = new VBox(scrollPane, hBox);
        VBox.setMargin(chat, new Insets(480, 0, 480,0 ));
        stage.setScene(chatRoomScene);
        stage.showAndWait();


    }


}
