import Appearance.FontAppearance;
import CardCollections.Deck;
import Data.Account;
import controller.GameController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class DeckOperator {

    private static Stage popOp = new Stage();
    private static Text result = new Text("this is the result");
    private static TextField cardId = new TextField();
    private static TextField cardName = new TextField();
    private static Text addToDeck = new Text("Add Card To Deck");
    private static Text removeFromDeck = new Text("Remove Card FromDeck");
    private static Text searchInDeck = new Text("Search Card In Deck");

    static {
        popOp.initModality(Modality.APPLICATION_MODAL);
        popOp.setMinWidth(200);// TODO: 6/7/2019 nesbi kon ino
        popOp.setMinHeight(200);
        result.setFont(FontAppearance.FONT_SEARCH_SHOP);
        addToDeck.setFont(FontAppearance.FONT_SEARCH_SHOP);
        removeFromDeck.setFont(FontAppearance.FONT_SEARCH_SHOP);
        searchInDeck.setFont(FontAppearance.FONT_SEARCH_SHOP);
        addToDeck.setFill(Color.GREEN);
        removeFromDeck.setFill(Color.RED);
        searchInDeck.setFill(Color.BLUE);
        result.setFill(Color.BLACK);
        cardId.setPromptText("CardId...");
        cardName.setPromptText("CardName");
    }

    public static void display(String deckName) {
        VBox root = new VBox();
        Scene scene = new Scene(root);
        root.setAlignment(Pos.CENTER);
        VBox.setMargin(cardId, new Insets(10, 200 / 3, 20, 200 / 3));
        VBox.setMargin(addToDeck, new Insets(20, 0, 20, 0));
        VBox.setMargin(removeFromDeck, new Insets(20, 0, 20, 0));
        VBox.setMargin(searchInDeck, new Insets(20, 0, 20, 0));
        VBox.setMargin(cardName, new Insets(0, 200 / 3, 0, 200 / 3));
        VBox.setMargin(result, new Insets(40, 0, 20, 0));
        addToDeck.setOnMouseClicked(event -> {
            String id = cardId.getText().toLowerCase();
            result.setText(GameController.addToDeck(id, deckName, Account.getLoginUser().getCollection()));
        });
        removeFromDeck.setOnMouseClicked(event -> {
            String id = cardId.getText().toLowerCase();
            result.setText(GameController.removeFromDeck(id, deckName, Account.getLoginUser().getCollection()));
        });

        searchInDeck.setOnMouseClicked(event -> {
            String name = cardName.getText().toLowerCase();
            Deck deck=Account.getLoginUser().getCollection().returnDeckFromName(deckName);
            result.setText(deck.returnIdFromName(name));
        });



        root.getChildren().addAll(cardId, addToDeck, removeFromDeck, searchInDeck, cardName, result);
        popOp.setScene(scene);
        popOp.showAndWait();

    }

}
