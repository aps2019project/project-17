import Client.*;
import Data.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OnlineTable {

    OnlineTable() {
        makeTable();
    }

    private void makeTable() {
        TableView tableView = new TableView();

        TableColumn<Account, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("userName"));
        column1.setMinWidth(100);


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

        Scene scene = new Scene(vbox, 100, 400);
        Stage window = new Stage();
        window.setTitle("OnlineUsers");
        window.setScene(scene);
        window.showAndWait();
    }

    private ObservableList<Account> getAllOnlineUsers() {
        return FXCollections.observableArrayList(Client.onlineAccounts());
    }
}
