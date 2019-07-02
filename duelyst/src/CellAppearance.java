import Appearance.ColorAppearance;
import Appearance.ExceptionEndGame;
import Appearance.MinionAppearance;
import Cards.Card;
import Cards.Hero;
import Cards.Minion;
import Cards.Spell;
import GameGround.Battle;
import GameGround.Cell;
import controller.GameController;
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
                // in this case we want to insert a card!
                try {
                    insertCard();
                } catch (ExceptionEndGame exceptionEndGame) {
                    new EndGameAppearance();
                }
            } else if (BattleAppearance.getCurrentBattleAppearance().getItemAppearance().getSelectedItem() != null) {
                useItem();
            } else if (BattleAppearance.getCurrentBattleAppearance().getSpecialPowerAppearance().isSelected()) {
                MinionAppearance minionAppearance = BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle(Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero(), false);
                minionAppearance.specialPower(0.95 * this.cellRectangle.getLayoutX(), 0.92 * cellRectangle.getLayoutY());
                try {
                    // TODO: delete it !
                    if (Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getName().equalsIgnoreCase("Arash")) {
                        for (int i = 0; i < 9; i++) {
                            if (i == Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getYCoordinate() - 1)
                                continue;
                            Cell cell = Battle.getCurrentBattle().getCellFromBoard(Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getXCoordinate(), i + 1);
                            if (cell.getCard() != null && cell.getCard() instanceof Hero) {
                                ((Minion) cell.getCard()).changeHealthPoint(-4);
                                BattleAppearance.getCurrentBattleAppearance().setShapeOfHealthHeroTexts();
                            }
                        }
                    } else if (Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getName().equalsIgnoreCase("ezhdehaye haftsar"))
                        Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero().setCanCounterAttack(false);
                    else if (Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getName().equalsIgnoreCase("rakhsh"))
                        Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero().setStunFake();
                    else if (Battle.getCurrentBattle().getPlayerOne().getMainDeck().getHero().getName().equalsIgnoreCase("simorgh")) {
                        for (int i = 0; i < Battle.getCurrentBattle().minionsOfCurrentPlayer(Battle.getCurrentBattle().getPlayerTwo()).size(); i++)
                            Battle.getCurrentBattle().minionsOfCurrentPlayer(Battle.getCurrentBattle().getPlayerTwo()).get(i).setStunFake();
                    }
                    BattleAppearance.getCurrentBattleAppearance().deleteExtraMinions();
                    GameController.useSpecialPower(cell.getRow() + 1, cell.getCol() + 1, Battle.getCurrentBattle());
                } catch (ExceptionEndGame exceptionEndGame) {
                    new EndGameAppearance();
                }
                BattleAppearance.getCurrentBattleAppearance().getSpecialPowerAppearance().setSelectedFalse();
            } else {
                if (BattleAppearance.getCurrentBattleAppearance().getCurrentSelectedCell() == null) {
                    if (this.cell.getCard() == null) {
                        return;
                    }
                    // in this case we select a card!
                    selectCard();
                } else {
                    if (BattleAppearance.getCurrentBattleAppearance().getCurrentSelectedCell() == this.cell) {
                        undoSelectedCard();
                        return;
                    }
                    if (this.cell.getCard() == null) {
                        // in this case we want to move!
                        try {
                            moveCard();
                        } catch (ExceptionEndGame exceptionEndGame) {
                            new EndGameAppearance();
                        }
                    } else {
                        // in this case we want to attack!
                        try {
                            attackCard();
                        } catch (ExceptionEndGame exceptionEndGame) {
                            new EndGameAppearance();
                        }
                    }
                    BattleAppearance.getCurrentBattleAppearance().getBoardBackGround()[BattleAppearance.getCurrentBattleAppearance().getCurrentSelectedCell().getRow()][BattleAppearance.getCurrentBattleAppearance().getCurrentSelectedCell().getCol()].setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
                    BattleAppearance.getCurrentBattleAppearance().setCurrentSelectedCell(null);
                    Battle.getCurrentBattle().selectCardOrItem(null);
                    BattleAppearance.getCurrentBattleAppearance().setFlagsItemsAppearance();
                }
            }
        });
        BattleAppearance.getCurrentBattleAppearance().deleteExtraMinions();
    }

    private void useItem() {
        int x = this.cell.getRow() + 1;
        int y = this.cell.getCol() + 1;
        try {
            try {
                this.cellRectangle.setFill(new ImagePattern(new Image(new FileInputStream("spell_action.gif"))));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            this.cellRectangle.setOpacity(1);
            try {
                if (BattleAppearance.getCurrentBattleAppearance().getItemAppearance().getSelectedItem().getName().equalsIgnoreCase("noosh daroo")) {
                    int r = new Random().nextInt(Battle.getCurrentBattle().minionsOfCurrentPlayer(Battle.getCurrentBattle().getPlayerOne()).size());
                    System.out.println("\n\nitem effect at " + Battle.getCurrentBattle().minionsOfCurrentPlayer(Battle.getCurrentBattle().getPlayerOne()).get(r).getName().concat("\n"));
                    Battle.getCurrentBattle().minionsOfCurrentPlayer(Battle.getCurrentBattle().getPlayerOne()).get(r).changeHealthPoint(6);
                }
                else if (BattleAppearance.getCurrentBattleAppearance().getItemAppearance().getSelectedItem().getName().equalsIgnoreCase("majoone mana")) {
                    Battle.getCurrentBattle().getPlayerOne().changeManaFake(2);
                }
                else if (BattleAppearance.getCurrentBattleAppearance().getItemAppearance().getSelectedItem().getName().equalsIgnoreCase("king wisdom")) {
                    Battle.getCurrentBattle().getPlayerOne().setKingWisdomActive(true);
                }
                GameController.useItem(x, y, Battle.getCurrentBattle());
            } catch (RuntimeException e) {
                e.printStackTrace();
            } finally {
                BattleAppearance.getCurrentBattleAppearance().getItemAppearance().setSelectedItemNull();
                BattleAppearance.getCurrentBattleAppearance().setManaIconImageLights();
            }
        } catch (ExceptionEndGame exceptionEndGame) {
            new EndGameAppearance();
        }
    }

    private void undoSelectedCard() {
        BattleAppearance.getCurrentBattleAppearance().getBoardBackGround()[BattleAppearance.getCurrentBattleAppearance().getCurrentSelectedCell().getRow()][BattleAppearance.getCurrentBattleAppearance().getCurrentSelectedCell().getCol()].setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
        BattleAppearance.getCurrentBattleAppearance().setCurrentSelectedCell(null);
        Battle.getCurrentBattle().selectCardOrItem(null);
    }

    private void attackCard() throws ExceptionEndGame {
        MinionAppearance defender = BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle((Minion) this.cell.getCard(), false);
        MinionAppearance minionAppearance = BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle((Minion) Battle.getCurrentBattle().getSelectedCard(), false);
        String result = Battle.getCurrentBattle().attack(this.cell.getCard().getId(), false, null);
        System.out.println(result);
        if (result.contains("attacked to")) {
            if (minionAppearance != null)
                minionAppearance.attack();
            if (defender != null) {
                defender.hit();
                if (defender.getMinion().isCanCounterAttack()) {
                    defender.attack();
                    minionAppearance.hit();
                }
            }
        } else ErrorOnBattle.display(result);
    }

    private void moveCard() throws ExceptionEndGame {
        String result = Battle.getCurrentBattle().movingCard(cell.getRow() + 1, cell.getCol() + 1);
        System.out.println(result);
        if (result.contains("successfully")) {
            MinionAppearance minionAppearance = BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle((Minion) Battle.getCurrentBattle().getSelectedCard(), false);
            if (minionAppearance != null)
                minionAppearance.move(0.975 * this.cellRectangle.getLayoutX() - minionAppearance.getImageView().getLayoutX(), 0.92 * this.cellRectangle.getLayoutY() - minionAppearance.getImageView().getLayoutY());
        } else ErrorOnBattle.display(result);
        BattleAppearance.getCurrentBattleAppearance().setFlagsItemsAppearance();
    }

    private void selectCard() {
        boolean isSelectDone = Battle.getCurrentBattle().setSelectedCard(this.cell);
        if (!isSelectDone) {
            ErrorOnBattle.display("you cant select this card");
            return;
        }
        BattleAppearance.getCurrentBattleAppearance().setCurrentSelectedCell(this.cell);
        BattleAppearance.getCurrentBattleAppearance().getBoardBackGround()[cell.getRow()][cell.getCol()].setFill(ColorAppearance.COLOR_CELL_CLICKED);
        System.out.println(this.cell.getCard().getName().concat(" in position ") + (cell.getRow() + 1) + " - " + (cell.getCol() + 1) + " successfully selected");
    }

    private void insertCard() throws ExceptionEndGame {
        Card card = BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCard();
        String result = Battle.getCurrentBattle().insertingCardFromHand(card.getName().toLowerCase().trim(), cell.getRow() + 1, cell.getCol() + 1);
        System.out.println(result);
        if (result.contains("successfully")) {
            BattleAppearance.getCurrentBattleAppearance().setAppearanceOfCells();
            if (card instanceof Spell) {
                try {
                    // TODO: 2019-06-30
                    this.cellRectangle.setFill(new ImagePattern(new Image(new FileInputStream("spell_action.gif"))));
                    this.cellRectangle.setOpacity(1);
                    if (card.getName().equalsIgnoreCase("total disarm")) {
                        if (cell.getCard() != null)
                            ((Minion) cell.getCard()).setTotalDisarmFake();
                    } else if (card.getName().trim().equalsIgnoreCase("fireball"))
                        Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero().changeHealth(-4);
                    else if (card.getName().equalsIgnoreCase("lighting bolt")) {
                        Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero().changeHealth(-8);
                    } else if (card.getName().equalsIgnoreCase("all disarm")) {
                        for (int i = 0; i < Battle.getCurrentBattle().minionsOfCurrentPlayer(Battle.getCurrentBattle().getPlayerTwo()).size(); i++)
                            Battle.getCurrentBattle().minionsOfCurrentPlayer(Battle.getCurrentBattle().getPlayerTwo()).get(i).setCanCounterAttack(false);
                    } else if (card.getName().equalsIgnoreCase("kings guard")) {
                        Battle.getCurrentBattle().getPlayerTwo().getMainDeck().getHero().changeHealth(-200);
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            } else {
                MinionAppearance minionAppearance = BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle((Minion) BattleAppearance.getCurrentBattleAppearance().getHandAppearance().getSelectedCard(), true);
                if (minionAppearance == null)
                    return;
                minionAppearance.setInHand(false);
                BattleAppearance.getCurrentBattleAppearance().setAppearanceOfCells();

            }
            BattleAppearance.getCurrentBattleAppearance().getHandAppearance().insert();
            BattleAppearance.getCurrentBattleAppearance().insert();
            BattleAppearance.getCurrentBattleAppearance().setManaIconImageLights();
        } else
            ErrorOnBattle.display(result);
        BattleAppearance.getCurrentBattleAppearance().getHandAppearance().setSelectedCardNull();
    }

    public void add(Group group) {
        if (!group.getChildren().contains(cellRectangle))
            group.getChildren().addAll(cellRectangle);
    }

    public Rectangle getCellRectangle() {
        return cellRectangle;
    }


    public void setCellAppearance() {
        checkFlagsItems();
        if (cell.getCard() != null) {
            Card card = cell.getCard();
            MinionAppearance minionAppearance = BattleAppearance.getCurrentBattleAppearance().getMinionAppearanceOfBattle((Minion) card, false);
            if (minionAppearance == null)
                return;
            minionAppearance.add(false);
            minionAppearance.setLocation(0.975 * this.cellRectangle.getLayoutX(), 0.92 * this.cellRectangle.getLayoutY());
            minionAppearance.breathing();
            minionAppearance.getImageView().setOpacity(1);
            minionAppearance.setInHand(false);
            minionAppearance.setInInBoard(true);
        }
    }

    public void checkFlagsItems() {
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

            BattleAppearance.getCurrentBattleAppearance().setShapeOfHealthHeroTexts();

            if (!check) {
                this.cellRectangle.setFill(ColorAppearance.COLOR_RECTANGLE_BOARD);
                this.cellRectangle.setOpacity(0.05);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
