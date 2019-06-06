import Appearance.CardsDataAppearance;
import Appearance.ColorAppearance;
import Appearance.FontAppearance;
import Cards.Hero;
import Cards.Item;
import Cards.Minion;
import Cards.Spell;
import Data.Account;
import InstanceMaker.CardMaker;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


class ShopAppearance {
    private Group root = new Group();
    private Scene shopScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
    private Rectangle[][] shownCards = new Rectangle[2][5];
    private Rectangle[][] outBox = new Rectangle[2][5];
    private ImageView shopIcon;
    private Text[] titles = {new Text("HEROES"), new Text("MINIONS"), new Text("SPELLS"), new Text("ITEMS")};
    private Rectangle fillMenu = new Rectangle(Main.WIDTH_OF_WINDOW / 10, Main.HEIGHT_OF_WINDOW);
    private ImageView rightDirection;
    private ImageView leftDirection;
    private Rectangle[] demoCards = new Rectangle[CardMaker.getAllCards().length + CardMaker.getAllItems().length];
    private int currentPage = 0;
    private Text currentPageView = new Text();
    private CardsDataAppearance[][] shownData = new CardsDataAppearance[2][5];
    private ImageView coinsImage;
    private Text moneyValue = new Text(Integer.toString(Account.getLoginUser().getDaric()));

