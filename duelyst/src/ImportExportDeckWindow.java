import Appearance.ColorAppearance;
import Appearance.FontAppearance;
import CardCollections.Deck;
import Cards.Card;
import Cards.Item;
import Data.Account;
import Data.Save;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImportExportDeckWindow {

    private static Stage popOp = new Stage();
    private static Text result = new Text();
    private static TextField deckName = new TextField();
    private static Text exportDeck = new Text("Export this deck");
    private static Text importDeck = new Text("Import this deck");
    private static Text deckNameTxt = new Text("Enter name of Deck  :  ");
    private static Rectangle exportBox = new Rectangle(310, 30);
    private static Rectangle importBox = new Rectangle(310, 30);

    static {
        popOp.setTitle("DECK GENERATOR");
        popOp.initModality(Modality.APPLICATION_MODAL);
        popOp.setMinWidth(Main.WIDTH_OF_WINDOW / 2.5);
        popOp.setMinHeight(Main.HEIGHT_OF_WINDOW / 4.17);
        result.setFont(FontAppearance.FONT_SEARCH_SHOP);
        exportDeck.setFont(FontAppearance.FONT_SEARCH_SHOP);
        importDeck.setFont(FontAppearance.FONT_SEARCH_SHOP);
        exportDeck.setFill(Color.WHITE);
        importDeck.setFill(Color.WHITE);
        result.setFill(Color.GREEN);
        deckName.setPromptText("Deck name");
    }

    static {
        deckNameTxt.setFont(FontAppearance.FONT_SEARCH_SHOP);
        deckNameTxt.setFill(Color.BLACK);
        try {
            exportBox.setFill(new ImagePattern(new Image(new FileInputStream("end_turn.png"))));
            importBox.setFill(new ImagePattern(new Image(new FileInputStream("end_turn.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        exportBox.setOpacity(0.7);
        importBox.setOpacity(0.7);
    }

    static {
        importDeck.setOnMouseEntered(e -> importBox.setOpacity(1));
        importDeck.setOnMouseExited(e -> importBox.setOpacity(0.7));

        exportDeck.setOnMouseEntered(e -> exportBox.setOpacity(1));
        exportDeck.setOnMouseExited(e -> exportBox.setOpacity(0.7));
    }

    public static void display() {
        VBox root = new VBox();
        Scene scene = new Scene(root);
        root.setAlignment(Pos.CENTER);
        scene.setFill(ColorAppearance.COLOR_TITLES_OF_SHOP);
        root.setSpacing(160);
        VBox.setMargin(deckNameTxt, new Insets(0, 0, 0, 0));
        deckName.setMaxWidth(Main.WIDTH_OF_WINDOW / 5);
        VBox vBox = new VBox(deckNameTxt, deckName);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        StackPane stackPane1 = new StackPane(exportBox, exportDeck);
        StackPane stackPane2 = new StackPane(importBox, importDeck);

        VBox vBox1 = new VBox(vBox, stackPane1, stackPane2, result);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(20);
        eventHandler();
        root.getChildren().addAll(vBox1);
        popOp.setScene(scene);
        popOp.showAndWait();
    }

    public static void eventHandler() {
        exportDeck.setOnMouseClicked(event -> exportAction());

        exportBox.setOnMouseClicked(event -> exportAction());

        importDeck.setOnMouseClicked(event -> importAction());

        importBox.setOnMouseClicked(event -> importAction());
    }

    private static void importAction() {
        String address = deckName.getText() + ".json";
        Deck imported = Save.importDeck(address);
        if (imported == null) {
            result.setText("This deck hasn't been exported");
            deckName.setText("");
        } else {
            if (imported.getItem() != null) {
                int flag = 0;
                for (Item item : Account.getLoginUser().getCollection().getItems()) {
                    if (imported.getItem().getName().equals(item.getName())) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    result.setText("You don't have this deck's item in your collection");
                    deckName.setText("");
                    return;
                }
            }
            for (Card deckCard : imported.getCards()) {
                int flag = 0;
                for (Card collectionCard : Account.getLoginUser().getCollection().getCards()) {
                    if (deckCard.getName().equals(collectionCard.getName())) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    result.setText("You don't some of this deck's card in your collection");
                    deckName.setText("");
                    return;
                }
            }
            Account.getLoginUser().getCollection().getDecks().add(imported);
            result.setText("Deck successfully imported");
            deckName.setText("");
        }
    }

    private static void exportAction() {
        String name = deckName.getText();
        Deck deck = Account.getLoginUser().getCollection().returnDeckFromName(name);
        Save.exportDeck(deck);
        result.setText("Deck exported successfully in file: " + deck.getName() + ".json");
        deckName.setText("");
    }

}
