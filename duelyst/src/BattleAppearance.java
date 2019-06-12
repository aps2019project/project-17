import Appearance.ColorAppearance;
import Appearance.FontAppearance;
import Appearance.MinionAppearance;
import Cards.Card;
import Cards.Hero;
import Cards.Minion;
import Data.AI;
import Data.Account;
import GameGround.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BattleAppearance {
    private Group root;
    private Scene battleScene;
    private CellAppearance[][] board;
    private Rectangle[][] boardBackGround;
    private Rectangle[] shapeOfHealthHero = new Rectangle[2];
    private Text[] textsOfBattle;
    private Rectangle[] manaIconImage;
    private Rectangle endTurnButton;
    private Rectangle graveYardButton;
    private HandAppearance handAppearance;
    private ArrayList<MinionAppearance> minionAppearanceOfBattle = new ArrayList<>();
    private Cell currentSelectedCell;
    private ItemAppearance itemAppearance;
    private Cell selectedCell;
    private ArrayList<MinionAppearance> minionAppearanceFirstPlayer = new ArrayList<>();
    private ArrayList<MinionAppearance> minionAppearanceSecondPlayer = new ArrayList<>();
    private static BattleAppearance currentBattleAppearance;

    public BattleAppearance() {
        currentBattleAppearance = this;
        primaryInitializes();
        setBackGround();
        secondaryInitializes();
        setStuffs();
        disPlay();
        handleEvents();
    }

    private void primaryInitializes() {
        this.root = new Group();
        this.battleScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
        this.board = new CellAppearance[5][9];
        this.boardBackGround = new Rectangle[5][9];
        this.textsOfBattle = new Text[]{
                new Text("End Turn"),
                new Text("Pooya"),
                new Text(AI.getCurrentAIPlayer().getUserName()),
                new Text(Integer.toString(Battle.getCurrentBattle().getPlayerTwo().getMana()).concat("  /  9")),
                new Text(Integer.toString(Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getHealthPoint())),
                new Text((Integer.toString(Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero().getHealthPoint()))),
                new Text("Grave Yard")};
        this.textsOfBattle = new Text[]{new Text("End Turn"), new Text(Battle.getCurrentBattle().getPlayerOne().getUserName()), new Text(AI.getCurrentAIPlayer().getUserName()), new Text(Integer.toString(Battle.getCurrentBattle().getPlayerTwo().getMana()).concat("  /  9")),
                new Text(Integer.toString(Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getHealthPoint())), new Text((Integer.toString(Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero().getHealthPoint())))};
        this.manaIconImage = new Rectangle[9];
        this.currentSelectedCell = null;
    }

    private void setBackGround() {
        Image image;
        try {
            image = new Image(new FileInputStream("board5.png"));
            ImageView imageOfBackGround = new ImageView(image);
            imageOfBackGround.fitWidthProperty().bind(battleScene.widthProperty());
            imageOfBackGround.fitHeightProperty().bind(battleScene.heightProperty());
            root.getChildren().add(imageOfBackGround);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void secondaryInitializes() {
        initBoardBG();
        initializeCells();
        initPlaceOfHeroes();
        locateNodes();
        initPlayersAppearance();
        initHand();
        addCells();
        initItemList();
    }


    private void setStuffs() {
        setEndTurn_GraveYardButton();
        setImagesOfPlayers();
        setShapeOfHealthHero();
        setManaIcons();
        setAppearanceOfTexts();
    }

    private void initBoardBG() {
        for (int i = 0; i < boardBackGround.length; i++) {
            for (int j = 0; j < boardBackGround[i].length; j++) {
                boardBackGround[i][j] = new Rectangle(Main.WIDTH_OF_WINDOW / 17, Main.HEIGHT_OF_WINDOW / 10);
                boardBackGround[i][j].setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
                boardBackGround[i][j].setOpacity(0.2);
                if (j == 0) {
                    if (i == 0) {
                        boardBackGround[i][j].setLayoutX(Main.WIDTH_OF_WINDOW / 4.6);
                        boardBackGround[i][j].setLayoutY(Main.HEIGHT_OF_WINDOW / 4);
                    } else {
                        boardBackGround[i][j].setLayoutX(boardBackGround[i - 1][j].getLayoutX());
                        boardBackGround[i][j].setLayoutY(boardBackGround[i - 1][j].getLayoutY() + Main.HEIGHT_OF_WINDOW / 9.2);
                    }
                } else {
                    boardBackGround[i][j].setLayoutX(boardBackGround[i][j - 1].getLayoutX() + Main.WIDTH_OF_WINDOW / 16.75);
                    boardBackGround[i][j].setLayoutY(boardBackGround[i][j - 1].getLayoutY());
                }
            }
            root.getChildren().addAll(boardBackGround[i]);
        }
    }


    private void initHand() {
        this.handAppearance = new HandAppearance(this.root);
    }

    private void initItemList(){
        this.itemAppearance=new ItemAppearance(this.root);
    }

    private void initializeCells() {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = new CellAppearance(Battle.getCurrentBattle().getBoard().getCells()[i][j]);

    }

    private void initPlaceOfHeroes() {
        Hero heroFirst = Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero();
        Hero heroSecond = Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero();
        MinionAppearance a = new MinionAppearance(heroFirst, heroFirst.getName(), root);
        MinionAppearance b = new MinionAppearance(heroSecond, heroSecond.getName(), root);
        minionAppearanceOfBattle.add(a);
        minionAppearanceOfBattle.add(b);
    }

    private void locateNodes() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].getCellRectangle().setLayoutX(boardBackGround[i][j].getLayoutX());
                board[i][j].getCellRectangle().setLayoutY(boardBackGround[i][j].getLayoutY());
            }
        }
    }

    private void initPlayersAppearance() {
        for (int i = 0; i < Battle.getCurrentBattle().getPlayerOne().getMainDeck().getCards().size(); i++) {
            Card card = Battle.getCurrentBattle().getPlayerOne().getMainDeck().getCards().get(i);
            if (card instanceof Minion)
                minionAppearanceOfBattle.add(new MinionAppearance((Minion) card, card.getName(), root));
        }
        for (int i = 0; i < Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getCards().size(); i++) {
            Card card = Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getCards().get(i);
            if (card instanceof Minion)
                minionAppearanceOfBattle.add(new MinionAppearance((Minion) card, card.getName(), root));
        }
    }

    private void addCells() {
        for (CellAppearance[] cellAppearances : board)
            for (CellAppearance cellAppearance : cellAppearances)
                cellAppearance.add(root);
    }

    private void setEndTurn_GraveYardButton() {
        endTurnButton = new Rectangle(Main.WIDTH_OF_WINDOW / 7.58, Main.HEIGHT_OF_WINDOW / 11.12);
        graveYardButton = new Rectangle(Main.WIDTH_OF_WINDOW / 7.58, Main.HEIGHT_OF_WINDOW / 11.12);
        textsOfBattle[6].setFont(FontAppearance.FONT_BUTTON);
        textsOfBattle[6].setFill(Color.WHITE);
        try {
            endTurnButton.setFill(new ImagePattern(new Image(new FileInputStream("end_turn.png"))));
            StackPane stackPane0 = new StackPane(endTurnButton, textsOfBattle[0]);
            stackPane0.setLayoutX(Main.WIDTH_OF_WINDOW * 8 / 10);
            stackPane0.setLayoutY(Main.HEIGHT_OF_WINDOW * 8.5 / 10);
            root.getChildren().addAll(stackPane0);
            graveYardButton.setFill(new ImagePattern(new Image(new FileInputStream("button.png"))));
            StackPane stackPane = new StackPane(graveYardButton, textsOfBattle[6]);
            stackPane.setLayoutX(Main.WIDTH_OF_WINDOW * 8 / 10);
            stackPane.setLayoutY(Main.HEIGHT_OF_WINDOW * 7.5 / 10);
            stackPane.setMaxWidth(stackPane0.getMinWidth());
            stackPane.setMaxHeight(stackPane0.getMinHeight());
            root.getChildren().addAll(stackPane);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void setImagesOfPlayers() {
        Rectangle rectangle1 = new Rectangle(Main.WIDTH_OF_WINDOW / 9, Main.HEIGHT_OF_WINDOW / 4.77);
        Rectangle rectangle2 = new Rectangle(Main.WIDTH_OF_WINDOW / 9, Main.HEIGHT_OF_WINDOW / 4.77);
        try {
            rectangle1.setFill(new ImagePattern(new Image(new FileInputStream("player1.png"))));
            rectangle2.setFill(new ImagePattern(new Image(new FileInputStream("player2.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        rectangle1.setLayoutX(Main.WIDTH_OF_WINDOW / 40);
        rectangle1.setLayoutY(Main.HEIGHT_OF_WINDOW / 200);
        rectangle2.setLayoutX(Main.WIDTH_OF_WINDOW * 8.5 / 10);
        rectangle2.setLayoutY(Main.HEIGHT_OF_WINDOW / 200);
        root.getChildren().addAll(rectangle1, rectangle2);
    }

    private void setShapeOfHealthHero() {
        shapeOfHealthHero[0] = new Rectangle(40, 40);
        shapeOfHealthHero[1] = new Rectangle(40, 40);
        try {
            for (int i = 0; i < shapeOfHealthHero.length; i++) {
                shapeOfHealthHero[i].setFill(new ImagePattern(new Image(new FileInputStream("hp_hero.png"))));
                if (i == 0) {
                    shapeOfHealthHero[0].setLayoutX(Main.WIDTH_OF_WINDOW / 14.85);
                    shapeOfHealthHero[0].setLayoutY(Main.HEIGHT_OF_WINDOW / 5.3);
                } else {
                    shapeOfHealthHero[1].setLayoutX(13.25 * Main.WIDTH_OF_WINDOW / 14.85);
                    shapeOfHealthHero[1].setLayoutY(Main.HEIGHT_OF_WINDOW / 5.3);
                }
            }
            root.getChildren().addAll(shapeOfHealthHero);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setManaIcons() {
        for (int i = 0; i < manaIconImage.length; i++) {
            try {
                manaIconImage[i] = new Rectangle(Main.WIDTH_OF_WINDOW / 48, Main.HEIGHT_OF_WINDOW / 27.8);
                manaIconImage[i].setFill(new ImagePattern(new Image(new FileInputStream("icon_mana2.png"))));
                manaIconImage[i].setLayoutY(Main.HEIGHT_OF_WINDOW / 8.5);
                manaIconImage[i].setOpacity(0.3);
                manaIconImage[i].setLayoutX(Main.WIDTH_OF_WINDOW / 8 + (i * Main.WIDTH_OF_WINDOW / 48));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        root.getChildren().addAll(manaIconImage);
        setManaIconImageLights();
    }

    private void setAppearanceOfTexts() {
        for (int i = 0; i < 4; i++) {
            textsOfBattle[i].setFont(FontAppearance.FONT_BUTTON);
            if (i == 1 || i == 2)
                textsOfBattle[i].setFill(Color.WHITE);
        }
        textsOfBattle[0].setFill(Color.WHITE);
        textsOfBattle[1].setLayoutX(Main.WIDTH_OF_WINDOW / 7.78);
        textsOfBattle[1].setLayoutY(Main.HEIGHT_OF_WINDOW / 9.86);
        textsOfBattle[2].setLayoutX(Main.WIDTH_OF_WINDOW / 1.286);
        textsOfBattle[2].setLayoutY(Main.HEIGHT_OF_WINDOW / 9.86);
        textsOfBattle[3].setFill(ColorAppearance.BACKGROUND_DATA_CARDS);
        textsOfBattle[3].setLayoutX(12 * Main.WIDTH_OF_WINDOW / 14.85);
        textsOfBattle[3].setLayoutY(0.73 * Main.HEIGHT_OF_WINDOW / 5.3);
        root.getChildren().addAll(textsOfBattle[1], textsOfBattle[2], textsOfBattle[3]);
    }

    public void setAppearanceOfCells() {
        for (CellAppearance[] cellAppearances : board) {
            for (CellAppearance cellAppearance : cellAppearances) {
                cellAppearance.setCellAppearance();
            }
        }
    }


    private void disPlay() {
        Main.getWindow().setScene(battleScene);
        setManaIconImageLights();
    }

    private void handleEvents() {
        textsOfBattle[0].setOnMouseClicked(e -> {
            currentBattleAppearance = null;
            Battle.getCurrentBattle().endingGame();
            new MainMenu();
        });
        textsOfBattle[6].setOnMouseClicked(event -> new GraveYardAppearance());

        endTurnButton.setOnMouseClicked(e -> {
            currentBattleAppearance = null;
            new MainMenu();
        });

        graveYardButton.setOnMouseClicked(event -> new GraveYardAppearance());
    }

    public HandAppearance getHandAppearance() {
        return handAppearance;
    }

    public static BattleAppearance getCurrentBattleAppearance() {
        return currentBattleAppearance;
    }

    public void setManaIconImageLights() {
        int mana = Account.getLoginUser().getPlayer().getMana();
        for (int i = 0; i < manaIconImage.length; i++) {
            if (i < mana)
                manaIconImage[i].setOpacity(1);
            else manaIconImage[i].setOpacity(0.3);
        }
    }

    public Group getRoot() {
        return root;
    }

    public CellAppearance[][] getBoard() {
        return board;
    }

    public Rectangle[][] getBoardBackGround() {
        return boardBackGround;
    }

    public MinionAppearance getMinionAppearanceOfBattle(String cardName, boolean isHand) {
        for (MinionAppearance minionAppearance : minionAppearanceOfBattle) {
            if (minionAppearance.getMinion().getName().trim().toLowerCase().equals(cardName.toLowerCase().trim()))
                if(minionAppearance.isInHand() == isHand)
                    return minionAppearance;

        }
        return null;
    }

    public void insert() {
        this.handAppearance = new HandAppearance(root);
    }

    public ArrayList<MinionAppearance> getMinionAppearanceOfBattle() {
        return minionAppearanceOfBattle;
    }

    public void setHandAppearance(HandAppearance handAppearance) {
        this.handAppearance = handAppearance;
    }

    public Cell getCurrentSelectedCell() {
        return currentSelectedCell;
    }

    public void setCurrentSelectedCell(Cell currentSelectedCell) {
        this.currentSelectedCell = currentSelectedCell;
    }
}

