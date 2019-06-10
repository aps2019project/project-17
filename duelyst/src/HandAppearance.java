import Appearance.FontAppearance;
import CardCollections.Hand;
import Cards.Card;
import Cards.Minion;
import Cards.Spell;
import Data.Account;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;


public class HandAppearance {

    private final Rectangle[] handIconsTemplate = new Rectangle[5];
    private Rectangle[] handIcons = new Rectangle[5];
    private Rectangle[] informationOfCards = new Rectangle[5];
    private Rectangle selectedCardIcon;
    private Text[] manaOfPlayers = new Text[handIcons.length];
    private Hand hand;
    private Group root;
    private Card selectedCard;

    public HandAppearance(Group root) {
        primaryInitialize(root);
        setHandIcons();
        addIconsToBattle();
        locateIcons();
        locateData();
        setEventHandling();
        addInformationOfCards();
    }

    private void primaryInitialize(Group root) {
        this.root = root;
        this.hand = Account.getLoginUser().getPlayer().getHand();
        this.selectedCard = null;
        this.selectedCardIcon = null;
    }

    private void setHandIcons() {
        for (int i = 0; i < handIconsTemplate.length; i++) {
            handIconsTemplate[i] = new Rectangle(Main.WIDTH_OF_WINDOW / 13, Main.HEIGHT_OF_WINDOW / 7);
            try {
                handIconsTemplate[i].setFill(new ImagePattern(new Image(new FileInputStream("blank_template.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < handIcons.length; i++) {
            handIcons[i] = new Rectangle(handIconsTemplate[i].getWidth() * 1.3, handIconsTemplate[i].getHeight() * 1.3);
            manaOfPlayers[i] = new Text("0");
            handIconsTemplate[i].setOpacity(0.5);
            initializeHandIcons(i);
        }
        for (int i = 0; i < informationOfCards.length; i++)
            informationOfCards[i] = new Rectangle(Main.WIDTH_OF_WINDOW / 13, Main.HEIGHT_OF_WINDOW / 7);

    }

    private void setEventHandling() {
        for (int i = 0; i < handIcons.length; i++) {
            final int value = i;
            if (handIcons[i] != null) {
                handIcons[i].setOnMouseClicked(e -> {
                    if (selectedCard != null)
                        selectedCardIcon.setOpacity(0.5);
                    selectedCard = hand.getCards().get(value);
                    selectedCardIcon = handIcons[value];
                    selectedCardIcon.setOpacity(1);
                });
            }
        }
    }

    private void addIconsToBattle() {
        root.getChildren().addAll(handIconsTemplate);
        root.getChildren().addAll(handIcons);
        root.getChildren().addAll(manaOfPlayers);

    }

    private void addInformationOfCards() {
        for (int i = 0; i < informationOfCards.length; i++) {
            if (handIcons[i] != null)
                setInformationOfCards(i, informationOfCards[i]);

        }
    }

    private void setInformationOfCards(int i, Rectangle informationOfCard) {
        Text text0 = new Text(hand.getCards().get(i).getName());
        Text text1 = new Text("");
        if (hand.getCards().get(i) instanceof Spell)
            text1.setText("Spell");
        else text1.setText("Minion");
        text0.setFill(Color.WHITE);
        text1.setFill(Color.WHITE);
        text0.setFont(FontAppearance.FONT_INFORMATION_CARDS_BATTLE);
        text1.setFont(FontAppearance.FONT_INFORMATION_CARDS_BATTLE);
        VBox vBox = new VBox(text0, text1);
        vBox.setAlignment(Pos.CENTER);
        StackPane stackPane = new StackPane(informationOfCards[i], vBox);
        try {
            informationOfCards[i].setFill(new ImagePattern(new Image(new FileInputStream("info_card.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        stackPane.setLayoutX(handIcons[i].getLayoutX());
        stackPane.setLayoutY(handIcons[i].getLayoutY() * 8 / 10);
        root.getChildren().add(stackPane);
        stackPane.setVisible(false);
        informationOfCards[i].setVisible(false);
        handIcons[i].setOnMouseEntered(e -> {
            stackPane.setVisible(true);
            informationOfCard.setVisible(true);
        });
        handIcons[i].setOnMouseExited(e -> {
            stackPane.setVisible(false);
            informationOfCard.setVisible(false);
        });
        handIconsTemplate[i].setOnMouseEntered(e -> {
            stackPane.setVisible(true);
            informationOfCard.setVisible(true);
        });
        handIconsTemplate[i].setOnMouseExited(e -> {
            stackPane.setVisible(false);
            informationOfCard.setVisible(true);
        });
    }

    private void locateIcons() {
        for (int i = 0; i < handIcons.length; i++) {
            if (handIcons[i] != null) {
                double x = Main.WIDTH_OF_WINDOW / 3.6 + (Main.WIDTH_OF_WINDOW / 9.6 * i);
                double y = Main.HEIGHT_OF_WINDOW * 7.9 / 10;
                handIcons[i].setLayoutX(x);
                handIcons[i].setLayoutY(0.95* y);
                handIconsTemplate[i].setLayoutX(x);
                handIconsTemplate[i].setLayoutY(y * 1.015);
                setInformationOfCards(i, informationOfCards[i]);
            }
        }
    }

    private void locateData() {
        for (int i = 0; i < manaOfPlayers.length; i++) {
            if (handIcons[i] != null) {
                double x = handIcons[i].getLayoutX() + Main.WIDTH_OF_WINDOW / 25.71;
                double y = Main.HEIGHT_OF_WINDOW * 9.2 / 10;
                manaOfPlayers[i].setLayoutX(x * 0.995);
                manaOfPlayers[i].setLayoutY(y * 1.02);
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
            handIcons[i] = new Rectangle(Main.WIDTH_OF_WINDOW / 13, Main.HEIGHT_OF_WINDOW / 8);
            manaOfPlayers[i] = new Text("0");
            if (i < hand.getCards().size()) {
                initializeHandIcons(i);
                handIconsTemplate[i].setVisible(true);
                continue;
            }
            handIcons[i] = null;
            manaOfPlayers[i] = null;
            handIconsTemplate[i].setVisible(false);
        }
        add();
    }

    public void endTurn() {

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
                handIcons[i].setFill(new ImagePattern(new Image(new FileInputStream("gifs/gif".concat(Integer.toString(new Random().nextInt(10)).concat(".gif"))))));
                manaOfPlayers[i].setText(Integer.toString(((Minion) hand.getCards().get(i)).getManaPoint()));
            } else if (hand.getCards().get(i) instanceof Spell) {
                handIcons[i].setFill(new ImagePattern(new Image(new FileInputStream("gifs/gif".concat(Integer.toString(new Random().nextInt(10)).concat(".gif"))))));
                manaOfPlayers[i].setText(Integer.toString(((Spell) hand.getCards().get(i)).getManaPoint()));
            }
            handIcons[i].setOpacity(1);
            manaOfPlayers[i].setFont(FontAppearance.FONT_CREATE_DECK);
            handIcons[i].setOpacity(0.5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void setSelectedCardIcon(Rectangle selectedCardIcon) {
        this.selectedCardIcon = selectedCardIcon;
    }
}
