import Appearance.ColorAppearance;
import Appearance.FontAppearance;
import Data.Account;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.CollectionView;

public class CreateDeckAppearance {

    private static Stage window = new Stage();
    private static TextField enterDeckName = new TextField();
    private static Text create = new Text("Create Deck");
    private static Text close = new Text("close");
    private static Text label = new Text();

    static {
        window.setTitle("CREATING DECK");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(Main.WIDTH_OF_WINDOW / 2.5);
        window.setMinHeight(Main.HEIGHT_OF_WINDOW / 6.5);
        label.setFont(FontAppearance.FONT_SEARCH_SHOP);
        close.setFont(FontAppearance.FONT_SEARCH_SHOP);
        close.setFill(ColorAppearance.BACKGROUND_DATA_CARDS);
        enterDeckName.setMinWidth(Main.WIDTH_OF_WINDOW / 13);
        create.setFont(FontAppearance.FONT_CREATE_DECK);
        create.setFill(ColorAppearance.GOLD_COLOR);
    }

    public static void disPlay() {
        VBox root = new VBox(enterDeckName, create);
        Scene scene = new Scene(root);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(window.getMinHeight() / 5);
        window.setScene(scene);
        create.setOnMouseClicked(e -> action(root));
        window.showAndWait();
        CollectionView.showAllDecks(Account.getLoginUser());
    }

    private static void action(VBox root) {
        String name = enterDeckName.getText();
        if (name.equals(""))
            return;
        root.getChildren().removeAll(enterDeckName, create);
        label.setText(Account.getLoginUser().getCollection().createDeck(name));
        root.getChildren().addAll(label, close);
        close.setOnMouseClicked(e -> window.close());
    }
}
