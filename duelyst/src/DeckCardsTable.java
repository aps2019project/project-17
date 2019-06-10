import CardCollections.Deck;
import Cards.Card;
import Data.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class DeckCardsTable {

    private static Stage popOp = new Stage();
    private static Text showDeckText=new Text("");
    private static ImageView cardsImage;

    public DeckCardsTable(String deckName) {
        display(deckName);
    }

    static {
        popOp.setTitle("DECK");
        popOp.initModality(Modality.APPLICATION_MODAL);
        popOp.setMinWidth(Main.WIDTH_OF_WINDOW / 2.5);
        popOp.setMinHeight(Main.HEIGHT_OF_WINDOW / 4.17);
        showDeckText.setFill(Color.BLACK);
        showDeckText.setFont(Font.font("Arial",16));
        try {
            cardsImage = new ImageView(new Image(new FileInputStream("cards.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        cardsImage.setOpacity(0.5);
    }

    public void display(String deckName){
        TableView tableView = new TableView();

        TableColumn<Card,String> column1 = new TableColumn<>("Spells/Minions");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column1.setMinWidth(300);

        tableView.getColumns().add(column1);


        tableView.setItems(getAllCards(deckName));

        Stage window = new Stage();
        window.setTitle("AllCards");
        window.setScene(new Scene(new VBox(tableView), 300, 300));
        window.show();
    }

    private ObservableList<Card> getAllCards(String deckName) {
        Deck deck= Account.getLoginUser().getCollection().findDeck(deckName);
        return FXCollections.observableArrayList(deck.getCards());
    }

}
