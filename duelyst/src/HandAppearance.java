import Appearance.FontAppearance;
import Appearance.MinionAppearance;
import CardCollections.Hand;
import Cards.Card;
import Cards.Minion;
import Cards.Spell;
import GameGround.Battle;
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

public class HandAppearance {

    private Rectangle[] handIconsTemplate;
    private Rectangle[] handIcons;
    private Rectangle[] informationOfCards;
    private Rectangle nextCard;
    private Rectangle selectedCardIcon;
    private Text[] manaOfPlayers;
    private Hand hand;
    private Group root;
    private Card selectedCard;

    public HandAppearance(Group root) {
        primaryInitialize(root);
        initHandIconsAndTemplateAndInformationOfCards();
        addIHandAppearanceToBattleAppearance();
        locateIcons();
        locateData();
        setEventHandling();
        addInformationOfCards();
    }

    private void primaryInitialize(Group root) {
        this.root = root;
        this.hand = Battle.getCurrentBattle().getPlayerOne().getHand();
        this.selectedCard = null;
        this.selectedCardIcon = null;
        this.handIconsTemplate = new Rectangle[this.hand.getCards().size() + 1];
        this.informationOfCards = new Rectangle[this.hand.getCards().size() + 1];
        this.handIcons = new Rectangle[this.hand.getCards().size()];
        this.manaOfPlayers = new Text[hand.getCards().size()];
    }

