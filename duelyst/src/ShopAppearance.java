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
    private Rectangle[][] shownCards = new Rectangle[2][5];
    private ImageView imageOfBackGround;
    private Text[] titles = {new Text("HEROES"), new Text("MINIONS"), new Text("SPELLS"), new Text("ITEMS")};
    private Rectangle fillMenu = new Rectangle(Main.WIDTH_OF_WINDOW / 10, Main.HEIGHT_OF_WINDOW);
    private ImageView rightDirection;
    private ImageView leftDirection;
    private static Rectangle[] demoCards = new Rectangle[30];

    {
        try {
            rightDirection = new ImageView(new Image(new FileInputStream("arrowright.png")));
            leftDirection = new ImageView(new Image(new FileInputStream("leftarrow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    ShopAppearance() {
        initializeDemoCards();
        initializeCards();
        setBackGround();
        addNodes();
        locateNodes();
        display();
    }

    private void initializeDemoCards() {
        for (int i = 0; i < demoCards.length; i++) {
            demoCards[i] = new Rectangle((Main.WIDTH_OF_WINDOW - (fillMenu.getWidth()) - shownCards.length * 70) / 6, Main.HEIGHT_OF_WINDOW / 2.6);
            if (i < 10) {
                demoCards[i].setFill(Color.RED);
            } else if (i < 20) {
                demoCards[i].setFill(Color.BLUE);
            } else {
                demoCards[i].setFill(Color.BLACK);
            }
        }
    }

    private void initializeCards() {
        for (int i = 0; i < shownCards.length; i++) {
            for (int j = 0; j < shownCards[i].length; j++) {
                shownCards[i][j] =demoCards[i*j];
                shownCards[i][j].setOpacity(0.4);
                final Rectangle temp = shownCards[i][j];
                shownCards[i][j].setOnMouseEntered(e -> temp.setOpacity(1));
                shownCards[i][j].setOnMouseExited(e -> temp.setOpacity(0.4));
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
        for (Rectangle[] totalCard : shownCards) {
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
        for (int i = 0; i < shownCards.length; i++) {
            for (int j = 0; j < shownCards[i].length; j++) {
                if (j == 0) {
                    if (i == 0) {
                        shownCards[i][j].setLayoutX(fillMenu.getWidth() + Main.WIDTH_OF_WINDOW / 25);
                        shownCards[i][j].setLayoutY(fillMenu.getHeight() / 20);
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
        double x = shopScene.getWidth() * 28 / 29;
        double y = 18.5 * shopScene.getHeight() / 40;
        rightDirection.setLayoutX(x);
        rightDirection.setLayoutY(y);
        leftDirection.setLayoutX(shopScene.getWidth() * 3.2 / 31);
        leftDirection.setLayoutY(y);
    }

    private void display() {
        Main.getWindow().setScene(shopScene);
        handleEvents();
    }

    private void handleEvents() {
    }
}

