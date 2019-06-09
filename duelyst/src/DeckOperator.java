import Appearance.ColorAppearance;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class DeckOperator {

    private static Stage popOp = new Stage();
    private static Text result = new Text();
    private static TextField cardId = new TextField();
    private static TextField cardName = new TextField();
    private static Text addToDeck = new Text("Add  this  Card  to  current  Deck");
    private static Text removeFromDeck = new Text("Remove  this  Card  From  current  Deck");
    private static Text showDeck=new Text("View Deck Cards");
    private static Text searchInDeck = new Text("Search situation of this Card  in  current  Deck");
    private static Text id = new Text("Enter  id  of  card  :  ");
    private static Text name = new Text("Enter  name  of  the  card  :  ");
    private static Rectangle addToDeckBox = new Rectangle(270, 30);
    private static Rectangle removeDeckBox = new Rectangle(310, 30);
    private static Rectangle showDeckBox=new Rectangle(270,30);
    private static Rectangle searchToDeckBox = new Rectangle(370, 30);

    static {
        popOp.setTitle("DECK OPERATOR");
        popOp.initModality(Modality.APPLICATION_MODAL);
        popOp.setMinWidth(Main.WIDTH_OF_WINDOW / 2.5);
        popOp.setMinHeight(Main.HEIGHT_OF_WINDOW / 4.17);
        result.setFont(FontAppearance.FONT_SEARCH_SHOP);
        addToDeck.setFont(FontAppearance.FONT_SEARCH_SHOP);
        removeFromDeck.setFont(FontAppearance.FONT_SEARCH_SHOP);
        showDeck.setFont(FontAppearance.FONT_SEARCH_SHOP);
        searchInDeck.setFont(FontAppearance.FONT_SEARCH_SHOP);
        addToDeck.setFill(Color.BLACK);
        removeFromDeck.setFill(Color.BLACK);
        showDeck.setFill(Color.BLACK);
        searchInDeck.setFill(Color.BLACK);
        result.setFill(Color.GREEN);
        cardId.setPromptText("ID of card");
        cardName.setPromptText("Name of card");
    }

    static {
        id.setFont(FontAppearance.FONT_SEARCH_SHOP);
        id.setFill(Color.BLACK);
        name.setFont(FontAppearance.FONT_SEARCH_SHOP);
        name.setFill(Color.BLACK);
        addToDeckBox.setFill(Color.GREEN);
        removeDeckBox.setFill(Color.RED);
        showDeckBox.setFill(Color.GRAY);
        searchToDeckBox.setFill(Color.BLUE);
        addToDeckBox.setOpacity(0.5);
        removeDeckBox.setOpacity(0.5);
        showDeckBox.setOpacity(0.5);
        searchToDeckBox.setOpacity(0.5);
    }

    static {
        addToDeck.setOnMouseEntered(e -> addToDeckBox.setOpacity(1));
        addToDeck.setOnMouseExited(e -> addToDeckBox.setOpacity(0.5));

        removeFromDeck.setOnMouseEntered(e -> removeDeckBox.setOpacity(1));
        removeFromDeck.setOnMouseExited(e -> removeDeckBox.setOpacity(0.5));

        showDeck.setOnMouseEntered(e->showDeckBox.setOpacity(1));
        showDeck.setOnMouseExited(e->showDeckBox.setOpacity(0.5));

        searchInDeck.setOnMouseEntered(e -> searchToDeckBox.setOpacity(1));
        searchInDeck.setOnMouseExited(e -> searchToDeckBox.setOpacity(0.5));
    }

    public static void display(String deckName) {
        VBox root = new VBox();
        Scene scene = new Scene(root);
        scene.setFill(ColorAppearance.COLOR_TITLES_OF_SHOP);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(160);
        cardId.setMaxWidth(Main.WIDTH_OF_WINDOW / 5);
        VBox.setMargin(id, new Insets(0, 0, 0, 0));
        VBox vBox = new VBox(id, cardId);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        StackPane stackPane1 = new StackPane(addToDeckBox, addToDeck);
        StackPane stackPane2 = new StackPane(removeDeckBox, removeFromDeck);
        StackPane stackPane3=new StackPane(showDeckBox,showDeck);
        StackPane stackPane4 = new StackPane(searchToDeckBox, searchInDeck);

        VBox vBox1 = new VBox(vBox, stackPane1, stackPane2,stackPane3);
        vBox1.setAlignment(Pos.CENTER);
        cardName.setMaxWidth(Main.WIDTH_OF_WINDOW / 5);
        vBox1.setSpacing(20);
        VBox vBox3 = new VBox(name, cardName);
        vBox3.setAlignment(Pos.CENTER);
        vBox3.setSpacing(5);
        VBox vBox2 = new VBox(vBox3, stackPane4, result);
        vBox2.setSpacing(20);
        vBox2.setAlignment(Pos.CENTER);
        eventHandler(deckName);
        root.getChildren().addAll(vBox1, vBox2);
        popOp.setScene(scene);
        popOp.showAndWait();
    }

    public static void eventHandler(String deckName) {
        addToDeck.setOnMouseClicked(event -> {
            String id = cardId.getText().toLowerCase();
            result.setText(GameController.addToDeck(id, deckName, Account.getLoginUser().getCollection()));
            cardId.setText("");
        });
        removeFromDeck.setOnMouseClicked(event -> {
            String id = cardId.getText().toLowerCase();
            result.setText(GameController.removeFromDeck(id, deckName, Account.getLoginUser().getCollection()));
            cardId.setText("");
        });

        showDeck.setOnMouseClicked(event -> DeckCardsTable.display(deckName));

        searchInDeck.setOnMouseClicked(event -> {
            String name = cardName.getText().toLowerCase();
            Deck deck = Account.getLoginUser().getCollection().returnDeckFromName(deckName);
            result.setText(deck.returnIdFromName(name));
            cardName.setText("");
        });
    }
}
