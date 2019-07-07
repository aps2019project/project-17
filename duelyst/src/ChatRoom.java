import Appearance.FontAppearance;
import Client.ChatDetail;
import Client.Client;
import Client.Message;
import javafx.beans.Observable;
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
    private ScrollPane scrollPane = new ScrollPane();
    private TextField textField = new TextField();
    private Rectangle sendButton = new Rectangle(100, 20);
    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    private final int HEIGHT_OF_BUTTON = 40;
    private final int WIDTH_OF_BUTTON = 80;
    private HBox hBox;

    public ChatRoom() {
        locateAndAdd();
    }

    private void locateAndAdd() {
        chatRoomScene = new Scene(chat, WIDTH, HEIGHT);
        textField.setMinWidth(WIDTH - WIDTH_OF_BUTTON);
        textField.setMinHeight(HEIGHT_OF_BUTTON);
        sendButton.setWidth(WIDTH_OF_BUTTON);
        sendButton.setHeight(HEIGHT_OF_BUTTON);
        scrollPane.setMinViewportWidth(WIDTH);
        scrollPane.setMinViewportHeight(HEIGHT - HEIGHT_OF_BUTTON * 1.5);
        try {
            sendButton.setFill(new ImagePattern(new Image(new FileInputStream("send.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        hBox = new HBox(textField, sendButton);
        chat.getChildren().addAll(scrollPane, hBox);
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
        new Thread(this::updateScrollPane);
        stage.showAndWait();
    }

    private void updateScrollPane() {
        while (true) {
            try {
                ChatDetail chatDetail = Client.getChatDetails().take();
                Label userName = new Label(chatDetail.getSender());
                Text text = new Text(chatDetail.getMessage());
                text.setFont(FontAppearance.FONT_SHOW_CARD_DATA);
                userName.setFont(FontAppearance.FONT_SHOW_CARD_DATA);
                HBox hBox = new HBox(userName, text);
                scrollPane.setContent(hBox);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
