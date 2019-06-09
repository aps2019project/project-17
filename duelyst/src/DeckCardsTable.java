import Appearance.ColorAppearance;
import CardCollections.Deck;
import Data.Account;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    public static void display(String deckName){
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.WHITE);
        Deck deck= Account.getLoginUser().getCollection().findDeck(deckName);
        String result="";
        if(deck.getHero()!=null){
            result=result.concat("Hero:\n"+deck.getHero().getName()+"\n");
        }
        if(deck.getItem()!=null){
            result=result.concat("Item:\n"+deck.getItem().getName()+"\n");
        }
        if(deck.getCards()!=null){
            result=result.concat("Cards:\n");
            for (int i = 0; i <deck.getCards().size() ; i++) {
                result=result.concat(deck.getCards().get(i).getName());
                if(i%5==0){
                    result=result.concat("\n");
                }else if(i!=deck.getCards().size()-1){
                    result=result.concat(" / ");
                }
            }
        }
        showDeckText.setText(result);
        showDeckText.setLayoutX(15);
        showDeckText.setLayoutY(15);
        cardsImage.setLayoutX(3*popOp.getWidth()/4);
        cardsImage.setLayoutX(0);
        root.getChildren().addAll(showDeckText,cardsImage);
        popOp.setScene(scene);
        popOp.showAndWait();
    }



}
