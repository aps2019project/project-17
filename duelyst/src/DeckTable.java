import CardCollections.Deck;
import Data.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeckTable {

    public DeckTable() {
        makeTable();
    }


    private void makeTable(){
        TableView tableView = new TableView();

        TableColumn<Deck,String> column1 = new TableColumn<>("DeckName");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column1.setMinWidth(300);

        tableView.getColumns().add(column1);

        tableView.setItems(getAllDecks());

        // Create a new RowFactory to handle actions
        tableView.setRowFactory(tv -> {

            // Define our new TableRow
            TableRow<Deck> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                // TODO: 6/7/2019  
            });
            return row;
        });

        VBox vbox = new VBox(tableView);
        Stage window = new Stage();
        window.setTitle("AllDecks");
        window.setScene(new Scene(vbox, 300, 300));
        window.show();

    }
    /**
     * @return all decks of logged in user
     */
    private ObservableList<Deck> getAllDecks() {
        return FXCollections.observableArrayList(Account.getLoginUser().getCollection().getDecks());

    }
}
