import Appearance.ColorAppearance;
import Data.Account;
import InstanceMaker.CardMaker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class MainMenu {

    private VBox root = new VBox();
    private Text battleTxt = new Text("Battle");
    private Text shopTxt = new Text("Shop");
    private Text collectionTxt = new Text("Collection");
    private Text logoutText = new Text("Logout");
    private Text exitTxt = new Text("Exit");
    private Scene sceneMainMenu;
    private ImageView imageViewBG;

    MainMenu() {
        setBackGround();
        locate();
        setFont();
        setMouse();
        display();
        Account.getLoginUser().getCollection().setMainDeck(Account.getLoginUser().getCollection().getDecks().get(0).getName());
        Account.getLoginUser().getCollection().getMainDeck().setHero(CardMaker.getHeroes()[5]);
    }

    private void setBackGround() {
        sceneMainMenu = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
        try {
            Image image = new Image(new FileInputStream("bg7.jpg"));
            imageViewBG = new ImageView(image);
            imageViewBG.fitWidthProperty().bind(sceneMainMenu.widthProperty());
            imageViewBG.fitHeightProperty().bind(sceneMainMenu.heightProperty());
            root.getChildren().add(imageViewBG);
            root.getChildren().addAll(battleTxt, shopTxt, collectionTxt, logoutText, exitTxt);
            root.setAlignment(Pos.BASELINE_LEFT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void locate() {
        VBox.setMargin(imageViewBG, new Insets(-Main.HEIGHT_OF_WINDOW / 83.4, 0, -Main.HEIGHT_OF_WINDOW / 83.4, 0));
        VBox.setMargin(battleTxt, new Insets(-Main.WIDTH_OF_WINDOW / 4.15, 0, -Main.HEIGHT_OF_WINDOW / 4.15, Main.WIDTH_OF_WINDOW / 4.5));
        VBox.setMargin(shopTxt, new Insets(Main.HEIGHT_OF_WINDOW / 83.4, 0, Main.HEIGHT_OF_WINDOW / 83.4, Main.WIDTH_OF_WINDOW / 4.5));
        VBox.setMargin(collectionTxt, new Insets(Main.HEIGHT_OF_WINDOW / 83.4, 0, Main.HEIGHT_OF_WINDOW / 83.4, Main.WIDTH_OF_WINDOW / 4.5));
        VBox.setMargin(logoutText, new Insets(Main.HEIGHT_OF_WINDOW / 83.4, 0, Main.HEIGHT_OF_WINDOW / 83.4, Main.WIDTH_OF_WINDOW / 4.5));
        VBox.setMargin(logoutText, new Insets(Main.HEIGHT_OF_WINDOW / 83.4, 0, Main.HEIGHT_OF_WINDOW / 83.4, Main.WIDTH_OF_WINDOW / 4.5));
        VBox.setMargin(exitTxt, new Insets(Main.HEIGHT_OF_WINDOW / 83.4, 0, Main.HEIGHT_OF_WINDOW / 83.4, Main.WIDTH_OF_WINDOW / 4.54));
    }

    private void setFont() {
        Font font = Font.font("Phosphate", 30);
        battleTxt.setFont(font);
        shopTxt.setFont(font);
        collectionTxt.setFont(font);
        logoutText.setFont(font);
        exitTxt.setFont(font);
        battleTxt.setFill(Color.WHITE);
        shopTxt.setFill(Color.WHITE);
        collectionTxt.setFill(Color.WHITE);
        logoutText.setFill(Color.WHITE);
        exitTxt.setFill(Color.WHITE);
    }

    private void setMouse() {
        try {
            sceneMainMenu.setCursor(new ImageCursor(new Image(new FileInputStream("mouse_icon.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void display() {
        Main.getWindow().setScene(sceneMainMenu);
        handleEvents();
    }


    private void setMouseHandler() {
        battleTxt.setOnMouseEntered(e -> battleTxt.setFill(ColorAppearance.CURRENT_MENU_BUTTON));
        battleTxt.setOnMouseExited(e -> battleTxt.setFill(Color.WHITE));

        shopTxt.setOnMouseEntered(e -> shopTxt.setFill(ColorAppearance.CURRENT_MENU_BUTTON));
        shopTxt.setOnMouseExited(e -> shopTxt.setFill(Color.WHITE));

        collectionTxt.setOnMouseEntered(e -> collectionTxt.setFill(ColorAppearance.CURRENT_MENU_BUTTON));
        collectionTxt.setOnMouseExited(e -> collectionTxt.setFill(Color.WHITE));

        logoutText.setOnMouseEntered(e -> logoutText.setFill(ColorAppearance.CURRENT_MENU_BUTTON));
        logoutText.setOnMouseExited(e -> logoutText.setFill(Color.WHITE));

        exitTxt.setOnMouseEntered(e -> exitTxt.setFill(ColorAppearance.CURRENT_MENU_BUTTON));
        exitTxt.setOnMouseExited(e -> exitTxt.setFill(Color.WHITE));
    }

    private void handleEvents() {
        setMouseHandler();
        sceneMainMenu.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                Main.emptyFields();
                Main.backToMainMenu();
                Account.logout();
            }
        });

        logoutText.setOnMouseClicked(e -> {
            Account.logout();
            Main.emptyFields();
            Main.backToMainMenu();
        });

        shopTxt.setOnMouseClicked(e -> new ShopAppearance());

        exitTxt.setOnMouseClicked(e -> Main.getWindow().close());

        collectionTxt.setOnMouseClicked(e -> new CollectionAppearance());

        battleTxt.setOnMouseClicked(e -> {
            if (!Account.getLoginUser().getPlayer().isPlayerReadyForBattle())
                CheckValidationDeckAppearance.disPlay();
            else StartingBattleAppearance.disPlay();
        });
    }
}
