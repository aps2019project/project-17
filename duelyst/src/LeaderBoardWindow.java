import Data.Account;
import controller.GameController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


class LeaderBoardWindow {

    LeaderBoardWindow() {
        makeTable();
    }

    private void makeTable() {
        TableView tableView = new TableView();

        TableColumn<Account,String> column1 = new TableColumn<>("UserName");
        column1.setCellValueFactory(new PropertyValueFactory<>("userName"));
        column1.setMinWidth(100);

        TableColumn<Account,Integer> column2 = new TableColumn<>("Wins");
        column2.setCellValueFactory(new PropertyValueFactory<>("numOfWins"));
        column2.setMinWidth(100);

        TableColumn<Account,Integer> column3 = new TableColumn<>("Loses");
        column3.setCellValueFactory(new PropertyValueFactory<>("numOfLose"));
        column3.setMinWidth(100);

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

        tableView.setItems(getAllAccounts());

        VBox vbox = new VBox(tableView);

        Scene scene = new Scene(vbox, 300, 300);
        Stage window = new Stage();
        window.setTitle("LeaderBoard");
        window.setScene(scene);
        window.getScene().getStylesheets().add(getClass().getResource("Table.css").toExternalForm());
        window.showAndWait();
    }

    private ObservableList<Account> getAllAccounts() {
        return FXCollections.observableArrayList(GameController.getAccounts());

    }
}
