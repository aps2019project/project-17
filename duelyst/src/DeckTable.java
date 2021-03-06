import CardCollections.Deck;
import Data.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
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


    private void makeTable() {
        TableView tableView = new TableView();

        TableColumn<Deck, String> column1 = new TableColumn<>("DeckName");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column1.setMinWidth(150);

        TableColumn<Deck, String> column2 = new TableColumn<>("Validate");
        column2.setCellValueFactory(new PropertyValueFactory<>("isValid"));
        column2.setMinWidth(150);

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.setItems(getAllDecks());

        column2.setCellFactory(column -> {
            return new TableCell<Deck, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<Deck> currentRow = getTableRow();

                    if (!isEmpty()) {
                        if (item.equals("INVALID"))
                            currentRow.setStyle("-fx-background-color:lightcoral");
                        else
                            currentRow.setStyle("-fx-background-color:lightgreen");
                    }
                }
            };
        });
        // Create a new RowFactory to handle actions
        tableView.setRowFactory(tv -> {

            // Define our new TableRow
            TableRow<Deck> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                DeckOperator.display(row.getItem().getName());
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
        for (int i = 0; i < Account.getLoginUser().getCollection().getDecks().size(); i++) {
            Account.getLoginUser().getCollection().getDecks().get(i).setIsValid();
        }
        return FXCollections.observableArrayList(Account.getLoginUser().getCollection().getDecks());

    }
}
