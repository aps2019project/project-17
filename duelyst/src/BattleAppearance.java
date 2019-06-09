

import Appearance.FontAppearance;
import Data.AI;
import Data.Account;
import GameGround.Battle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BattleAppearance {
    private Group root;
    private Scene shopScene;
    private CellAppearance[][] board;
    private Text[] textsOfBattle;
    private Rectangle[] manaIconImage;
    private Rectangle endTurnButton;
    private HandAppearance handAppearance;
    private static BattleAppearance currentBattleAppearance;

    public BattleAppearance() {
        currentBattleAppearance = this;
        init();
        setBackGround();
        initHand();
        initializeCells();
        addCells();
        locateNodes();
        setFontsAndColor();
        setEndTurnButton();
        setImagesOfPlayers();
        setManaIcons();
        setUserNames();
        disPlay();
        handleEvents();
    }

    private void init() {
        this.root = new Group();
        this.shopScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
        this.board = new CellAppearance[5][9];
        this.textsOfBattle = new Text[]{new Text("End Turn"), new Text("Pooya"), new Text(AI.getCurrentAIPlayer().getUserName())};
        this.manaIconImage = new Rectangle[9];
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
//            stackPane0.setOpacity(0.75);
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
    }

    private void initializeCells() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new CellAppearance(Battle.getCurrentBattle().getBoard().getCells()[i][j]);
                System.out.println("i  :  " + i + "  " + " j  :  " + j);
            }
        }
    }

    private void locateNodes() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j == 0) {
                    if (i == 0) {
                        board[i][j].getCellRectangle().setLayoutX(Main.WIDTH_OF_WINDOW / 4.5);
                        board[i][j].getCellRectangle().setLayoutY(Main.HEIGHT_OF_WINDOW / 3.5);
                        continue;
                    }
                    board[i][j].getCellRectangle().setLayoutX(board[i - 1][j].getCellRectangle().getLayoutX());
                    board[i][j].getCellRectangle().setLayoutY(board[i - 1][j].getCellRectangle().getLayoutY() + Main.HEIGHT_OF_WINDOW / 11);
                    continue;
                }
                board[i][j].getCellRectangle().setLayoutX(board[i][j - 1].getCellRectangle().getLayoutX() + Main.WIDTH_OF_WINDOW / 17);
                board[i][j].getCellRectangle().setLayoutY(board[i][j - 1].getCellRectangle().getLayoutY());
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
}

