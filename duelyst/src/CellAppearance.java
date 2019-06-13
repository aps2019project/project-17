import Appearance.ColorAppearance;
import Appearance.MinionAppearance;
import Cards.Card;
import Cards.Minion;
import Cards.Spell;
import GameGround.Battle;
import GameGround.Cell;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class CellAppearance {

    private Rectangle cellRectangle;
    private Cell cell;

    public CellAppearance(Cell cell) {
        cellRectangle = new Rectangle(Main.WIDTH_OF_WINDOW / 17, Main.HEIGHT_OF_WINDOW / 10);
        cellRectangle.setArcWidth(10);
        cellRectangle.setArcHeight(10);
        cellRectangle.setOpacity(0.05);
        this.cellRectangle.setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
        this.cell = cell;
        eventHandle();
    }

    private void eventHandle() {
        this.cellRectangle.setOnMouseClicked(e -> {

            if (BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCard() != null) {
                // in this case we wanna insert a card!
                Card card = BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCard();
                String result = Battle.getCurrentBattle().insertingCardFromHand(card.getName(), cell.getRow() + 1, cell.getCol() + 1);
                System.out.println(result);
                if (result.contains("successfully")) {
                    if (card instanceof Spell) {
                        try {
                            this.cellRectangle.setFill(new ImagePattern(new Image(new FileInputStream("spell_action.gif"))));
                            this.cellRectangle.setOpacity(1);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCard().getName(), true).setInHand(false);
                        BattleAppearance.getCurrentBattleAppearance().setAppearanceOfCells();

                    }
                    BattleAppearance.getCurrentBattleAppearance().getHandAppearance().insert();
                    BattleAppearance.getCurrentBattleAppearance().insert();
                    BattleAppearance.getCurrentBattleAppearance().setManaIconImageLights();
                } else {
                    ErrorOnBattle.display(result);
                }
                BattleAppearance.getCurrentBattleAppearance().getHandAppearance().setSelectedCard(null);
            } else {
                if (BattleAppearance.getCurrentBattleAppearance().getCurrentSelectedCell() == null) {
                    if (this.cell.getCard() == null)
                        return;
                    // in this case we select a card!
                    BattleAppearance.getCurrentBattleAppearance().setCurrentSelectedCell(this.cell);
                    BattleAppearance.getCurrentBattleAppearance().getBoardBackGround()[cell.getRow()][cell.getCol()].setFill(ColorAppearance.COLOR_CELL_CLICKED);
                    System.out.println(Battle.getCurrentBattle().selectCardOrItem(cell.getCard().getId()));
                } else {
                    if (this.cell.getCard() == null) {
                        // in this case we want to move!
                        String result = Battle.getCurrentBattle().movingCard(cell.getRow() + 1, cell.getCol() + 1);
                        System.out.println(result);
                        if (result.contains("successfully")) {
                            MinionAppearance minionAppearance = BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(Battle.getCurrentBattle().getSelectedCard().getName(), false);
                            minionAppearance.move(0.975 * this.cellRectangle.getLayoutX() - minionAppearance.getImageView().getLayoutX(), 0.92 * this.cellRectangle.getLayoutY() - minionAppearance.getImageView().getLayoutY());
                        } else ErrorOnBattle.display(result);
//                        BattleAppearance.getCurrentBattleAppearance().setAppearanceOfCells();
                    } else {
                        // in this case we want to attack!
                        String result = Battle.getCurrentBattle().attack(this.cell.getCard().getId(), false, null);
                        System.out.println(result);
                        if (result.contains("attacked to")) {
                            MinionAppearance minionAppearance = BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(Battle.getCurrentBattle().getSelectedCard().getName(), false);
                            minionAppearance.attack();
                            if (((Minion) cell.getCard()).isCanCounterAttack()) {
                                BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(cell.getCard().getName(), false).attack();
                            }
                        } else ErrorOnBattle.display(result);
                    }
                    BattleAppearance.getCurrentBattleAppearance().getBoardBackGround()[BattleAppearance.getCurrentBattleAppearance().getCurrentSelectedCell().getRow()][BattleAppearance.getCurrentBattleAppearance().getCurrentSelectedCell().getCol()].setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
                    BattleAppearance.getCurrentBattleAppearance().setCurrentSelectedCell(null);
                    Battle.getCurrentBattle().selectCardOrItem(null);
                }
            }

        });
    }

    public void add(Group group) {// TODO: 6/8/2019
        group.getChildren().addAll(cellRectangle);
    }

    public Rectangle getCellRectangle() {
        return cellRectangle;
    }


    public void setCellAppearance() {
        this.cellRectangle.setOpacity(1);
        boolean check = false;
        try {
            if (cell.hasFlag()) {
                this.cellRectangle.setFill(new ImagePattern(new Image(new FileInputStream("flag.gif"))));
                check = true;
            } else if (cell.getItem() != null) {
                this.cellRectangle.setFill(new ImagePattern(new Image(new FileInputStream("item.gif"))));
                check = true;
            }
            if (cell.getEffect() != null) {
                cellRectangle.setFill(Color.ORANGE);
                cellRectangle.setOpacity(0.8);
                check = true;
            }
            if (cell.getCard() != null) {
                Card card = cell.getCard();
                MinionAppearance minionAppearance = BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(card.getName(), false);
                minionAppearance.add(false);
                minionAppearance.setLocation(0.975 * this.cellRectangle.getLayoutX(), 0.92 * this.cellRectangle.getLayoutY());
                minionAppearance.breathing();
                minionAppearance.getImageView().setOpacity(1);
                minionAppearance.setInHand(false);
                minionAppearance.setInInBoard(true);
            }

            if (!check) {
                this.cellRectangle.setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
                this.cellRectangle.setOpacity(0.05);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

// TODO: 2019-06-11 a function that fill for items and flags!
