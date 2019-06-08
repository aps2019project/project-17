import Appearance.FontAppearance;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class CheckValidationDeckAppearance {
    private static Stage popOp = new Stage();
    private static Text invalidDeck = new Text("your selected deck in invalid");
    private static VBox root = new VBox();
    private static Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth() / 4, Screen.getPrimary().getBounds().getHeight() / 10);
    private static Text close = new Text("Close");
    private static Text backCollection = new Text("back to collection");

    static {
        popOp.setScene(scene);
        invalidDeck.setFont(FontAppearance.FONT_NOT_FOUND);
        HBox hBox = new HBox(close, backCollection);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(Main.WIDTH_OF_WINDOW / 8);
        invalidDeck.setFill(Color.RED);
        root.getChildren().addAll(invalidDeck, hBox);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(Main.HEIGHT_OF_WINDOW / 15);
        close.setFont(FontAppearance.FONT_NOT_FOUND);
        backCollection.setFont(FontAppearance.FONT_NOT_FOUND);
    }

    static {
        close.setOnMouseClicked(e -> popOp.close());
        backCollection.setOnMouseClicked(e -> {
            popOp.close();
            new CollectionAppearance();
        });
        popOp.initModality(Modality.APPLICATION_MODAL);
    }

    public static void disPlay() {
        popOp.showAndWait();
    }
}
