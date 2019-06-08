

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BattleAppearance {
    private Group root = new Group();
    private Scene shopScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
    private CellAppearance[][] board = new CellAppearance[5][9];

    public BattleAppearance() {
        setBackGround();
        disPlay();
        initializeCells();
        addCells();
        locateNodes();
        handleEvents();
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

    private void disPlay() {
        Main.getWindow().setScene(shopScene);
    }

    private void initializeCells() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new CellAppearance();
            }
        }
    }

    private void locateNodes() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j == 0) {
                    if (i == 0) {
                        board[i][j].getCellRectangle().setLayoutX(Main.WIDTH_OF_WINDOW / 4.5);
                        board[i][j].getCellRectangle().setLayoutY(Main.HEIGHT_OF_WINDOW /3.5);
                        continue;
                    }
                    board[i][j].getCellRectangle().setLayoutX(board[i - 1][j].getCellRectangle().getLayoutX());
                    board[i][j].getCellRectangle().setLayoutY(board[i - 1][j].getCellRectangle().getLayoutY() + Main.HEIGHT_OF_WINDOW /11);
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

    private void handleEvents(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].handleEvents();
            }
        }
    }
}

