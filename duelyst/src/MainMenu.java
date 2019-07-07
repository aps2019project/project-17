import Appearance.ColorAppearance;
import Cards.Minion;
import Client.*;
import Data.Account;
import Data.Save;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
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
    private Text customCardTxt = new Text("Custom Card");
    private Text logoutText = new Text("Logout");
    private Text exitTxt = new Text("Exit");
    private Scene sceneMainMenu;
    private ImageView imageViewBG;
    private ImageView save;
    private VBox saveBox;

    MainMenu() {
        initializeSaveVBox();
        setBackGround();
        locate();
        setFont();
        setMouse();
        display();
    }

    private void initializeSaveVBox() {
        try {
            Image saveImage = new Image(new FileInputStream("save.png"));
            save = new ImageView(saveImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        save.setOpacity(0.9);
        save.setFitWidth(70);
        save.setFitHeight(70);
        Text saveTxt = new Text("Save");
        saveTxt.setFont(Font.font("Phosphate", 10));
        saveTxt.setFill(Color.WHITE);
        saveBox = new VBox(save, saveTxt);
        saveBox.setAlignment(Pos.CENTER);
        saveBox.setSpacing(Main.HEIGHT_OF_WINDOW / 200);
        saveBox.setAlignment(Pos.CENTER);
    }

    private void setBackGround() {
        sceneMainMenu = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
        try {
            Image image = new Image(new FileInputStream("bg7.jpg"));
            imageViewBG = new ImageView(image);
            imageViewBG.fitWidthProperty().bind(sceneMainMenu.widthProperty());
            imageViewBG.fitHeightProperty().bind(sceneMainMenu.heightProperty());
            root.getChildren().add(imageViewBG);
            root.getChildren().addAll(battleTxt, shopTxt, collectionTxt, customCardTxt, logoutText, exitTxt, saveBox);
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
        VBox.setMargin(customCardTxt, new Insets(Main.HEIGHT_OF_WINDOW / 83.4, 0, Main.HEIGHT_OF_WINDOW / 83.4, Main.WIDTH_OF_WINDOW / 4.5));
        VBox.setMargin(exitTxt, new Insets(Main.HEIGHT_OF_WINDOW / 83.4, 0, Main.HEIGHT_OF_WINDOW / 83.4, Main.WIDTH_OF_WINDOW / 4.54));
        VBox.setMargin(saveBox, new Insets(Main.HEIGHT_OF_WINDOW / 83.4, 0, Main.HEIGHT_OF_WINDOW / 83.4, Main.WIDTH_OF_WINDOW / 1.1));
    }

    private void setFont() {
        Font font = Font.font("Phosphate", 30);
        battleTxt.setFont(font);
        shopTxt.setFont(font);
        collectionTxt.setFont(font);
        logoutText.setFont(font);
        exitTxt.setFont(font);
        customCardTxt.setFont(font);
        battleTxt.setFill(Color.WHITE);
        shopTxt.setFill(Color.WHITE);
        collectionTxt.setFill(Color.WHITE);
        logoutText.setFill(Color.WHITE);
        exitTxt.setFill(Color.WHITE);
        customCardTxt.setFill(Color.WHITE);
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

        customCardTxt.setOnMouseEntered(e -> customCardTxt.setFill(ColorAppearance.CURRENT_MENU_BUTTON));
        customCardTxt.setOnMouseExited(e -> customCardTxt.setFill(Color.WHITE));

        save.setOnMouseEntered(e -> save.setOpacity(1));
        save.setOnMouseExited(e -> save.setOpacity(0.9));
        save.setOnMouseClicked(event -> Save.saveAccount(Account.getLoginUser()));
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
            Client.send(new Message("logout"));
            Account.logout();
            Main.emptyFields();
            Main.backToMainMenu();
        });

        shopTxt.setOnMouseClicked(e -> {
            new ShopAppearance();
            for (int i = 0; i < Account.getLoginUser().getShop().getCollection().getCards().size(); i++) {
                System.out.println(Account.getLoginUser().getShop().getCollection().getCards().get(i).getName());
            }
        });

        exitTxt.setOnMouseClicked(e -> Main.getWindow().close());

        customCardTxt.setOnMouseClicked(e -> CustomCardPreview.disPlay());

        collectionTxt.setOnMouseClicked(e -> new CollectionAppearance());

        battleTxt.setOnMouseClicked(e -> {
            if (!Account.getLoginUser().getPlayer().isPlayerReadyForBattle())
                CheckValidationDeckAppearance.disPlay();
            else StartingBattleAppearance.disPlay();
        });
    }
}
