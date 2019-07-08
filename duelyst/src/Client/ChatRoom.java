package Client;

import Appearance.FontAppearance;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ChatRoom {

    private Stage stage = new Stage();
    private VBox chat = new VBox();
    private Scene chatRoomScene;
    private static VBox messagesVBox = new VBox();
    private TextField textField = new TextField();
    private Rectangle sendButton = new Rectangle(100, 20);
    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    private final int HEIGHT_OF_BUTTON = 40;
    private final int WIDTH_OF_BUTTON = 80;

    public ChatRoom() {
        locateAndAdd();
    }

    private void locateAndAdd() {
        chatRoomScene = new Scene(chat, WIDTH, HEIGHT);
        textField.setMinWidth(WIDTH - WIDTH_OF_BUTTON);
        textField.setMinHeight(HEIGHT_OF_BUTTON);
        sendButton.setWidth(WIDTH_OF_BUTTON);
        sendButton.setHeight(HEIGHT_OF_BUTTON);
        messagesVBox.setMinWidth(WIDTH);
        messagesVBox.setMinHeight(HEIGHT - HEIGHT_OF_BUTTON * 1.5);
        try {
            sendButton.setFill(new ImagePattern(new Image(new FileInputStream("send.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HBox hBox = new HBox(textField, sendButton);
        chat.getChildren().addAll(messagesVBox, hBox);
        stage.setScene(chatRoomScene);
        chatRoomScene.setOnKeyPressed(event -> {
            Message message = new Message("message " + textField.getText().trim());
            textField.clear();
            Client.send(message);
        });
        sendButton.setOnMouseClicked(event -> {
            Message message = new Message("message " + textField.getText().trim());
            textField.clear();
            Client.send(message);
        });
        stage.showAndWait();
    }

    public static void updateScrollPane() {
        Platform.runLater(() -> {
            for (ChatDetail chatDetail : Client.getChatDetails()) {
                Label userName = new Label(chatDetail.getSender() + " : ");
                Text text = new Text(chatDetail.getMessage());
                text.setFont(FontAppearance.FONT_SHOW_CARD_DATA);
                userName.setFont(FontAppearance.FONT_SHOW_CARD_DATA);
                HBox hBox = new HBox(userName, text);
                messagesVBox.getChildren().add(hBox);
            }
            Client.getChatDetails().clear();
        });
    }

}
