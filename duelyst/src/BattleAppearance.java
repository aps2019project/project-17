

import Appearance.ColorAppearance;
import Appearance.FontAppearance;
import Appearance.MinionAppearance;
import Data.AI;
import GameGround.Battle;
import GameGround.Cell;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BattleAppearance {
    private Group root;
    private Scene shopScene;
    private CellAppearance[][] board;
    private Rectangle[][] boardBackGround;
    private Text[] textsOfBattle;
    private Rectangle[] manaIconImage;
    private Rectangle endTurnButton;
    private HandAppearance handAppearance;
    private static BattleAppearance currentBattleAppearance;

    public BattleAppearance() {
        currentBattleAppearance = this;
        init();
        setBackGround();
        initBoardBG();
        initHand();
        initializeCells();
        initPlaceOfHeroes();
        addCells();
        locateNodes();
        setFontsAndColor();
        setEndTurnButton();
        setImagesOfPlayers();
        setManaIcons();
        setUserNames();
        disPlay();
        handleEvents();
        MinionAppearance minionAppearance = new MinionAppearance(null, "decepticleprime", root);
        minionAppearance.setLocation(500,500);
//        minionAppearance.breathing();
        minionAppearance.move(100,100,300,300);
//        minionAppearance.attack();
//        minionAppearance.death();
//        minionAppearance.idle();
    }

    private void init() {
        this.root = new Group();
        this.shopScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
        this.board = new CellAppearance[5][9];
        this.boardBackGround = new Rectangle[5][9];
        this.textsOfBattle = new Text[]{new Text("End Turn"), new Text("Pooya"), new Text(AI.getCurrentAIPlayer().getUserName())};
        this.manaIconImage = new Rectangle[9];
    }

    private void initBoardBG() {
        for (int i = 0; i < boardBackGround.length; i++) {
            for (int j = 0; j < boardBackGround[i].length; j++) {
                boardBackGround[i][j] = new Rectangle(Main.WIDTH_OF_WINDOW / 19, Main.HEIGHT_OF_WINDOW / 10);
                boardBackGround[i][j].setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
                boardBackGround[i][j].setOpacity(0.3);
                if (j == 0) {
                    if (i == 0) {
                        boardBackGround[i][j].setLayoutX(Main.WIDTH_OF_WINDOW / 4.2);
                        boardBackGround[i][j].setLayoutY(Main.HEIGHT_OF_WINDOW / 4);
                    } else {
                        boardBackGround[i][j].setLayoutX(boardBackGround[i - 1][j].getLayoutX());
                        boardBackGround[i][j].setLayoutY(boardBackGround[i - 1][j].getLayoutY() + Main.HEIGHT_OF_WINDOW / 9.2);
                    }
                } else {
                    boardBackGround[i][j].setLayoutX(boardBackGround[i][j - 1].getLayoutX() + Main.WIDTH_OF_WINDOW / 18);
                    boardBackGround[i][j].setLayoutY(boardBackGround[i][j - 1].getLayoutY());
                }
            }
            root.getChildren().addAll(boardBackGround[i]);
        }
    }

    private void initPlaceOfHeroes() {
        int x = Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getXCoordinate();
        int y = Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getYCoordinate();
        int x1 = Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero().getXCoordinate();
        int y1 = Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero().getYCoordinate();
        Cell cellHeroOne = Battle.getCurrentBattle().getCellFromBoard(x, y);
        Cell cellHeroTwo = Battle.getCurrentBattle().getCellFromBoard(x1, y1);
    }

    private void setBackGround() {
        Image image;
        try {
            image = new Image(new FileInputStream("board1.png"));
            ImageView imageOfBackGround = new ImageView(image);
            imageOfBackGround.fitWidthProperty().bind(shopScene.widthProperty());
            imageOfBackGround.fitHeightProperty().bind(shopScene.heightProperty());
            root.getChildren().add(imageOfBackGround);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initHand() {
        this.handAppearance = new HandAppearance(this.root);
    }

    private void setFontsAndColor() {
        textsOfBattle[0].setFont(FontAppearance.FONT_BUTTON);
        textsOfBattle[0].setFill(Color.WHITE);
    }

    private void setUserNames() {
        textsOfBattle[1].setFont(FontAppearance.FONT_BUTTON);
        textsOfBattle[2].setFont(FontAppearance.FONT_BUTTON);
        textsOfBattle[1].setLayoutX(Main.WIDTH_OF_WINDOW / 7.78);
        textsOfBattle[1].setLayoutY(Main.HEIGHT_OF_WINDOW / 9.86);
        textsOfBattle[2].setLayoutX(Main.WIDTH_OF_WINDOW / 1.286);
        textsOfBattle[2].setLayoutY(Main.HEIGHT_OF_WINDOW / 9.86);
        root.getChildren().addAll(textsOfBattle[1], textsOfBattle[2]);
    }

    private void setEndTurnButton() {
        endTurnButton = new Rectangle(Main.WIDTH_OF_WINDOW / 7.58, Main.HEIGHT_OF_WINDOW / 11.12);
        try {
            endTurnButton.setFill(new ImagePattern(new Image(new FileInputStream("end_turn.png"))));
            StackPane stackPane0 = new StackPane(endTurnButton, textsOfBattle[0]);
            stackPane0.setLayoutX(Main.WIDTH_OF_WINDOW * 8 / 10);
            stackPane0.setLayoutY(Main.HEIGHT_OF_WINDOW * 8.5 / 10);
            root.getChildren().addAll(stackPane0);
            stackPane0.setOnMouseEntered(event -> stackPane0.setOpacity(1));
            stackPane0.setOnMouseExited(event -> stackPane0.setOpacity(0.75));
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

    private void setManaIcons() {
        for (int i = 0; i < manaIconImage.length; i++) {
            try {
                manaIconImage[i] = new Rectangle(Main.WIDTH_OF_WINDOW / 48, Main.HEIGHT_OF_WINDOW / 27.8);
                manaIconImage[i].setFill(new ImagePattern(new Image(new FileInputStream("icon_mana2.png"))));
                manaIconImage[i].setLayoutY(Main.HEIGHT_OF_WINDOW / 8.5);
                manaIconImage[i].setLayoutX(Main.WIDTH_OF_WINDOW / 8 + (i * Main.WIDTH_OF_WINDOW / 48));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        root.getChildren().addAll(manaIconImage);
    }

    private void disPlay() {
        Main.getWindow().setScene(shopScene);
        setManaIconImageLights();
    }

    private void initializeCells() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new CellAppearance(Battle.getCurrentBattle().getBoard().getCells()[i][j]);
            }
        }
    }

    private void locateNodes() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].getCellRectangle().setLayoutX(boardBackGround[i][j].getLayoutX());
                board[i][j].getCellRectangle().setLayoutY(boardBackGround[i][j].getLayoutY());
            }
        }
    }

    private void addCells() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].add(root);
            }
        }
    }

    private void handleEvents() {
        for (CellAppearance[] cellAppearances : board) {
            for (CellAppearance cellAppearance : cellAppearances) {
                cellAppearance.handleEvents();
            }
        }
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
            if (i < mana) {
                manaIconImage[i].setOpacity(1);
            } else manaIconImage[i].setOpacity(0.5);
        }
    }

    public Group getRoot() {
        return root;
    }
}