    private void initHandIconsAndTemplateAndInformationOfCards() {
        for (int i = 0; i < handIconsTemplate.length; i++) {
            handIconsTemplate[i] = new Rectangle(Main.WIDTH_OF_WINDOW / 13, Main.HEIGHT_OF_WINDOW / 7);
            handIconsTemplate[i].setVisible(true);
            try {
                handIconsTemplate[i].setFill(new ImagePattern(new Image(new FileInputStream("blank_template.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (i > hand.getCards().size())
                handIconsTemplate[i].setVisible(false);
        }
        for (int i = 0; i < hand.getCards().size(); i++) {
            handIcons[i] = new Rectangle(handIconsTemplate[i].getWidth() / 2, handIconsTemplate[i].getHeight() / 2);
            manaOfPlayers[i] = new Text("0");
        }
        nextCard = new Rectangle(handIconsTemplate[0].getWidth(), handIconsTemplate[0].getHeight());
        for (int i = 0; i < informationOfCards.length; i++)
            informationOfCards[i] = new Rectangle(Main.WIDTH_OF_WINDOW / 13, Main.HEIGHT_OF_WINDOW / 7);
    }

    private void addIHandAppearanceToBattleAppearance() {
//        for (int i = 0; i < handIcons.length; i++) {
//            if (handIcons[i] != null)
//                root.getChildren().add(handIcons[i]);
//            if (handIconsTemplate[i] != null)
//                root.getChildren().add(handIconsTemplate[i]);
//            if (manaOfPlayers[i] != null)
//                root.getChildren().add(manaOfPlayers[i]);
//        }
//        for (Rectangle rectangle : handIconsTemplate) {
//            if (rectangle != null) {
//                if (!root.getChildren().contains(rectangle))
//                    root.getChildren().add(rectangle);
//            }
//        }
        root.getChildren().addAll(handIconsTemplate);
        root.getChildren().addAll(handIcons);
        root.getChildren().addAll(manaOfPlayers);
        root.getChildren().add(nextCard);
    }

    private void locateIcons() {
        for (int i = 0; i < hand.getCards().size(); i++) {
            if (handIcons[i] != null) {
                double x = Main.WIDTH_OF_WINDOW / 3.6 + (Main.WIDTH_OF_WINDOW / 9.6 * i);
                double y = Main.HEIGHT_OF_WINDOW * 7.9 / 10;
                handIcons[i].setLayoutX(x * 1.03);
                handIcons[i].setLayoutY(y * 1.06);
                handIconsTemplate[i].setLayoutX(x);
                handIconsTemplate[i].setLayoutY(y * 1.015);
                initializeHandIcons(i);
                setInformationOfCards(i, informationOfCards[i], hand.getCards().get(i));
            }
        }
        handIconsTemplate[this.hand.getCards().size()].setLayoutY(Main.HEIGHT_OF_WINDOW / 4);
        handIconsTemplate[this.hand.getCards().size()].setLayoutX(Main.WIDTH_OF_WINDOW / 9);
        nextCard.setLayoutX(Main.HEIGHT_OF_WINDOW / 5);
        nextCard.setLayoutY(Main.WIDTH_OF_WINDOW / 9);
        // TODO: 2019-06-12
        nextCard.setVisible(false);
    }

    private void locateData() {
        for (int i = 0; i < hand.getCards().size(); i++) {
            if (handIcons[i] != null && i < 5) {
                double x = handIcons[i].getLayoutX() + Main.WIDTH_OF_WINDOW / 25.71;
                double y = Main.HEIGHT_OF_WINDOW * 9.2 / 10;
                manaOfPlayers[i].setLayoutX(x * 0.97);
                manaOfPlayers[i].setLayoutY(y * 1.02);
            }
        }
    }

    private void setEventHandling() {
        for (int i = 0; i < this.hand.getCards().size(); i++) {
            if (handIcons[i] == null)
                continue;
            final int value = i;
            if (hand.getCards().get(i) instanceof Spell) {
                handIcons[i].setOnMouseClicked(e -> {
                    if (selectedCard != null && selectedCard == hand.getCards().get(value)) {
                        selectedCardIcon.setOpacity(0.5);
                        selectedCard = null;
                        return;
                    }
                    if (selectedCard instanceof Spell)
                        selectedCardIcon.setOpacity(0.5);
                    else if (selectedCard instanceof Minion)
                        BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(selectedCard.getName(), true).getImageView().setOpacity(0.5);
                    selectedCardIcon = handIcons[value];
                    selectedCardIcon.setOpacity(1);
                    selectedCard = hand.getCards().get(value);
                    System.out.println(hand.getCards().get(value).getName() + " selected");
                });
            } else if (hand.getCards().get(i) instanceof Minion) {
                MinionAppearance minionAppearance = BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(hand.getCards().get(i).getName(), true);
                if (minionAppearance == null)
                    return;
                minionAppearance.getImageView().setOnMouseClicked(e -> {
                    if (selectedCard != null && selectedCard == hand.getCards().get(value)) {
                        BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(selectedCard.getName(), true).getImageView().setOpacity(0.5);
                        selectedCard = null;
                        return;
                    }
                    if (selectedCard != null) {
                        if (!BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(selectedCard.getName(), true).isInHand())
                            return;
                    }
                    if (selectedCard instanceof Spell)
                        selectedCardIcon.setOpacity(0.5);
                    else if (selectedCard instanceof Minion)
                        BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(selectedCard.getName(), true).getImageView().setOpacity(0.5);
                    selectedCard = minionAppearance.getMinion();
                    BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(selectedCard.getName(), true).getImageView().setOpacity(1);
                    System.out.println(minionAppearance.getMinion().getName() + " selected");
                });
            }
        }
    }

    private void addInformationOfCards() {
        for (int i = 0; i < hand.getCards().size(); i++) {
            if (i < 5) {
                if (handIcons[i] != null)
                    setInformationOfCards(i, informationOfCards[i], hand.getCards().get(i));
            }
        }
    }

    private void setInformationOfCards(int i, Rectangle informationOfCard, Card card) {
        Text text0 = new Text(card.getName());
        Text text1 = new Text("");
        if (card instanceof Spell)
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
        handIconsTemplate[i].setOnMouseEntered(e -> {
            stackPane.setVisible(true);
            informationOfCard.setVisible(true);
        });
        handIconsTemplate[i].setOnMouseExited(e -> {
            stackPane.setVisible(false);
            informationOfCard.setVisible(true);
        });
    }

    public void initializeHandIcons(int i) {
        if (hand.getCards().get(i) instanceof Minion) {
            handIcons[i].setVisible(false);
            MinionAppearance minionAppearance = BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(hand.getCards().get(i).getName(), true);
            if(minionAppearance != null){
                minionAppearance.add(true);
                minionAppearance.setLocation(handIconsTemplate[i].getLayoutX(), 0.95 * handIconsTemplate[i].getLayoutY());
                minionAppearance.breathing();
                minionAppearance.getImageView().setOpacity(0.5);
            }
            manaOfPlayers[i].setText(Integer.toString(((Minion) hand.getCards().get(i)).getManaPoint()));
        } else if (hand.getCards().get(i) instanceof Spell) {
            try {
                handIcons[i].setFill(new ImagePattern(new Image(new FileInputStream("spell.gif"))));
                handIcons[i].setVisible(true);
                handIcons[i].setOpacity(0.5);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            manaOfPlayers[i].setText(Integer.toString(((Spell) hand.getCards().get(i)).getManaPoint()));
        }
        manaOfPlayers[i].setFont(FontAppearance.FONT_CREATE_DECK);
    }

    public void insert() {
        root.getChildren().removeAll(handIconsTemplate);
        root.getChildren().removeAll(handIcons);
        root.getChildren().removeAll(manaOfPlayers);
        root.getChildren().removeAll(nextCard);
        for (int i = 0; i < this.hand.getCards().size(); i++) {
            if (hand.getCards().get(i) instanceof Minion){
                MinionAppearance minionAppearance = BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(hand.getCards().get(i).getName(), true);
                if (minionAppearance != null)
                    minionAppearance.remove();
            }

//            else {
//                handIcons[i].setFill(null);
//            }
//            handIcons[i] = null;
//            handIconsTemplate[i] = null;
        }
        for (int i = 0; i < handIcons.length; i++) {
            if (handIcons[i] != null) {
                handIcons[i].setFill(null);
//                handIcons[i] = null;
            }
        }
        this.hand = null;


//        for (int i = 0; i < handIcons.length; i++) {
//            handIcons[i].setVisible(false);
//            handIconsTemplate[i].setVisible(false);
//            informationOfCards[i].setVisible(false);
//        }
//        initHandIconsAndTemplateAndInformationOfCards();
//        addIHandAppearanceToBattleAppearance();
//        locateIcons();
//        locateData();
//        setEventHandling();
//        addInformationOfCards();
    }

    private void setNextCardFill() {

    }

    public void setSelectedCard(Card selectedCard) {
        if (selectedCard == null && this.selectedCard != null) {
            if (this.selectedCard instanceof Spell)
                selectedCardIcon.setOpacity(0.5);
            else if (this.selectedCard instanceof Minion)
                BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(this.selectedCard.getName(), true).getImageView().setOpacity(0.5);
        }
        this.selectedCard = selectedCard;
    }

    public void setSelectedCardIcon(Rectangle selectedCardIcon) {
        this.selectedCardIcon = selectedCardIcon;
    }

    public Card getSelectedCard() {
        return this.selectedCard;
    }

    public Rectangle getSelectedCardIcon() {
        return this.selectedCardIcon;
    }
}