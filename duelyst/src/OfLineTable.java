import Client.Client;
import Data.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OfLineTable {

    public OfLineTable() {
        makeTable();
    }

    private void makeTable() {
        TableView tableView = new TableView();

        TableColumn<Account, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("userName"));
        column1.setMinWidth(200);


        tableView.getColumns().add(column1);


        new Thread(() -> {
            while (true) {
                tableView.refresh();
                tableView.setItems(getAllOnlineUsers());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        VBox vbox = new VBox(tableView);

        Scene scene = new Scene(vbox, 200, 400);
        Stage window = new Stage();
        window.setTitle("OfLineUsers");
        window.setScene(scene);
        window.getScene().getStylesheets().add(getClass().getResource("OfLineTable.css").toExternalForm());
        window.showAndWait();
    }

    private ObservableList<Account> getAllOnlineUsers() {
        return FXCollections.observableArrayList(Client.offlineAccounts());
    }
}
