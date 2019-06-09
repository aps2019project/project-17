

import Appearance.ColorAppearance;
import Appearance.FontAppearance;
import Appearance.MinionAppearance;
import Data.AI;
import Data.Account;
import GameGround.Battle;
import GameGround.BattleHoldingFlag;
import GameGround.BattleKillHero;
import GameGround.Cell;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BattleAppearance {
    private Group root;
    private Scene shopScene;
    private CellAppearance[][] board;
    private Rectangle[][] boardBackGround;
    private Rectangle[] shapeOfHealthHero = new Rectangle[2];
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
        setShapeOfHealthHero();
        setManaIcons();
        setUserNames();
        setFlag();
        disPlay();
        handleEvents();
    }

    private void init() {
        this.root = new Group();
        this.shopScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
        this.board = new CellAppearance[5][9];
        this.boardBackGround = new Rectangle[5][9];
        this.textsOfBattle = new Text[]{new Text("End Turn"), new Text("Pooya"), new Text(AI.getCurrentAIPlayer().getUserName()), new Text(Integer.toString(Battle.getCurrentBattle().getPlayerTwo().getMana()).concat("  /  9"))};
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
                final int i0 = i;
                final int j0 = j;
                boardBackGround[i][j].setOnMouseEntered(e -> boardBackGround[i0][j0].setOpacity(0.6));
                boardBackGround[i][j].setOnMouseExited(e -> boardBackGround[i0][j0].setOpacity(0.3));
            }
            root.getChildren().addAll(boardBackGround[i]);
        }
    }

    private void initPlaceOfHeroes() {
        int x = Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getXCoordinate();
        int y = Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getYCoordinate();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == x - 1 && j == y - 1) {
                    try {
                        board[i][j].getCellRectangle().setFill(new ImagePattern(new Image(new FileInputStream("test.png"))));
                        board[i][j].getCellRectangle().setOpacity(1);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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
        for (int i = 1; i < 4; i++)
            textsOfBattle[i].setFont(FontAppearance.FONT_BUTTON);
        textsOfBattle[1].setLayoutX(Main.WIDTH_OF_WINDOW / 7.78);
        textsOfBattle[1].setLayoutY(Main.HEIGHT_OF_WINDOW / 9.86);
        textsOfBattle[2].setLayoutX(Main.WIDTH_OF_WINDOW / 1.286);
        textsOfBattle[2].setLayoutY(Main.HEIGHT_OF_WINDOW / 9.86);
        textsOfBattle[3].setFill(ColorAppearance.BACKGROUND_DATA_CARDS);
        textsOfBattle[3].setLayoutX(12 * Main.WIDTH_OF_WINDOW / 14.85);
        textsOfBattle[3].setLayoutY(0.73 * Main.HEIGHT_OF_WINDOW  / 5.3);
        root.getChildren().addAll(textsOfBattle[1], textsOfBattle[2], textsOfBattle[3]);
    }

    private void setShapeOfHealthHero() {
        shapeOfHealthHero[0] = new Rectangle(40, 40);
        shapeOfHealthHero[1] = new Rectangle(40, 40);
        try {
            shapeOfHealthHero[0].setFill(new ImagePattern(new Image(new FileInputStream("hp_hero.png"))));
            shapeOfHealthHero[1].setFill(new ImagePattern(new Image(new FileInputStream("hp_hero.png"))));
            shapeOfHealthHero[0].setLayoutX(Main.WIDTH_OF_WINDOW / 14.85);
            shapeOfHealthHero[0].setLayoutY(Main.HEIGHT_OF_WINDOW / 5.3);
            shapeOfHealthHero[1].setLayoutX(13.25 * Main.WIDTH_OF_WINDOW / 14.85);
            shapeOfHealthHero[1].setLayoutY(Main.HEIGHT_OF_WINDOW / 5.3);
            root.getChildren().addAll(shapeOfHealthHero);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setFlag() {
        if (Battle.getCurrentBattle() instanceof BattleKillHero)
            return;
        if (Battle.getCurrentBattle() instanceof BattleHoldingFlag) {
            Cell cell = ((BattleHoldingFlag) Battle.getCurrentBattle()).getCellOfFlag();
            try {
                board[cell.getRow()][cell.getRow()].getCellRectangle().setFill(new ImagePattern(new Image(new FileInputStream("flag.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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
        endTurnButton.setOnMouseClicked(e -> new MainMenu());
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

