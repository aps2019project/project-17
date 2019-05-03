package GameGround;

import Data.AI;
import Data.GameData;
import Data.Player;
import effects.Card;
import effects.Minion;

import java.util.Random;

public class BattleHoldingFlag extends Battle {
    private Player whoHasFlag;
    private int timeHoldingFlag;
    private SinglePlayerModes singlePlayerModes;
    private Cell cellOfFlag;

    public BattleHoldingFlag(Player playerOne, Player playerTwo) {
        super(playerOne, playerTwo, GameMode.MULTI_PLAYER, BattleType.HOLDING_FLAG);
        this.whoHasFlag = null;
        this.timeHoldingFlag = 0;
        currentBattle = this;
        Random r = new Random();
        int x = r.nextInt(10);
        int y = r.nextInt(6);
        this.cellOfFlag = this.board.getCells()[x - 1][y - 1];
        this.cellOfFlag.setFlag(true);
        setGameData();
        // Multi Player
    }

    public BattleHoldingFlag(Player playerOne, SinglePlayerModes singlePlayerModes) {
        super(playerOne, AI.getCurrentAIPlayer(), GameMode.SINGLE_PLAYER, BattleType.HOLDING_FLAG);
        this.singlePlayerModes = singlePlayerModes;
        this.whoHasFlag = null;
        this.timeHoldingFlag = 0;
        currentBattle = this;
        Random r = new Random();
        int x = r.nextInt(10);
        int y = r.nextInt(6);
        this.cellOfFlag = this.board.getCells()[x - 1][y - 1];
        this.cellOfFlag.setFlag(true);
        setGameData();
        // single player
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
        toPrint.append("flag is in Coordinate ->").append(cellOfFlag.getRow()).append(" - ").append(cellOfFlag.getCol());
        if (whoHasFlag == null) {
            toPrint.append("\nno one has flag in this current time");
        } else
            toPrint.append("\n").append(whoHasFlag.getUserName()).append(" has flag");
        return toPrint;
    }

    @Override
    public String movingCard(int x, int y) {
        if (this.selectedCard == null)
            return "firs you have to select a card";
        Minion minion = (Minion) this.selectedCard;

        Cell cellDestination = this.board.getCells()[x - 1][y - 1];
        Cell cellFirst = this.board.getCells()[minion.getXCoordinate() - 1][minion.getYCoordinate() - 1];

        if (cellDestination.getCard() != null || Cell.distance(cellFirst, cellDestination) > minion.getDistanceCanMove())
            return "invalid target";

        if (!minion.isCanMove())
            return "this card can't move";

        cellDestination.setCard(minion);

        if (cellDestination.getItem() != null)
            whoseTurn().addItemToCollectAbleItems(cellDestination.getItem());
        // cell has buf ??

        cellFirst.setCard(null);
        minion.setCanMove(false);
        minion.setCoordinate(x, y);

        if (minion.isHasFlag())
            cellOfFlag = cellDestination;

        if (cellDestination.hasFlag()) {
            minion.setHasFlag(true);
            cellDestination.setFlag(false);
            whoHasFlag = whoseTurn();
            timeHoldingFlag = 0;
            return selectedCard.getId() + " moved to " + x + " - " + y + " and capture the flag";
        }
        return selectedCard.getId() + " moved to " + x + " - " + y;
    }

    @Override
    public String insertingCardFromHand(String cardName, int x, int y) {
        Card card = whoHasFlag.getCardFromHand(cardName);
        Cell cellDestination = this.board.getCells()[x - 1][y - 1];

        if (card == null)
            return "invalid card name ";
        if (card instanceof Minion) {
            if (cellDestination == null || cellDestination.getCard() != null)
                return "invalid target";
            if (whoseTurn().getMana() < ((Minion) card).getManaPoint())
                return "you don't have enough mana to insert this card";
            if (!this.board.isCoordinateAvailable(cellDestination, whoHasFlag, this))
                return "invalid target";
            card.setUserName(whoseTurn().getUserName());
            whoseTurn().lessMana(-((Minion) card).getManaPoint());
            cellDestination.setCard(card);
            ((Minion) card).setCoordinate(x, y);
            whoseTurn().removeCardFromHand(card);
            this.selectedCard = card;
            if (cellDestination.hasFlag()) {
                ((Minion) card).setHasFlag(true);
                cellDestination.setFlag(false);
                whoHasFlag = whoseTurn();
                timeHoldingFlag = 0;
                return "card successfully inserted and capture the flag";
            }
            return "card successfully inserted";
        }
        return "card successfully inserted";
    }
}
