import Appearance.CardsDataAppearance;
import Appearance.ColorAppearance;
import Appearance.FontAppearance;
import Cards.Card;
import Cards.Hero;
import Cards.Minion;
import Cards.Spell;
import Data.Account;
import InstanceMaker.CardMaker;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

class CustomCardShop {

    private Stage window = new Stage();
    private Group root = new Group();
    private Scene CustomCardShopScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
    private Rectangle[][] shownCards = new Rectangle[2][5];
    private Rectangle[] allProducts;// TODO: 6/14/2019
    private ArrayList<Card> customCards = new ArrayList<>();
    private Rectangle fillMenu = new javafx.scene.shape.Rectangle(Main.WIDTH_OF_WINDOW / 10, Main.HEIGHT_OF_WINDOW);
    private CardsDataAppearance[][] shownData = new CardsDataAppearance[2][5];
    private Rectangle currentSelectedRectangle;
    private Rectangle[][] outBox = new Rectangle[2][5];
    private ImageView rightDirection;
    private ImageView leftDirection;
    private int currentPage = 0;
    private Text title = new Text("CustomCard Shop");
    private Text currentPageView = new Text("page : 1");


    CustomCardShop(Text moneyValue) {
        initialiseCustomCardShow();
        initializeAllCards();
        initShownCards();
        initializeOutBox();
        setTitleAppearance();
        setBackGround();
        setDirectionsAction();
        addNodes();
        locateNodes();
        disPlay(moneyValue);
    }

