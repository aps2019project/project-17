import Appearance.FontAppearance;
import Cards.Spell;
import controller.GameController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class CustomSpellWindow {

    private Group root = new Group();
    private Scene customSpellScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
    private TextField name = new TextField();
    private TextField id = new TextField();
    private TextField price = new TextField();
    private TextField manaPoint = new TextField();
    private TextField desc = new TextField();
    private Text nameTxt = new Text("Name");
    private Text idTxt = new Text("ID");
    private Text priceTxt = new Text("Price");
    private Text manaPointTxt = new Text("ManaPoint");
    private Text descTxt = new Text("Description");
    private Rectangle buildButton;
    private Text buildTxt = new Text("Build");

    public CustomSpellWindow() {

        setBackGround();
        setNodesView();
        setBuildButton();
        locateNodes();
        handleEvents();
        disPlay();
    }

    private void setNodesView() {
        nameTxt.setFont(FontAppearance.FONT_ERRORS);
        idTxt.setFont(FontAppearance.FONT_ERRORS);
        priceTxt.setFont(FontAppearance.FONT_ERRORS);
        manaPointTxt.setFont(FontAppearance.FONT_ERRORS);
        descTxt.setFont(FontAppearance.FONT_ERRORS);
        nameTxt.setFill(Color.WHITE);
        idTxt.setFill(Color.WHITE);
        priceTxt.setFill(Color.WHITE);
        manaPointTxt.setFill(Color.WHITE);
        descTxt.setFill(Color.WHITE);
        name.setMinWidth(Main.WIDTH_OF_WINDOW / 7);
        id.setMinWidth(Main.WIDTH_OF_WINDOW / 7);
        price.setMinWidth(Main.WIDTH_OF_WINDOW / 7);
        manaPoint.setMinWidth(Main.WIDTH_OF_WINDOW / 7);
        desc.setMinWidth(Main.WIDTH_OF_WINDOW / 7);
        desc.setPromptText("Optional");
    }

    private void setBackGround() {
        Image image;
        try {
            image = new Image(new FileInputStream("purpleBackGrouund.jpg"));
            ImageView imageOfBackGround = new ImageView(image);
            imageOfBackGround.fitWidthProperty().bind(customSpellScene.widthProperty());
            imageOfBackGround.fitHeightProperty().bind(customSpellScene.heightProperty());
            imageOfBackGround.setOpacity(0.8);
            root.getChildren().add(imageOfBackGround);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setBuildButton() {
        buildButton = new Rectangle(Main.WIDTH_OF_WINDOW / 7.58, Main.HEIGHT_OF_WINDOW / 11.12);
        buildTxt.setFont(FontAppearance.FONT_BUTTON);
        buildTxt.setFill(Color.WHITE);
        try {
            buildButton.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            StackPane stackPane0 = new StackPane(buildButton, buildTxt);
            stackPane0.setLayoutX(Main.WIDTH_OF_WINDOW / 2.3);
            stackPane0.setLayoutY(Main.HEIGHT_OF_WINDOW * 8.5 / 10);
            root.getChildren().addAll(stackPane0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void locateNodes() {
        VBox vBox0 = new VBox(nameTxt, name);
        VBox vBox1 = new VBox(idTxt, id);
        VBox vBox2 = new VBox(priceTxt, price);
        vBox0.setAlignment(Pos.CENTER);
        vBox0.setSpacing(Main.HEIGHT_OF_WINDOW / 51);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(Main.HEIGHT_OF_WINDOW / 50);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setSpacing(Main.HEIGHT_OF_WINDOW / 51);
        HBox hBox = new HBox(vBox0, vBox1, vBox2);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(Main.WIDTH_OF_WINDOW / 6);
        hBox.setLayoutY(Main.HEIGHT_OF_WINDOW / 3);
        hBox.setLayoutX(Main.WIDTH_OF_WINDOW / 8);
        root.getChildren().add(hBox);


        VBox vBox3 = new VBox(manaPointTxt, manaPoint);
        vBox3.setAlignment(Pos.CENTER);
        vBox3.setSpacing(Main.HEIGHT_OF_WINDOW / 50);
        VBox vBox4 = new VBox(descTxt, desc);
        vBox4.setAlignment(Pos.CENTER);
        vBox4.setSpacing(Main.HEIGHT_OF_WINDOW / 50);
        HBox hBox1 = new HBox(vBox3, vBox4);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(Main.WIDTH_OF_WINDOW / 6);
        hBox1.setLayoutY(2 * Main.HEIGHT_OF_WINDOW / 3);
        hBox1.setLayoutX(Main.WIDTH_OF_WINDOW / 3.5);
        root.getChildren().add(hBox1);

    }

    private void handleEvents() {
        buildTxt.setOnMouseClicked(event -> {
            action();
        });

        buildButton.setOnMouseClicked(event -> action());
    }

    private void action() {
        if (checkValid()) {
            String nameInput = name.getText();
            String idInput = id.getText();
            String descInput = desc.getText();
            try {
                int priceInput = Integer.parseInt(price.getText());
                int manaPointInput = Integer.parseInt(manaPoint.getText());
                GameController.saveSpell(new Spell(nameInput, idInput, priceInput, manaPointInput, descInput));
            } catch (Exception e) {
                ErrorOnBattle.display("Please input valid fields!");
            }
            new MainMenu();
        } else {
            ErrorOnBattle.display("Please Fill all fields!");
        }
    }

    private void disPlay() {
        Main.getWindow().setScene(customSpellScene);
    }

    private boolean checkValid() {
        return !this.name.getText().equals("") && !this.price.getText().equals("") && !this.id.getText().equals("") && !this.manaPoint.getText().equals("");
    }
}
