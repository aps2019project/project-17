import Appearance.CardsDataAppearance;
import Appearance.FontAppearance;
import Cards.*;
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

public class GraveYardAppearance {

    private Stage window = new Stage();
    private Group root = new Group();
    private Scene graveYardScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
    private Rectangle[][] shownCards = new Rectangle[2][5];
    private Rectangle[] cardsOfUser = new Rectangle[Account.getLoginUser().getPlayer().getGraveYard().size()];
    private Rectangle fillMenu = new javafx.scene.shape.Rectangle(Main.WIDTH_OF_WINDOW / 10, Main.HEIGHT_OF_WINDOW);
    private CardsDataAppearance[][] shownData = new CardsDataAppearance[2][5];
    private ImageView rightDirection;
    private ImageView leftDirection;
    private int currentPage = 0;
    private Text title=new Text("Grave Yard");
    private Text currentPageView = new Text("page : 1");


    public GraveYardAppearance() {
        setTitleAppearance();
        initializeAllCards();
        initShownCards();
        setBackGround();
        setDirectionsAction();
        addNodes();
        locateNodes();
        disPlay();
    }

    {
        try {
            rightDirection = new ImageView(new Image(new FileInputStream("arrowright.png")));
            leftDirection = new ImageView(new Image(new FileInputStream("leftarrow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setTitleAppearance(){
        title.setFont(Font.font("phosphate",80));
        title.setFill(Color.WHITE);
        title.setRotate(-90);
        title.setLayoutX(-fillMenu.getWidth());
        title.setLayoutY(fillMenu.getHeight()/2);

    }

    private void initializeAllCards() {
        ArrayList<Card> cards = Account.getLoginUser().getPlayer().getGraveYard();
        for (int i = 0; i < cardsOfUser.length; i++) {
            cardsOfUser[i] = new Rectangle((Main.WIDTH_OF_WINDOW - (fillMenu.getWidth()) - 2 * 70) / 5.5, Main.HEIGHT_OF_WINDOW / 2.3);
            if (i < cards.size()) {
                try {
                    if (cards.get(i) instanceof Hero)
                        cardsOfUser[i].setFill(new ImagePattern(new Image(new FileInputStream("product1.png"))));
                    else if (cards.get(i) instanceof Spell)
                        cardsOfUser[i].setFill(new ImagePattern(new Image(new FileInputStream("product3.png"))));
                    else if (cards.get(i) instanceof Minion)
                        cardsOfUser[i].setFill(new ImagePattern(new Image(new FileInputStream("product2.png"))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initShownCards() {
        ArrayList<Card> cards = new ArrayList<>(Account.getLoginUser().getPlayer().getGraveYard());
        for (int i = 0; i < shownCards.length; i++) {
            for (int j = 0; j < shownCards[i].length; j++) {
                if ((5 * i) + j >= cardsOfUser.length)
                    return;
                shownCards[i][j] = cardsOfUser[(5 * i) + j];
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
            image = new Image(new FileInputStream("graveYard.jpg"));
            ImageView imageOfBackGround = new ImageView(image);
            imageOfBackGround.fitWidthProperty().bind(graveYardScene.widthProperty());
            imageOfBackGround.fitHeightProperty().bind(graveYardScene.heightProperty());
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
        for (Rectangle[] shownCard : shownCards)
            for (Rectangle rectangle : shownCard) {
                if (rectangle == null)
                    continue;
                root.getChildren().add(rectangle);
            }
        root.getChildren().addAll(rightDirection, leftDirection, currentPageView,title);
    }

    private void locateNodes() {
        locateShownCards();
        locateDirections();
        locateData();
    }

    private void locateDirections() {
        double y = 18.5 * graveYardScene.getHeight() / 40;
        double x = graveYardScene.getWidth() * 28 / 29;
        leftDirection.setLayoutX(graveYardScene.getWidth() * 3.2 / 31);
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
                        continue;
                    }
                    shownCards[i][j].setLayoutX(shownCards[i - 1][j].getLayoutX());
                    shownCards[i][j].setLayoutY(shownCards[i - 1][j].getLayoutY() + Main.HEIGHT_OF_WINDOW / 2);
                    continue;
                }
                shownCards[i][j].setLayoutY(shownCards[i][j - 1].getLayoutY());
                shownCards[i][j].setLayoutX(shownCards[i][j - 1].getLayoutX() + Main.WIDTH_OF_WINDOW / 6);
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

    private void disPlay() {
        window.setScene(graveYardScene);
        window.showAndWait();
        handleEventsKeyBoards();
    }

    private void handleEventsKeyBoards() {
        int sizeVariable = cardsOfUser.length / 10;
        if (cardsOfUser.length % 10 != 0)
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
        graveYardScene.setOnKeyPressed(event -> {
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

        ArrayList<Card> cards = Account.getLoginUser().getCollection().getCards();
        for (int i = 0; i < shownCards.length; i++) {
            for (int j = 0; j < shownCards[i].length; j++) {
                if ((currentPage * 10) + (5 * i) + j >= cardsOfUser.length)
                    continue;
                shownCards[i][j] = cardsOfUser[(currentPage * 10) + (5 * i) + j];
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
        locateNodes();
    }
}
