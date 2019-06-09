import Appearance.FontAppearance;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorOnBattle {
    private static Stage window = new Stage();
    private static Text massage = new Text();
    private static Text close = new Text("close");

    static {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(Main.WIDTH_OF_WINDOW / 2.5);
        window.setMinHeight(Main.HEIGHT_OF_WINDOW / 15);
        massage.setFont(FontAppearance.FONT_SEARCH_SHOP);
        massage.setFill(Color.RED);
        close.setFont(FontAppearance.FONT_SEARCH_SHOP);
        close.setFill(Color.BLACK);
    }

    static {
        close.setOnMouseClicked(e -> window.close());
    }

    public static void display(String massage0) {
        massage.setText(massage0);
        VBox root = new VBox(massage, close);
        Scene scene = new Scene(root);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(Main.HEIGHT_OF_WINDOW / 30);
        window.setScene(scene);
        window.showAndWait();
    }
}
