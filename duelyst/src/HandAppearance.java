import Appearance.FontAppearance;
import CardCollections.Hand;
import Cards.Card;
import Cards.Minion;
import Cards.Spell;
import Data.Account;
import GameGround.Battle;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class HandAppearance {

    private Rectangle[] handIcons = new Rectangle[5];
    private Text[] manaOfPlayers = new Text[handIcons.length];
    private Hand hand;
    private Group root;
    private Card selectedCard;

    public HandAppearance(Group root) {
        this.root = root;
        this.hand = Account.getLoginUser().getPlayer().getHand();
        this.selectedCard = null;
        setHandIcons();
        addIconsToBattle();
        locateIcons();
        locateData();
        setEventHandling();
    }

    private void setHandIcons() {
        for (int i = 0; i < handIcons.length; i++) {
            handIcons[i] = new Rectangle(Main.WIDTH_OF_WINDOW / 12, Main.HEIGHT_OF_WINDOW / 6.95);
            manaOfPlayers[i] = new Text("0");
            try {
                if (hand.getCards().get(i) instanceof Minion) {
                    handIcons[i].setFill(new ImagePattern(new Image(new FileInputStream("minion_hand.png"))));
                    manaOfPlayers[i].setText(Integer.toString(((Minion) hand.getCards().get(i)).getManaPoint()));
                } else if (hand.getCards().get(i) instanceof Spell) {
                    handIcons[i].setFill(new ImagePattern(new Image(new FileInputStream("spell_hand.png"))));
                    manaOfPlayers[i].setText(Integer.toString(((Spell) hand.getCards().get(i)).getManaPoint()));
                }
                manaOfPlayers[i].setFont(FontAppearance.FONT_CREATE_DECK);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void setEventHandling() {
        for (int i = 0; i < handIcons.length; i++) {
            final  int value = i;
            handIcons[i].setOnMouseClicked(e -> {
                selectedCard = hand.getCards().get(value);
                Battle.getCurrentBattle().selectCardOrItem(hand.getCards().get(value).getId());
                System.out.println(Battle.getCurrentBattle().getSelectedCard().getName());
            });
        }
    }

    private void addIconsToBattle() {
        root.getChildren().addAll(handIcons);
        root.getChildren().addAll(manaOfPlayers);
    }

    private void locateIcons() {
        for (int i = 0; i < handIcons.length; i++) {
            double x = 400 + (150 * i);
            double y = Main.HEIGHT_OF_WINDOW * 7.9 / 10;
            handIcons[i].setLayoutX(x);
            handIcons[i].setLayoutY(y);
        }
    }

    private void locateData() {
        for (int i = 0; i < manaOfPlayers.length; i++) {
            double x = handIcons[i].getLayoutX() + 56;
            double y = Main.HEIGHT_OF_WINDOW * 9.2 / 10;
            manaOfPlayers[i].setLayoutX(x);
            manaOfPlayers[i].setLayoutY(y);
        }
    }
}