    {
        try {
            rightDirection = new ImageView(new Image(new FileInputStream("arrowright.png")));
            leftDirection = new ImageView(new Image(new FileInputStream("leftarrow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initialiseCustomCardShow() {

        if (CardMaker.getHeroes().length > 10) {
            for (int i = 10; i < CardMaker.getHeroes().length; i++) {
                customCards.add(CardMaker.getHeroes()[i]);
            }
        }
        if (CardMaker.getMinions().length > 40) {
            for (int i = 40; i < CardMaker.getMinions().length; i++) {
                customCards.add(CardMaker.getMinions()[i]);
            }
        }

        if (CardMaker.getSpells().length > 20) {
            for (int i = 20; i < CardMaker.getSpells().length; i++) {
                customCards.add(CardMaker.getSpells()[i]);
            }
        }
        allProducts = new Rectangle[customCards.size()];
    }

    private void initializeOutBox() {
        for (int i = 0; i < outBox.length; i++) {
            for (int j = 0; j < outBox[i].length; j++) {
                outBox[i][j] = new Rectangle((Main.WIDTH_OF_WINDOW - (fillMenu.getWidth()) - 2 * 70) / 5 - 8, Main.HEIGHT_OF_WINDOW / 2.17 - 5);
                outBox[i][j].setFill(ColorAppearance.BACKGROUND_DATA_CARDS);
                outBox[i][j].setVisible(false);
                outBox[i][j].setOpacity(0.7);
            }
        }
    }

    private void setTitleAppearance() {
        title.setFont(Font.font("phosphate", 40));
        title.setFill(Color.WHITE);
        title.setRotate(-90);
        title.setLayoutX(-fillMenu.getWidth());
        title.setLayoutY(fillMenu.getHeight() / 2);

    }

    private void initializeAllCards() {
        ArrayList<Card> cards = customCards;
        for (int i = 0; i < allProducts.length; i++) {
            allProducts[i] = new Rectangle((Main.WIDTH_OF_WINDOW - (fillMenu.getWidth()) - 2 * 70) / 5.5, Main.HEIGHT_OF_WINDOW / 2.3);
            if (i < cards.size()) {
                try {
                    if (cards.get(i) instanceof Spell)
                        allProducts[i].setFill(new ImagePattern(new Image(new FileInputStream("product3.png"))));
                    else if (cards.get(i) instanceof Hero)
                        allProducts[i].setFill(new ImagePattern(new Image(new FileInputStream("product1.png"))));
                    else if (cards.get(i) instanceof Minion) {
                        FileInputStream fileInputStream = new FileInputStream("product2.png");
                        allProducts[i].setFill(new ImagePattern(new Image(fileInputStream)));
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                allProducts[i].setOpacity(0.7);
            }
        }
    }

    private void initShownCards() {
        ArrayList<Card> cards = new ArrayList<>(customCards);
        for (int i = 0; i < shownCards.length; i++) {
            for (int j = 0; j < shownCards[i].length; j++) {
                if ((5 * i) + j >= allProducts.length)
                    return;
                shownCards[i][j] = allProducts[(5 * i) + j];
                if (((5 * i) + j) < cards.size()) {
                    String name, price, manaPoint;
                    name = cards.get((5 * i) + j).getName();
                    price = Integer.toString(cards.get((5 * i) + j).getPrice());
                    if (cards.get((5 * i) + j) instanceof Minion) {
                        int hp = ((Minion) cards.get(j + (i * 5))).getAttackPoint();
                        int ap = ((Minion) cards.get(j + (i * 5))).getAttackPoint();
                        manaPoint = Integer.toString(((Minion) cards.get((5 * i) + j)).getManaPoint());
                        shownData[i][j] = new CardsDataAppearance(name, price, manaPoint, Integer.toString(ap), Integer.toString(hp));
                    } else if (cards.get((5 * i) + j) instanceof Spell) {
                        manaPoint = Integer.toString(((Spell) cards.get((5 * i) + j)).getManaPoint());
                        shownData[i][j] = new CardsDataAppearance(name, price, manaPoint);
                    }
                }
            }
        }
    }

    private void setBackGround() {
        Image image;
        try {
            image = new Image(new FileInputStream("purpleBackGrouund.jpg"));
            ImageView imageOfBackGround = new ImageView(image);
            imageOfBackGround.fitWidthProperty().bind(CustomCardShopScene.widthProperty());
            imageOfBackGround.fitHeightProperty().bind(CustomCardShopScene.heightProperty());
            imageOfBackGround.setOpacity(1);
            fillMenu.setOpacity(0.6);
            root.getChildren().add(imageOfBackGround);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setDirectionsAction() {
        rightDirection.setOpacity(0.4);
        leftDirection.setOpacity(0.4);
        rightDirection.setOnMouseEntered(e -> rightDirection.setOpacity(1));
        leftDirection.setOnMouseEntered(e -> leftDirection.setOpacity(1));
        rightDirection.setOnMouseExited(e -> rightDirection.setOpacity(0.4));
        leftDirection.setOnMouseExited(e -> leftDirection.setOpacity(0.4));
    }

    private void addNodes() {
        root.getChildren().add(fillMenu);

        for (Rectangle[] box : outBox)
            root.getChildren().addAll(box);

        for (Rectangle[] shownCard : shownCards)
            for (Rectangle rectangle : shownCard) {
                if (rectangle == null)
                    continue;
                root.getChildren().add(rectangle);
            }
        root.getChildren().addAll(rightDirection, leftDirection, currentPageView, title);
    }

    private void locateNodes() {
        locateShownCards();
        locateDirections();
        locateData();
    }

    private void locateDirections() {
        double y = 18.5 * CustomCardShopScene.getHeight() / 40;
        double x = CustomCardShopScene.getWidth() * 28 / 29;
        leftDirection.setLayoutX(CustomCardShopScene.getWidth() * 3.2 / 31);
        leftDirection.setLayoutY(y);
        rightDirection.setLayoutX(x);
        rightDirection.setLayoutY(y);
        leftDirection.setOpacity(0.4);
        rightDirection.setOpacity(0.4);
        currentPageView.setLayoutX(Main.WIDTH_OF_WINDOW / 50);
        currentPageView.setLayoutY(7 * Main.HEIGHT_OF_WINDOW / 8);
        currentPageView.setFont(FontAppearance.FONT_BUTTON);
        currentPageView.setFill(Color.WHITE);
    }

    private void locateShownCards() {
        for (int i = 0; i < shownCards.length; i++) {
            for (int j = 0; j < shownCards[i].length; j++) {
                if (shownCards[i][j] == null)
                    continue;
                if (j == 0) {
                    if (i == 0) {

                        shownCards[i][j].setLayoutY(fillMenu.getHeight() / 45);
                        shownCards[i][j].setLayoutX(fillMenu.getWidth() + Main.WIDTH_OF_WINDOW / 45);
                        outBox[i][j].setLayoutX(shownCards[i][j].getLayoutX());
                        outBox[i][j].setLayoutY(shownCards[i][j].getLayoutY());
                        continue;
                    }

                    shownCards[i][j].setLayoutX(shownCards[i - 1][j].getLayoutX());
                    shownCards[i][j].setLayoutY(shownCards[i - 1][j].getLayoutY() + Main.HEIGHT_OF_WINDOW / 2);
                    outBox[i][j].setLayoutY(shownCards[i][j].getLayoutY());
                    outBox[i][j].setLayoutX(shownCards[i][j].getLayoutX());
                    continue;
                }
                shownCards[i][j].setLayoutY(shownCards[i][j - 1].getLayoutY());
                shownCards[i][j].setLayoutX(shownCards[i][j - 1].getLayoutX() + Main.WIDTH_OF_WINDOW / 6);
                outBox[i][j].setLayoutY(shownCards[i][j].getLayoutY());
                outBox[i][j].setLayoutX(shownCards[i][j].getLayoutX());
            }
        }
    }

    private void locateData() {
        if (shownCards[0][0] == null)
            return;
        double cardWidth = shownCards[0][0].getWidth();
        double cardHeight = shownCards[0][0].getHeight();
        for (int i = 0; i < shownData.length; i++) {
            for (int j = 0; j < shownData[i].length; j++) {
                if (shownData[i][j] != null) {
                    shownData[i][j].addAll(root);
                    shownData[i][j].getNameView().setLayoutX(shownCards[i][j].getLayoutX() + 1 * cardWidth / 10);
                    shownData[i][j].getMpView().setLayoutX((shownCards[i][j].getLayoutX()) + cardWidth / 14.1);
                    shownData[i][j].getPriceView().setLayoutX(shownCards[i][j].getLayoutX() + cardWidth / 2.3);
                    shownData[i][j].getNameView().setLayoutY(shownCards[i][j].getLayoutY() + 4 * cardHeight / 5);
                    shownData[i][j].getMpView().setLayoutY((shownCards[i][j].getLayoutY()) + cardHeight / 9.9);
                    shownData[i][j].getPriceView().setLayoutY(shownCards[i][j].getLayoutY() + 10 * cardHeight / 11);

                    if (shownData[i][j].getApView() != null) {
                        shownData[i][j].getApView().setLayoutX(shownCards[i][j].getLayoutX() + cardWidth / 4);
                        shownData[i][j].getApView().setLayoutY(shownCards[i][j].getLayoutY() + cardHeight / 1.55);
                        shownData[i][j].getHpView().setLayoutX(shownData[i][j].getApView().getLayoutX() + cardWidth / 2.1);
                        shownData[i][j].getHpView().setLayoutY(shownData[i][j].getApView().getLayoutY());
                    }
                }
            }
        }
    }

    private void disPlay(Text moneyValue) {
        window.setScene(CustomCardShopScene);
        window.show();
        handleEventsKeyBoards();
        handleEventsCards(moneyValue);
    }

    private void handleEventsKeyBoards() {
        int sizeVariable = allProducts.length / 10;
        if (allProducts.length % 10 != 0)
            sizeVariable++;
        int size = sizeVariable;
        rightDirection.setOnMouseClicked(e -> {
            currentPage = Math.abs((currentPage + 1) % size);
            changeCards();
        });
        leftDirection.setOnMouseClicked(e -> {
            currentPage = Math.abs((currentPage + size - 1) % size);
            changeCards();
        });
        CustomCardShopScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    currentPage = Math.abs((currentPage + size - 1) % size);
                    changeCards();
                    break;
                case RIGHT:
                    currentPage = Math.abs((currentPage + 1) % size);
                    changeCards();
                    break;
                case ESCAPE:
                    new MainMenu();
            }
        });
    }

    private void changeCards() {
        for (int i = 0; i < shownCards.length; i++) {
            for (int j = 0; j < shownCards[i].length; j++) {
                if (shownData[i][j] == null) {
                    continue;
                }
                shownData[i][j].removeAll(root);
                root.getChildren().remove(shownCards[i][j]);
                shownCards[i][j] = null;
            }
        }

        ArrayList<Card> cards = customCards;
        for (int i = 0; i < shownCards.length; i++) {
            for (int j = 0; j < shownCards[i].length; j++) {
                if ((currentPage * 10) + (5 * i) + j >= allProducts.length)
                    continue;
                shownCards[i][j] = allProducts[(currentPage * 10) + (5 * i) + j];
                root.getChildren().add(shownCards[i][j]);
                if ((currentPage * 10) + (5 * i) + j < cards.size()) {
                    if (cards.get((currentPage * 10) + (5 * i) + j) instanceof Minion) {
                        Minion minion = (Minion) cards.get((currentPage * 10) + (5 * i) + j);
                        shownData[i][j] = new CardsDataAppearance(minion.getName().toUpperCase(), Integer.toString(minion.getPrice()), Integer.toString(minion.getManaPoint()), Integer.toString(minion.getAttackPoint()), Integer.toString(minion.getHealthPoint()));
                    } else {
                        Spell spell = (Spell) cards.get((currentPage * 10) + (5 * i) + j);
                        shownData[i][j] = new CardsDataAppearance(spell.getName().toUpperCase(), Integer.toString(spell.getPrice()), Integer.toString(spell.getManaPoint()));
                    }
                }
            }
        }
        currentPageView.setText("Page : ".concat(Integer.toString(Math.abs(currentPage + 1))));
        locateShownCards();
        locateNodes();
        if (currentSelectedRectangle != null)
            currentSelectedRectangle.setVisible(false);
    }


    private void handleEventsCards(Text moneyValue) {
        for (int i = 0; i < allProducts.length; i++) {
            final Rectangle temp = allProducts[i];
            final int value = i % 10;
            allProducts[i].setOnMouseEntered(e -> {
                temp.setOpacity(1);
                shownData[value / 5][value % 5].light();
            });
            allProducts[i].setOnMouseExited(e -> {
                temp.setOpacity(0.7);
                shownData[value / 5][value % 5].dark();
            });
            allProducts[i].setOnMouseClicked(e -> {
                if (currentSelectedRectangle != null)
                    currentSelectedRectangle.setVisible(false);
                outBox[value / 5][value % 5].setVisible(true);
                currentSelectedRectangle = outBox[value / 5][value % 5];
                CertainlyOfShop.disPlay(shownData[value / 5][value % 5].getNameView().getText().toLowerCase(), shownData[value / 5][value % 5].getPriceView().getText());
                moneyValue.setText(Integer.toString(Account.getLoginUser().getDaric()));
            });
        }
    }
}


