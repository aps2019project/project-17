import Appearance.ColorAppearance;
import Appearance.FontAppearance;
import Data.Account;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class CertainlyOfShop {

    private static Stage popOp = new Stage();
    private static Text label = new Text();
    private static Text yes = new Text("YES");
    private static Text no = new Text("No");
    private static Text close = new Text("CLOSE");

    static {
        popOp.initModality(Modality.APPLICATION_MODAL);
        popOp.setMaxWidth(Main.WIDTH_OF_WINDOW / 4);
        popOp.setMaxHeight(Main.HEIGHT_OF_WINDOW / 4);
        label.setFont(FontAppearance.FONT_SEARCH_SHOP);
        yes.setFont(FontAppearance.FONT_SEARCH_SHOP);
        no.setFont(FontAppearance.FONT_SEARCH_SHOP);
        close.setFont(FontAppearance.FONT_SEARCH_SHOP);
        yes.setFill(Color.GREEN);
        no.setFill(Color.RED);
        close.setFill(ColorAppearance.BACKGROUND_DATA_CARDS);
    }


    public static void disPlay(String cardName, String price) {
        VBox root = new VBox();
        Scene scene = new Scene(root);
        root.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(yes, no);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(Main.WIDTH_OF_WINDOW / 8);
        popOp.setScene(scene);
        String result = Account.getLoginUser().getShop().search(cardName);
        if (result.equals("there is not any card\\item in shop with this name")) {
            sellLogic(cardName, root, hBox);

        } else {
            buyLogic(cardName, price, root, hBox);
        }
        popOp.showAndWait();
    }

    private static void buyLogic(String cardName, String price, VBox root, HBox hBox) {
        if (Account.getLoginUser().getDaric() < Integer.parseInt(price)) {
            label.setText("you dont have enough money :(");
            root.getChildren().addAll(label, close);
            close.setOnMouseClicked(e -> popOp.close());
        } else {
            label.setText("your selection cost ".concat(price.concat(" daric! are you sure ?!")));
            root.getChildren().addAll(label);
            root.getChildren().addAll(hBox);
            yes.setOnMouseClicked(e -> {
                root.getChildren().removeAll(hBox);
                buy(cardName, root);
            });
            no.setOnMouseClicked(e -> popOp.close());
        }
    }

    private static void sellLogic(String cardName, VBox root, HBox hBox) {
        label.setText("are you sure yo sell this  card\\item ?!");
        root.getChildren().add(label);
        root.getChildren().addAll(hBox);
        yes.setOnMouseClicked(e -> {
            root.getChildren().removeAll(hBox);
            sell(cardName, root);
        });
        no.setOnMouseClicked(e -> popOp.close());
    }

    private static void buy(String cardName, VBox root) {
        String result = Account.getLoginUser().getShop().buy(cardName);
        System.out.println(result);
        label.setText(result);
        root.getChildren().addAll(close);
        close.setOnMouseClicked(e -> popOp.close());
    }

    private static void sell(String cardName, VBox root) {
        String result = Account.getLoginUser().getShop().sell(cardName);
        System.out.println(result);
        label.setText(result);
        root.getChildren().add(close);
        close.setOnMouseClicked(e -> popOp.close());
    }
}
