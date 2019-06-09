import Appearance.FontAppearance;
import CardCollections.Hand;
import Cards.Card;
import Cards.Minion;
import Cards.Spell;
import Data.Account;
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
    private Rectangle selectedCardIcon;

    public HandAppearance(Group root) {
        this.root = root;
        this.hand = Account.getLoginUser().getPlayer().getHand();
        this.selectedCard = null;
        this.selectedCardIcon = null;
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
            initializeHandIcons(i);
        }
    }

    private void setEventHandling() {
        for (int i = 0; i < handIcons.length; i++) {
            final int value = i;
            if (handIcons[i] != null) {
                handIcons[i].setOnMouseClicked(e -> {
                    if (selectedCard != null)
                        selectedCardIcon.setOpacity(0.75);

                    selectedCard = hand.getCards().get(value);
                    selectedCardIcon = handIcons[value];
                    selectedCardIcon.setOpacity(1);
                    System.out.println(selectedCard.getName());
                });
            }
        }
    }

    private void addIconsToBattle() {
        root.getChildren().addAll(handIcons);
        root.getChildren().addAll(manaOfPlayers);
    }

    private void locateIcons() {
        for (int i = 0; i < handIcons.length; i++) {
            if (handIcons[i] != null) {
                double x = Main.WIDTH_OF_WINDOW / 3.6 + (Main.WIDTH_OF_WINDOW / 9.6 * i);
                double y = Main.HEIGHT_OF_WINDOW * 7.9 / 10;
                handIcons[i].setLayoutX(x);
                handIcons[i].setLayoutY(y);
            }
        }
    }

    private void locateData() {
        for (int i = 0; i < manaOfPlayers.length; i++) {
            if (handIcons[i] != null) {
                double x = handIcons[i].getLayoutX() + Main.WIDTH_OF_WINDOW / 25.71;
                double y = Main.HEIGHT_OF_WINDOW * 9.2 / 10;
                manaOfPlayers[i].setLayoutX(x);
                manaOfPlayers[i].setLayoutY(y);
            }
        }
    }

    public Card getSelectedCard() {
        return this.selectedCard;
    }

    public Rectangle getSelectedCardIcon() {
        return this.selectedCardIcon;
    }

    public void insert() {
        this.root.getChildren().removeAll(handIcons);
        this.root.getChildren().removeAll(manaOfPlayers);
        this.selectedCard = null;
        this.selectedCardIcon = null;
        for (int i = 0; i < handIcons.length; i++) {
            handIcons[i] = new Rectangle(Main.WIDTH_OF_WINDOW / 12, Main.HEIGHT_OF_WINDOW / 6.95);
            manaOfPlayers[i] = new Text("0");
            if (i < hand.getCards().size()) {
                initializeHandIcons(i);
                continue;
            }
            handIcons[i] = null;
            manaOfPlayers[i] = null;
        }
        add();
    }

    private void add() {
        for (int i = 0; i < handIcons.length; i++) {
            if (handIcons[i] != null)
                this.root.getChildren().addAll(handIcons[i], manaOfPlayers[i]);
        }
        locateIcons();
        locateData();
        setEventHandling();
    }

    public void initializeHandIcons(int i) {
        try {
            if (hand.getCards().get(i) instanceof Minion) {
                handIcons[i].setFill(new ImagePattern(new Image(new FileInputStream("minion_hand.png"))));
                manaOfPlayers[i].setText(Integer.toString(((Minion) hand.getCards().get(i)).getManaPoint()));
            } else if (hand.getCards().get(i) instanceof Spell) {
                handIcons[i].setFill(new ImagePattern(new Image(new FileInputStream("spell_hand.png"))));
                manaOfPlayers[i].setText(Integer.toString(((Spell) hand.getCards().get(i)).getManaPoint()));
            }
            manaOfPlayers[i].setFont(FontAppearance.FONT_CREATE_DECK);
            handIcons[i].setOpacity(0.75);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