    {
        try {
            rightDirection = new ImageView(new Image(new FileInputStream("arrowright.png")));
            leftDirection = new ImageView(new Image(new FileInputStream("leftarrow.png")));
            coinsImage = new ImageView(new Image(new FileInputStream("coins.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    ShopAppearance() {
        initializeDemoCardsAndShownCards();
        initializeCards();
        setBackGround();
        addNodes();
        locateNodes();
        setMouse();
        display();
    }

    private void initializeDemoCardsAndShownCards() {
        for (int i = 0; i < demoCards.length; i++) {
            demoCards[i] = new Rectangle((Main.WIDTH_OF_WINDOW - (fillMenu.getWidth()) - 2 * 70) / 5.5, Main.HEIGHT_OF_WINDOW / 2.3);
            try {
                if (i >= 70)
                    demoCards[i].setFill(new ImagePattern(new Image(new FileInputStream("item_template.png"))));
                else if (CardMaker.getAllCards()[i] instanceof Hero)
                    demoCards[i].setFill(new ImagePattern(new Image(new FileInputStream("hero_template.png"))));
                else if ((CardMaker.getAllCards()[i] instanceof Minion))
                    demoCards[i].setFill(new ImagePattern(new Image(new FileInputStream("minion_template.png"))));
                else demoCards[i].setFill(new ImagePattern(new Image(new FileInputStream("spell_template.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            demoCards[i].setOpacity(0.7);

        }
        for (int i = 0; i < shownCards.length; i++) {
            for (int j = 0; j < shownCards[i].length; j++) {
                shownCards[i][j] = demoCards[(5 * i) + j];
                Spell spell = (Spell) CardMaker.getAllCards()[(5 * i) + j];
                shownData[i][j] = new CardsDataAppearance(spell.getName().toUpperCase(), Integer.toString(spell.getPrice()), Integer.toString(spell.getManaPoint()));
            }
        }
        for (int i = 0; i < 2; i++) {
            System.arraycopy(demoCards, (5 * i), shownCards[i], 0, 5);
        }

        for (int i = 0; i < outBox.length; i++) {
            for (int j = 0; j < outBox[i].length; j++) {
                outBox[i][j] = new Rectangle((Main.WIDTH_OF_WINDOW - (fillMenu.getWidth()) - 2 * 70) / 5, Main.HEIGHT_OF_WINDOW / 2.17);
                outBox[i][j].setFill(ColorAppearance.BACKGROUND_DATA_CARDS);
                outBox[i][j].setOpacity(0.7);
                outBox[i][j].setVisible(false);
            }
        }
    }

    private void initializeCards() {
        for (Text title : titles) {
            title.setFont(FontAppearance.FONT_SHOP_BUTTONS);
            title.setFill(Color.WHITE);
        }
        rightDirection.setOpacity(0.4);
        leftDirection.setOpacity(0.4);
        rightDirection.setOnMouseEntered(e -> rightDirection.setOpacity(1));
        rightDirection.setOnMouseExited(e -> rightDirection.setOpacity(0.4));
        leftDirection.setOnMouseEntered(e -> leftDirection.setOpacity(1));
        leftDirection.setOnMouseExited(e -> leftDirection.setOpacity(0.4));
    }

    private void setBackGround() {
        Image image;
        try {
            image = new Image(new FileInputStream("purpleBackGrouund.jpg"));
            ImageView imageOfBackGround = new ImageView(image);
            imageOfBackGround.fitWidthProperty().bind(shopScene.widthProperty());
            imageOfBackGround.fitHeightProperty().bind(shopScene.heightProperty());
            imageOfBackGround.setOpacity(0.8);
            fillMenu.setOpacity(0.6);
            root.getChildren().add(imageOfBackGround);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addNodes() {
        root.getChildren().add(fillMenu);
        root.getChildren().addAll(titles);
        for (Rectangle[] box : outBox)
            root.getChildren().addAll(box);

        for (Rectangle[] totalCard : shownCards)
            root.getChildren().addAll(totalCard);

        root.getChildren().addAll(rightDirection, leftDirection, currentPageView, coinsImage, moneyValue);
        currentPageView.setText("page : ".concat(Integer.toString(Math.abs(currentPage + 1))));
    }

    private void locateNodes() {
        locateTitles();
        locateShownCards();
        locateDirections();
        locateData();
    }

    private void locateDirections() {
        double x = shopScene.getWidth() * 28 / 29;
        double y = 18.5 * shopScene.getHeight() / 40;
        rightDirection.setLayoutX(x);
        rightDirection.setLayoutY(y);
        leftDirection.setLayoutX(shopScene.getWidth() * 3.2 / 31);
        leftDirection.setLayoutY(y);
        coinsImage.setLayoutX(Main.WIDTH_OF_WINDOW / 45);
        coinsImage.setLayoutY(12 * Main.HEIGHT_OF_WINDOW / 13);
        moneyValue.setLayoutX(coinsImage.getLayoutX() + Main.WIDTH_OF_WINDOW / 38);
        moneyValue.setLayoutY(coinsImage.getLayoutY() + Main.HEIGHT_OF_WINDOW / 40);
    }

    private void locateTitles() {
        for (int i = 0; i < titles.length; i++) {
            if (i == 0) {
                titles[i].setLayoutX(Main.HEIGHT_OF_WINDOW / 50);
                titles[i].setLayoutY(Main.WIDTH_OF_WINDOW / 25);
                continue;
            }
            titles[i].setLayoutX(titles[i - 1].getLayoutX());
            titles[i].setLayoutY(titles[i - 1].getLayoutY() + Main.HEIGHT_OF_WINDOW / 7);
        }
        currentPageView.setLayoutX(Main.WIDTH_OF_WINDOW / 50);
        currentPageView.setLayoutY(5 * Main.HEIGHT_OF_WINDOW / 6);
        currentPageView.setFont(FontAppearance.FONT_BUTTON);
        currentPageView.setFill(Color.WHITE);
        moneyValue.setFont(FontAppearance.FONT_MONEY_VALUE);
        moneyValue.setFill(ColorAppearance.GOLD_COLOR);
    }

    private void locateShownCards() {
        for (int i = 0; i < shownCards.length; i++) {
            for (int j = 0; j < shownCards[i].length; j++) {
                if (j == 0) {
                    if (i == 0) {
                        shownCards[i][j].setLayoutX(fillMenu.getWidth() + Main.WIDTH_OF_WINDOW / 45);
                        shownCards[i][j].setLayoutY(fillMenu.getHeight() / 45);
                        outBox[i][j].setLayoutX(shownCards[i][j].getLayoutX());
                        outBox[i][j].setLayoutY(shownCards[i][j].getLayoutY());
                        continue;
                    }
                    shownCards[i][j].setLayoutX(shownCards[i - 1][j].getLayoutX());
                    shownCards[i][j].setLayoutY(shownCards[i - 1][j].getLayoutY() + Main.HEIGHT_OF_WINDOW / 2);
                    outBox[i][j].setLayoutX(shownCards[i][j].getLayoutX());
                    outBox[i][j].setLayoutY(shownCards[i][j].getLayoutY());
                    continue;
                }
                shownCards[i][j].setLayoutY(shownCards[i][j - 1].getLayoutY());
                shownCards[i][j].setLayoutX(shownCards[i][j - 1].getLayoutX() + Main.WIDTH_OF_WINDOW / 6);
                outBox[i][j].setLayoutX(shownCards[i][j].getLayoutX());
                outBox[i][j].setLayoutY(shownCards[i][j].getLayoutY());
            }
        }
    }

    private void setMouse() {
        try {
            shopScene.setCursor(new ImageCursor(new Image(new FileInputStream("sword1.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void display() {
        Main.getWindow().setScene(shopScene);
        handleEvents();
    }

    private void handleEvents() {
        rightDirection.setOnMouseClicked(event -> {
            int size = demoCards.length / 10;
            currentPage = Math.abs((currentPage + 1) % size);
            changeCards();
        });
        leftDirection.setOnMouseClicked(event -> {
            int size = demoCards.length / 10;
            currentPage = Math.abs((currentPage + size - 1) % size);
            changeCards();
        });

        shopScene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.RIGHT)) {
                int size = demoCards.length / 10;
                currentPage = Math.abs((currentPage + 1) % size);
                changeCards();
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                int size = demoCards.length / 10;
                currentPage = Math.abs((currentPage + size - 1) % size);
                changeCards();
            }
        });

        for (int i = 0; i < demoCards.length; i++) {
            final Rectangle temp = demoCards[i];
            final int value = i % 10;
            demoCards[i].setOnMouseEntered(e -> {
                temp.setOpacity(1);
                shownData[value / 5][value % 5].light();
                outBox[value / 5][value % 5].setVisible(true);
            });
            demoCards[i].setOnMouseExited(e -> {
                temp.setOpacity(0.7);
                shownData[value / 5][value % 5].dark();
                outBox[value / 5][value % 5].setVisible(false);
            });
        }
    }

    private void changeCards() {
        for (int i = 0; i < shownCards.length; i++) {
            root.getChildren().removeAll(shownCards[i]);
            for (int j = 0; j < shownCards[i].length; j++) {
                shownData[i][j].removeAll(root);
            }
        }
        for (int i = 0; i < shownCards.length; i++) {
            for (int j = 0; j < shownCards[i].length; j++) {
                shownCards[i][j] = demoCards[(currentPage * 10) + (5 * i) + j];
                if ((currentPage * 10) + (5 * i) + j >= 70) {
                    Item item = CardMaker.getAllItems()[(currentPage * 10) + (5 * i) + j - 70];
                    shownData[i][j] = new CardsDataAppearance(item.getName().toUpperCase(), Integer.toString(item.getPrice()), "0");
                }
                else if (CardMaker.getAllCards()[(currentPage * 10) + (5 * i) + j] instanceof Spell) {
                    Spell spell = (Spell) CardMaker.getAllCards()[(currentPage * 10) + (5 * i) + j];
                    shownData[i][j] = new CardsDataAppearance(spell.getName().toUpperCase(), Integer.toString(spell.getPrice()), Integer.toString(spell.getManaPoint()));
                } else {
                    Minion minion = (Minion) CardMaker.getAllCards()[(currentPage * 10) + (5 * i) + j];
                    shownData[i][j] = new CardsDataAppearance(minion.getName().toUpperCase(), Integer.toString(minion.getPrice()), Integer.toString(minion.getManaPoint()), Integer.toString(minion.getAttackPoint()), Integer.toString(minion.getHealthPoint()));
                }
            }
            root.getChildren().addAll(shownCards[i]);
        }
        currentPageView.setText("Page : ".concat(Integer.toString(Math.abs(currentPage + 1))));
        locateShownCards();
        locateNodes();
    }

    private void locateData() {
        double cardWidth = shownCards[0][0].getWidth();
        double cardHeight = shownCards[0][0].getHeight();
        for (int i = 0; i < shownData.length; i++) {
            for (int j = 0; j < shownData[i].length; j++) {

                shownData[i][j].addAll(root);
                shownData[i][j].getNameView().setLayoutX(shownCards[i][j].getLayoutX() + 1 * cardWidth / 10);
                shownData[i][j].getNameView().setLayoutY(shownCards[i][j].getLayoutY() + 4 * cardHeight / 5);

                shownData[i][j].getMpView().setLayoutX((shownCards[i][j].getLayoutX()) + cardWidth / 14.1);
                shownData[i][j].getMpView().setLayoutY((shownCards[i][j].getLayoutY()) + cardHeight / 9.9);

                shownData[i][j].getPriceView().setLayoutX(shownCards[i][j].getLayoutX() + cardWidth / 2.3);
                shownData[i][j].getPriceView().setLayoutY(shownCards[i][j].getLayoutY() + 10 * cardHeight / 11);

                if (shownData[i][j].getApView() != null) {
                    shownData[i][j].getApView().setLayoutX(shownCards[i][j].getLayoutX() + cardWidth / 4.5);
                    shownData[i][j].getApView().setLayoutY(shownCards[i][j].getLayoutY() + cardHeight / 1.55);

                    shownData[i][j].getHpView().setLayoutX(shownData[i][j].getApView().getLayoutX() + cardWidth / 2.1);
                    shownData[i][j].getHpView().setLayoutY(shownData[i][j].getApView().getLayoutY());
                }
            }
        }
    }
}
