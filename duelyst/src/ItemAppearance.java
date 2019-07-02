import Appearance.ExceptionEndGame;
import Appearance.FontAppearance;
import Cards.Item;
import GameGround.Battle;
import controller.GameController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class ItemAppearance {

    private Item selectedItem;
    private Item[] items = new Item[6];
    private Rectangle[] itemBackGrounds = new Rectangle[6];
    private Rectangle[] itemInfo = new Rectangle[6];
    private Group root;


    ItemAppearance(Group root, Item[] items) {

        primaryInitialize(root, items);
        setItemList();
        locateIcons();
        add();
        eventHandler();
        //addInformationOfCards();
    }

    private void primaryInitialize(Group root, Item[] passedItems) {
        this.root = root;
        System.arraycopy(passedItems, 0, this.items, 0, passedItems.length);
    }

    private void setItemList() {
        for (int i = 0; i < items.length; i++) {
            itemBackGrounds[i] = new Rectangle(Main.WIDTH_OF_WINDOW / 15, Main.HEIGHT_OF_WINDOW / 9);
            try {
                itemBackGrounds[i].setFill(new ImagePattern(new Image(new FileInputStream("itemShow.gif"))));
                itemBackGrounds[i].setOpacity(0.7);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < items.length; i++)
            itemInfo[i] = new Rectangle(Main.WIDTH_OF_WINDOW / 15, Main.HEIGHT_OF_WINDOW / 9);

    }


    private void setInformationOfItems(int i, Item item) {
        //if (item != null) {
        Text text = new Text(item.getName());
        text.setFill(Color.WHITE);
        text.setFont(FontAppearance.FONT_INFORMATION_ITEM);
        StackPane stackPane = new StackPane(itemInfo[i], text);
        try {
            itemInfo[i].setFill(new ImagePattern(new Image(new FileInputStream("info_card.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        stackPane.setLayoutX(itemBackGrounds[i].getLayoutX() * 9);
        stackPane.setLayoutY(itemBackGrounds[i].getLayoutY());
        root.getChildren().add(stackPane);
        stackPane.setVisible(false);
        itemInfo[i].setVisible(false);
        itemBackGrounds[i].setOnMouseEntered(e -> {
            stackPane.setVisible(true);
            itemInfo[i].setVisible(true);
        });
        itemBackGrounds[i].setOnMouseExited(e -> {
            stackPane.setVisible(false);
            itemInfo[i].setVisible(false);
        });
        //}
    }

    private void locateIcons() {
        for (int i = 0; i < itemBackGrounds.length; i++) {
            if (itemBackGrounds[i] != null && itemInfo[i] != null && items[i] != null) {
                double x = Main.WIDTH_OF_WINDOW / 100;
                double y = Main.HEIGHT_OF_WINDOW / 2.5;
                if (i == 0) {
                    itemBackGrounds[i].setLayoutX(x);
                    itemBackGrounds[i].setLayoutY(y);
                } else {
                    itemBackGrounds[i].setLayoutX(x);
                    itemBackGrounds[i].setLayoutY(y + i * Main.WIDTH_OF_WINDOW / 15);
                }
                setInformationOfItems(i, items[i]);
            }
        }
    }

    private void add() {
        for (int i = 0; i < itemBackGrounds.length; i++) {
            if (itemBackGrounds[i] != null && itemInfo[i] != null && items[i] != null) {
                this.root.getChildren().addAll(itemBackGrounds[i]);
                this.root.getChildren().addAll(itemInfo[i]);
            }
        }
        locateIcons();
    }

    void delete(Group root) {
        for (int i = 0; i < itemBackGrounds.length; i++) {
            if (itemBackGrounds[i] != null) {
                root.getChildren().removeAll(itemBackGrounds[i]);
                root.getChildren().removeAll(itemInfo[i]);
            }
        }
    }

    public void setSelectedItemNull() {
        if (selectedItem == null) {
            return;
        }
        for (Rectangle itemBackGround : itemBackGrounds) {
            if (itemBackGround.getOpacity() == 1) {
                itemBackGround.setOpacity(0.7);
                break;
            }
        }
        try {
            GameController.selectItem(null);
        } catch (ExceptionEndGame exceptionEndGame) {
            new EndGameAppearance();
        }
        this.selectedItem = null;
        System.out.println("selected item became null");
    }

    private void eventHandler() {
        for (int i = 0; i < itemBackGrounds.length; i++) {
            if (itemBackGrounds[i] != null && itemInfo[i] != null && items[i] != null) {
                final int j = i;
                itemBackGrounds[i].setOnMouseClicked(event -> {
                    try {
                        GameController.selectItem(items[j]);
                    } catch (ExceptionEndGame exceptionEndGame) {
                        new EndGameAppearance();
                    }
                    for (int k = 0; k <itemBackGrounds.length ; k++) {
                        if(k!=j){
                            itemBackGrounds[k].setOpacity(0.7);
                        }
                    }
                    if (selectedItem != null)
                        if (selectedItem == items[j]) {
                            itemBackGrounds[j].setOpacity(0.7);
                            selectedItem = null;
                            System.out.println("selected Item became null");
                            return;
                        }
                    itemBackGrounds[j].setOpacity(1);
                    selectedItem = items[j];
                    BattleAppearance.getCurrentBattleAppearance().getHandAppearance().setSelectedCardNull();
                    BattleAppearance.getCurrentBattleAppearance().getSpecialPowerAppearance().setSelectedFalse();
                });
            }
        }
    }

    public Item getSelectedItem() {
        return selectedItem;
    }
}
