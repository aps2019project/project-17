import Appearance.CardsDataAppearance;
import Appearance.ColorAppearance;
import Appearance.FontAppearance;
import Cards.*;
import Data.Account;
import InstanceMaker.CardMaker;
import controller.GameController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
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
    private Rectangle[] allProducts = new Rectangle[CardMaker.getAllCards().length + CardMaker.getAllItems().length];
    private Rectangle fillMenu = new Rectangle(Main.WIDTH_OF_WINDOW / 10, Main.HEIGHT_OF_WINDOW);
    private Rectangle outBoxOfSearch = new Rectangle(85, 30);
    private Rectangle currentSelectedRectangle;
    private CardsDataAppearance[][] shownData = new CardsDataAppearance[2][5];
    private CardsDataAppearance searchAppearance;
    private ImageView backIcon;
    private ImageView rightDirection;
    private ImageView leftDirection;
    private ImageView coinsImage;
    private Text[] titles = {new Text("HEROES"), new Text("MINIONS"), new Text("SPELLS"), new Text("ITEMS")};
    private Text search = new Text("Search");
    private Text currentPageView = new Text();
    private Text moneyValue = new Text(Integer.toString(Account.getLoginUser().getDaric()));
    private TextField toSearch = new TextField();
    private Text notFound = new Text("Card not Found!");
    private int currentPage = 0;

    {
        try {
            rightDirection = new ImageView(new Image(new FileInputStream("arrowright.png")));
            leftDirection = new ImageView(new Image(new FileInputStream("leftarrow.png")));
            coinsImage = new ImageView(new Image(new FileInputStream("coins.png")));
            backIcon = new ImageView(new Image(new FileInputStream("icon.png")));
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
        setSearchingAppearance();
        display();
    }

    private void initializeDemoCardsAndShownCards() {
        initializeProducts();
        initializeShownCards();
        initializeOutBox();
        currentPageView.setText("page : ".concat(Integer.toString(Math.abs(currentPage + 1))));
    }

    private void initializeOutBox() {
        for (int i = 0; i < outBox.length; i++) {
            for (int j = 0; j < outBox[i].length; j++) {
                outBox[i][j] = new Rectangle((Main.WIDTH_OF_WINDOW - (fillMenu.getWidth()) - 2 * 70) / 5 - 8, Main.HEIGHT_OF_WINDOW / 2.17 - 5);
                outBox[i][j].setFill(ColorAppearance.BACKGROUND_DATA_CARDS);
                outBox[i][j].setOpacity(0.7);
                outBox[i][j].setVisible(false);
            }
        }
    }

    private void initializeShownCards() {
        for (int i = 0; i < shownCards.length; i++) {
            for (int j = 0; j < shownCards[i].length; j++) {
                shownCards[i][j] = allProducts[(5 * i) + j];
                Spell spell = (Spell) CardMaker.getAllCards()[(5 * i) + j];
                shownData[i][j] = new CardsDataAppearance(spell.getName().toUpperCase(), Integer.toString(spell.getPrice()), Integer.toString(spell.getManaPoint()));
            }
        }
    }

    private void initializeProducts() {
        for (int i = 0; i < allProducts.length; i++) {
            allProducts[i] = new Rectangle((Main.WIDTH_OF_WINDOW - (fillMenu.getWidth()) - 2 * 70) / 5.5, Main.HEIGHT_OF_WINDOW / 2.3);
            try {
                if (i >= 70)
                    allProducts[i].setFill(new ImagePattern(new Image(new FileInputStream("item_template.png"))));
                else if (CardMaker.getAllCards()[i] instanceof Hero)
                    allProducts[i].setFill(new ImagePattern(new Image(new FileInputStream("hero_template.png"))));
                else if ((CardMaker.getAllCards()[i] instanceof Minion))
                    allProducts[i].setFill(new ImagePattern(new Image(new FileInputStream("minion_template.png"))));
                else allProducts[i].setFill(new ImagePattern(new Image(new FileInputStream("spell_template.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            allProducts[i].setOpacity(0.7);

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

    private void setSearchingAppearance() {
        toSearch.setMaxWidth(fillMenu.getWidth());
        notFound.setFont(FontAppearance.FONT_NOT_FOUND);
        notFound.setFill(Color.RED);
        search.setFont(FontAppearance.FONT_SEARCH_SHOP);
        search.setFill(Color.BLACK);
        outBoxOfSearch.setOpacity(0.7);
        outBoxOfSearch.setOnMouseEntered(e -> outBoxOfSearch.setOpacity(1));
        outBoxOfSearch.setOnMouseExited(e -> outBoxOfSearch.setOpacity(0.7));
        search.setOnMouseEntered(e -> outBoxOfSearch.setOpacity(1));
        outBoxOfSearch.setFill(ColorAppearance.COLOR_OUTBOX_SEARCH_SHOP);
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
        root.getChildren().addAll(rightDirection, leftDirection, currentPageView, coinsImage, moneyValue, toSearch, backIcon, outBoxOfSearch, search);
    }

    private void locateNodes() {
        locateTitles();
        locateShownCards();
        locateDirections();
        LocateSearchOptions();
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

    private void LocateSearchOptions() {
        toSearch.setLayoutX(0);
        toSearch.setLayoutY(5.2 * Main.HEIGHT_OF_WINDOW / 13);
        backIcon.setLayoutX(fillMenu.getWidth() / 3);
        backIcon.setLayoutY(fillMenu.getWidth() / 3);
        backIcon.setFitWidth(fillMenu.getWidth() / 3);
        backIcon.setFitHeight(fillMenu.getWidth() / 3);
        backIcon.setOpacity(0.5);
        search.setLayoutX(3.25 * fillMenu.getWidth() / 10);
        search.setLayoutY(6 * Main.HEIGHT_OF_WINDOW / 13);
        outBoxOfSearch.setLayoutX(6.5 * search.getLayoutX() / 10);
        outBoxOfSearch.setLayoutY(9.5 * search.getLayoutY() / 10);
        notFound.setLayoutX(0);
        notFound.setLayoutY(outBoxOfSearch.getLayoutY() + 1.5 * outBoxOfSearch.getHeight());
    }

    private void locateTitles() {
        for (int i = 0; i < titles.length; i++) {
            if (i == 0) {
                titles[i].setLayoutX(Main.HEIGHT_OF_WINDOW / 50);
                titles[i].setLayoutY(Main.WIDTH_OF_WINDOW / 10);
                continue;
            }
            titles[i].setLayoutX(titles[i - 1].getLayoutX());
            titles[i].setLayoutY(titles[i - 1].getLayoutY() + Main.HEIGHT_OF_WINDOW / 20);
        }
        currentPageView.setLayoutX(Main.WIDTH_OF_WINDOW / 50);
        currentPageView.setLayoutY(7 * Main.HEIGHT_OF_WINDOW / 8);
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

    private void display() {
        Main.getWindow().setScene(shopScene);
        handleEvents();
        changeColor();
    }

    private void handleEvents() {
        handleEventsSearch();
        handleEventsDirection();
        handleEventsTitles();
        handleEventsKeyBoards();
        handleEventsCards();
        handleEventBack();
    }

    private void handleEventsSearch() {
        search.setOnMouseClicked(e -> searchLogic());
        outBoxOfSearch.setOnMouseClicked(e -> searchLogic());
        toSearch.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case RIGHT:
                    int size = allProducts.length / 10;
                    currentPage = Math.abs((currentPage + 1) % size);
                    changeCards();
                    break;
                case LEFT:
                    size = allProducts.length / 10;
                    currentPage = Math.abs((currentPage + size - 1) % size);
                    changeCards();
            }
        });
    }

    private void handleEventsCards() {
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

    private void handleEventsKeyBoards() {
        shopScene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.RIGHT)) {
                int size = allProducts.length / 10;
                currentPage = Math.abs((currentPage + 1) % size);
                changeCards();
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                int size = allProducts.length / 10;
                currentPage = Math.abs((currentPage + size - 1) % size);
                changeCards();
            } else if (event.getCode().equals(KeyCode.ESCAPE)) {
                new MainMenu();
            }
        });
    }

    private void handleEventsTitles() {
        for (Text title : titles)
            title.setOnMouseEntered(event -> title.setFill(Color.rgb(178, 46, 90, 1)));
        for (Text title : titles)
            title.setOnMouseExited(event -> title.setFill(Color.WHITE));
        titles[0].setOnMouseClicked(event -> {
            currentPage = 6;
            changeCards();
            changeColor();
        });
        titles[1].setOnMouseClicked(event -> {
            currentPage = 2;
            changeCards();
            changeColor();
        });
        titles[2].setOnMouseClicked(event -> {
            currentPage = 0;
            changeCards();
            changeColor();
        });
        titles[3].setOnMouseClicked(event -> {
            currentPage = 7;
            changeCards();
            changeColor();
        });
    }

    private void handleEventBack() {
        backIcon.setOnMouseEntered(event -> backIcon.setOpacity(1));
        backIcon.setOnMouseExited(event -> backIcon.setOpacity(0.5));
        backIcon.setOnMouseClicked(event -> new MainMenu());
    }

    private void handleEventsDirection() {
        rightDirection.setOnMouseClicked(event -> {
            int size = allProducts.length / 10;
            currentPage = Math.abs((currentPage + 1) % size);
            changeCards();
        });
        leftDirection.setOnMouseClicked(event -> {
            int size = allProducts.length / 10;
            currentPage = Math.abs((currentPage + size - 1) % size);
            changeCards();
        });
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
                shownCards[i][j] = allProducts[(currentPage * 10) + (5 * i) + j];
                if ((currentPage * 10) + (5 * i) + j >= 70) {
                    Item item = CardMaker.getAllItems()[(currentPage * 10) + (5 * i) + j - 70];
                    shownData[i][j] = new CardsDataAppearance(item.getName().toUpperCase(), Integer.toString(item.getPrice()), "0");
                } else if (CardMaker.getAllCards()[(currentPage * 10) + (5 * i) + j] instanceof Spell) {
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
        changeColor();
        if (currentSelectedRectangle != null)
            currentSelectedRectangle.setVisible(false);
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

    private void changeColor() {
        if (currentPage == 0 || currentPage == 1) {
            titles[2].setFill(ColorAppearance.COLOR_TITLES_OF_SHOP);
            titles[0].setFill(Color.WHITE);
            titles[1].setFill(Color.WHITE);
            titles[3].setFill(Color.WHITE);
        } else if (currentPage >= 2 && currentPage <= 5) {
            titles[1].setFill(ColorAppearance.COLOR_TITLES_OF_SHOP);
            titles[0].setFill(Color.WHITE);
            titles[2].setFill(Color.WHITE);
            titles[3].setFill(Color.WHITE);
        } else if (currentPage == 6) {
            titles[0].setFill(ColorAppearance.COLOR_TITLES_OF_SHOP);
            titles[2].setFill(Color.WHITE);
            titles[1].setFill(Color.WHITE);
            titles[3].setFill(Color.WHITE);
        } else {
            titles[3].setFill(ColorAppearance.COLOR_TITLES_OF_SHOP);
            titles[2].setFill(Color.WHITE);
            titles[1].setFill(Color.WHITE);
            titles[0].setFill(Color.WHITE);
        }
    }

    private void searchLogic() {
        root.getChildren().removeAll(notFound);
        if (searchAppearance != null)
            searchAppearance.removeAll(root, 0);
        String name = toSearch.getText();
        if (name.equals(""))
            return;
        String result = CardMaker.returnSearch(name);
        if (result.equals("-1")) {
            root.getChildren().add(notFound);
            return;
        } else {
            String isInCollection = GameController.search(name, Account.getLoginUser().getCollection());
            if (isInCollection.equals("can't find this card\\item")) {
                isInCollection = "You don't have this card";
            } else {
                isInCollection = "You have this card";
            }
            if (GameController.getCardFromId(result, Account.getLoginUser().getShop()) != null) {//the searched thing is a card
                Card card = GameController.getCardFromId(result, Account.getLoginUser().getShop());
                if (card instanceof Hero) {
                    searchAppearance = new CardsDataAppearance(new Text("Hero"), new Text("ID =  " + card.getId()), new Text(isInCollection));
                } else if (card instanceof Minion) {
                    searchAppearance = new CardsDataAppearance(new Text("Minion"), new Text("ID =  " + card.getId()), new Text(isInCollection));
                } else if (card instanceof Spell) {
                    searchAppearance = new CardsDataAppearance(new Text("Spell"), new Text("ID =  " + card.getId()), new Text(isInCollection));
                }
            } else if (GameController.getItemFromId(result, Account.getLoginUser().getShop()) != null) {//the searched thing is an item
                Item item = GameController.getItemFromId(result, Account.getLoginUser().getShop());
                searchAppearance = new CardsDataAppearance(new Text("Item"), new Text("ID =  " + item.getId()), new Text(isInCollection));
            }
        }
        searchAppearance.addAll(root, 0, outBoxOfSearch.getLayoutY() + 1.5 * outBoxOfSearch.getHeight());
    }
}
