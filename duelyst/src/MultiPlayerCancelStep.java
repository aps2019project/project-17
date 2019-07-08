import Appearance.FontAppearance;
import Client.Client;
import Client.Message;
import com.google.gson.Gson;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MultiPlayerCancelStep {
    private Group root = new Group();
    private Text yes = new Text("YES");
    private Text no = new Text("NO");
    private Scene scene;
    private ImageView imageViewBG;

    public MultiPlayerCancelStep() {
        initBackGround();
        display();
    }

    private void initBackGround() {
        scene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
        try {
            Image image = new Image(new FileInputStream("bg5.jpg"));
            imageViewBG = new ImageView(image);
            imageViewBG.fitWidthProperty().bind(scene.widthProperty());
            imageViewBG.fitHeightProperty().bind(scene.heightProperty());
            yes.relocate(200, 500);
            no.relocate(600, 500);
            root.getChildren().add(imageViewBG);
            root.getChildren().addAll(yes, no);
            yes.setFont(FontAppearance.FONT_MULTIPLAYER);
            yes.setFill(Color.WHITE);
            no.setFont(FontAppearance.FONT_MULTIPLAYER);
            no.setFill(Color.WHITE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void display() {
        Main.getWindow().setScene(scene);
        handleEvents();
    }

    private void handleEvents() {
        yes.setOnMouseClicked(e -> {
            Client.send(new Message("fine multi player"));
            Gson gson = new Gson();
            Object object = Client.get();
            Message message = gson.fromJson(object.toString(), Message.class);
            if (message.getData().equals("ok")) {
                new MainMenu();

            } else {
                new ShopAppearance();
            }
        });

        no.setOnMouseClicked(e -> {
            Client.send(new Message("not fine multi player"));
            new ShopAppearance();
        });
    }
}
