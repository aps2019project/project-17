package GameGround;

import Appearance.ExceptionEndGame;
import Data.*;
import Effects.enums.AttackType;
import Cards.Card;
import Cards.Hero;
import Cards.Minion;

import java.util.Random;

public class BattleHoldingFlag extends Battle {
    private Player whoHasFlag;
    private int timeHoldingFlag;
    private SinglePlayerModes singlePlayerModes;
    private Cell cellOfFlag;

    public BattleHoldingFlag(Player playerOne, Player playerTwo) {
        super(playerOne, playerTwo, GameMode.MULTI_PLAYER);
        this.whoHasFlag = null;
        this.timeHoldingFlag = 0;
        currentBattle = this;
        Random r = new Random();
        int x = r.nextInt(5);
        int y = r.nextInt(9);
        while (x <= 0 || y <= 0 || (x == 3 && y == 1) || (x == 3 && y == 9) || this.board.getCells()[x - 1][y - 1].getItem() != null || this.board.getCells()[x - 1][y - 1].getCard() != null) {
            x = r.nextInt(5);
            y = r.nextInt(9);
        }
        this.cellOfFlag = this.board.getCells()[x - 1][y - 1];
        this.cellOfFlag.setFlag(true);
        setGameData();
        setPrice();
    }

    public BattleHoldingFlag(Player playerOne, SinglePlayerModes singlePlayerModes) {
        super(playerOne, AI.getCurrentAIPlayer(), GameMode.SINGLE_PLAYER);
        this.singlePlayerModes = singlePlayerModes;
        this.whoHasFlag = null;
        this.timeHoldingFlag = 0;
        currentBattle = this;
        Random r = new Random();
        int x = r.nextInt(5) + 1;
        int y = r.nextInt(9) + 1;
        while (x <= 0 || y <= 0) {
            x = r.nextInt(5) + 1;
            y = r.nextInt(9) + 1;
        }
        this.cellOfFlag = this.board.getCells()[x - 1][y - 1];
        this.cellOfFlag.setFlag(true);
        setPrice();
        setGameData();
    }

    private void setGameData() {
        switch (gameMode) {
            case SINGLE_PLAYER:
                gameDataPlayerTwo = null;
                switch (singlePlayerModes) {
                    case STORY:
                        gameDataPlayerOne = new GameData("mode two");
                        break;
                    case CUSTOM:
                        gameDataPlayerOne = new GameData("custom game  mode : holding flag");
                        break;
                }
                break;
            case MULTI_PLAYER:
                gameDataPlayerOne = new GameData(this.playerTwo.getUserName());
                gameDataPlayerTwo = new GameData(this.playerOne.getUserName());
                break;
        }
    }

    @Override
    public StringBuilder showGameInfo() {
        StringBuilder toPrint = super.showGameInfo();
        toPrint.append("flag is in Coordinate ->").append(cellOfFlag.getRow() + 1).append(" - ").append(cellOfFlag.getCol() + 1);
        if (whoHasFlag == null) {
            toPrint.append("\nno one has flag in this current time");
        } else
            toPrint.append("\n").append(whoHasFlag.getUserName()).append(" has flag");
        return toPrint;
    }

    @Override
    public String movingCard(int x, int y) throws ExceptionEndGame {
        String returnOFSuper = super.movingCard(x, y);
        if (!returnOFSuper.equals("ok"))
            return returnOFSuper;

        Minion minion = (Minion) this.selectedCard;
        Cell cellDestination = getCellFromBoard(x, y);

        if (minion.isHasFlag())
            cellOfFlag = cellDestination;

        if (cellDestination.hasFlag()) {
            minion.setHasFlag(true);
            whoHasFlag = whoseTurn();
            timeHoldingFlag = 0;
            cellDestination.setFlag(false);
            return selectedCard.getId() + " successfully moved to " + x + " - " + y + " and captured the flag";
        }
        super.check();
        return selectedCard.getId() + " successfully moved to " + x + " - " + y;
    }

    @Override
    public String insertingCardFromHand(String cardName, int x, int y) throws ExceptionEndGame {
        String returningFromSuper = super.insertingCardFromHand(cardName, x, y);
        if (!returningFromSuper.equals("ok"))
            return returningFromSuper;

        Card card = whoseTurn().getCardFromHand(cardName.toLowerCase().trim());
        Cell cellDestination = getCellFromBoard(x, y);

        if (card instanceof Minion) {
            whoseTurn().removeCardFromHand(card);
            if (cellDestination.hasFlag()) {
                ((Minion) card).setHasFlag(true);
                cellDestination.setFlag(false);
                whoHasFlag = whoseTurn();
                timeHoldingFlag = 0;
                super.check();
                return "card successfully inserted and captured the flag";
            }
            super.check();
            return "card successfully inserted";
        }
        whoseTurn().removeCardFromHand(card);
        super.check();
        return "card successfully inserted";
    }

    @Override
    protected void setPrice() {
        this.price = 1000;
    }

    @Override
    public String deletedDeadMinions() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getAllMinion().size(); i++) {
            if (getAllMinion().get(i).getHealthPoint() > 0)
                continue;
            Minion minion = getAllMinion().get(i);
            if (whoseTurn().getUserName().equals(minion.getUserName()))
                whoseTurn().addCardToGraveYard(minion);
            else if (theOtherPlayer().getUserName().equalsIgnoreCase(minion.getName().trim()))
                theOtherPlayer().addCardToGraveYard(minion);
            if (minion.getAttackType().equals(AttackType.ON_DEATH))
                minion.useSpecialPower(minion.getXCoordinate(), minion.getYCoordinate());
            if (minion.isHasFlag()) {
                whoHasFlag = null;
                timeHoldingFlag = 0;
                cellOfFlag = getCellFromBoard(minion.getXCoordinate(), minion.getYCoordinate());
                cellOfFlag.setFlag(true);
                minion.setHasFlag(false);
            }
            stringBuilder.append(minion.getName()).append(" died \n");
            Cell cell = getCellFromBoard(minion.getXCoordinate(), minion.getYCoordinate());
            cell.setCard(null);
        }
        return stringBuilder.toString();
    }

    @Override
    public void endGame() throws ExceptionEndGame {
        if (whoHasFlag == null)
            return;

        if (timeHoldingFlag >= 8) {
            if (whoHasFlag.equals(playerOne)) {
                playerOneWon();
            } else if (whoHasFlag.equals(playerTwo)) {
                playerTwoWon();
            }
        }
    }

    @Override
    public void endTurn() throws ExceptionEndGame {
        if (whoHasFlag != null) {
            switch (gameMode) {
                case SINGLE_PLAYER:
                    this.timeHoldingFlag += 2;
                    break;
                case MULTI_PLAYER:
                    this.timeHoldingFlag++;
                    break;
            }
        }
        super.endingTurn();
    }

    public Cell getCellOfFlag() {
        return cellOfFlag;
    }
}
