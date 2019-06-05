import Appearance.FontAppearance;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


class ShopAppearance {
    private Group root = new Group();
    private Scene shopScene = new Scene(root, Main.WIDTH_OF_WINDOW, Main.HEIGHT_OF_WINDOW);
    private Rectangle[][] ShownCards = new Rectangle[2][5];
    private ImageView imageOfBackGround;
    private Text[] titles = {new Text("HEROES"), new Text("MINIONS"), new Text("SPELLS"), new Text("ITEMS")};
    private Rectangle fillMenu = new Rectangle(Main.WIDTH_OF_WINDOW / 10, Main.HEIGHT_OF_WINDOW);
    private ImageView rightDirection;
    private ImageView leftDirection;

    {
        try {
            rightDirection = new ImageView(new Image(new FileInputStream("arrowright.png")));
            leftDirection = new ImageView(new Image(new FileInputStream("leftarrow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    ShopAppearance() {
        initializeCards();
        setBackGround();
        addNodes();
        locateNodes();
        display();
    }

    private void initializeCards() {
        for (int i = 0; i < ShownCards.length; i++) {
            for (int j = 0; j < ShownCards[i].length; j++) {
                ShownCards[i][j] = new Rectangle((Main.WIDTH_OF_WINDOW - (fillMenu.getWidth()) - ShownCards.length * 70) / 6, Main.HEIGHT_OF_WINDOW / 2.6);
                ShownCards[i][j].setOpacity(0.4);
                final Rectangle temp = ShownCards[i][j];
                ShownCards[i][j].setOnMouseEntered(e -> temp.setOpacity(1));
                ShownCards[i][j].setOnMouseExited(e -> temp.setOpacity(0.4));
            }
        }

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
            image = new Image(new FileInputStream("bg0.png"));
            imageOfBackGround = new ImageView(image);
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
        for (Rectangle[] totalCard : ShownCards) {
            root.getChildren().addAll(totalCard);
        }
        root.getChildren().addAll(rightDirection, leftDirection);
    }

    private void locateNodes() {
        for (int i = 0; i < titles.length; i++) {
            if (i == 0) {
                titles[i].setLayoutX(Main.HEIGHT_OF_WINDOW / 50);
                titles[i].setLayoutY(Main.WIDTH_OF_WINDOW / 25);
                continue;
            }
            titles[i].setLayoutX(titles[i - 1].getLayoutX());
            titles[i].setLayoutY(titles[i - 1].getLayoutY() + Main.HEIGHT_OF_WINDOW / 7);
        }
        for (int i = 0; i < ShownCards.length; i++) {
            for (int j = 0; j < ShownCards[i].length; j++) {
                if (j == 0) {
                    if (i == 0) {
                        ShownCards[i][j].setLayoutX(fillMenu.getWidth() + Main.WIDTH_OF_WINDOW / 25);
                        ShownCards[i][j].setLayoutY(fillMenu.getHeight() / 20);
                        continue;
                    }
                    ShownCards[i][j].setLayoutX(ShownCards[i - 1][j].getLayoutX());
                    ShownCards[i][j].setLayoutY(ShownCards[i - 1][j].getLayoutY() + Main.HEIGHT_OF_WINDOW/2);
                    continue;
                }
                ShownCards[i][j].setLayoutY(ShownCards[i][j - 1].getLayoutY());
                ShownCards[i][j].setLayoutX(ShownCards[i][j - 1].getLayoutX() + Main.WIDTH_OF_WINDOW/6);
            }
        }
        double x = shopScene.getWidth() * 30 / 31;
        double y = 18.5 * shopScene.getHeight() / 40;
        rightDirection.setLayoutX(x);
        rightDirection.setLayoutY(y);
        leftDirection.setLayoutX(shopScene.getWidth() * 2.8 / 31);
        leftDirection.setLayoutY(y);
    }

    private void display() {
        Main.getWindow().setScene(shopScene);
        handleEvents();
    }

    private void handleEvents() {
    }
}

