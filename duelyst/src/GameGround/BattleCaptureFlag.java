package GameGround;

import Data.AI;
import Data.GameData;
import Data.Player;
import effects.Card;
import effects.Minion;

import java.util.ArrayList;
import java.util.Random;

public class BattleCaptureFlag extends Battle {

    private int numberOfFlags;
    private ArrayList<Minion> minionsHaveFlag;
    private SinglePlayerModes singlePlayerModes;

    public BattleCaptureFlag(Player playerOne, Player playerTwo, int numberOfFlags) {
        super(playerOne, playerTwo, GameMode.MULTI_PLAYER, BattleType.CAPTURE_FLAG);
        this.numberOfFlags = numberOfFlags;
        this.minionsHaveFlag = new ArrayList<>();
        this.singlePlayerModes = null;
        setGameDate();
        setFlagsInBoard();
        currentBattle = this;
        // Multi Player
    }

    public BattleCaptureFlag(Player playerOne, int numberOfFlags, SinglePlayerModes singlePlayerModes) {
        super(playerOne, AI.getCurrentAIPlayer(), GameMode.SINGLE_PLAYER, BattleType.CAPTURE_FLAG);
        this.numberOfFlags = numberOfFlags;
        this.minionsHaveFlag = new ArrayList<>();
        this.singlePlayerModes = singlePlayerModes;
        setGameDate();
        setFlagsInBoard();
        currentBattle = this;
        // Single Player
    }

    @Override
    public StringBuilder showGameInfo() {
        StringBuilder toPrint = super.showGameInfo();
        if (minionsHaveFlag.size() == 0)
            toPrint.append("no minion has flag yet");
        else {
            for (Minion minion : minionsHaveFlag) {
                toPrint.append(minion.getName()).append(" has flag").append(" from ").append(minion.getUserName()).append("\n");
            }
        }
        return toPrint;
    }

    private void setGameDate() {
        switch (gameMode) {
            case SINGLE_PLAYER:
                gameDataPlayerTwo = null;
                switch (singlePlayerModes) {
                    case STORY:
                        gameDataPlayerOne = new GameData("mode three and opponent is AI");
                        break;
                    case CUSTOM:
                        gameDataPlayerOne = new GameData("opponent is AI and custom game ->  mode : capture flag");
                        break;
                }
                break;
            case MULTI_PLAYER:
                gameDataPlayerOne = new GameData(playerTwo.getUserName());
                gameDataPlayerTwo = new GameData(playerOne.getUserName());
                break;
        }
    }

    private void setFlagsInBoard() {
        Random r = new Random();
        for (int i = 0; i < this.numberOfFlags / 2; i++) {
            int x = r.nextInt(6);
            int y = r.nextInt(6);
            Cell cell = this.board.getCells()[x - 1][y - 1];

            while ((x == 3 && y == 0) || cell.hasFlag()) {
                x = r.nextInt(6);
                y = r.nextInt(6);
                cell = this.board.getCells()[x - 1][y - 1];
            }
            cell.setFlag(true);
        }
        for (int i = 0; i < this.numberOfFlags - this.numberOfFlags / 2; i++) {
            int x = r.nextInt(5);
            int y = r.nextInt(6);
            Cell cell = this.board.getCells()[5 + x - 1][y - 1];

            while ((x == 3 && y == 9) || cell.hasFlag()) {
                x = r.nextInt(10);
                y = r.nextInt(6);
                cell = this.board.getCells()[5 + x - 1][y - 1];
            }
            cell.setFlag(true);
        }
    }

    @Override
    public String movingCard(int x, int y) {
        if (selectedCard == null)
            return "first you have to select card";

        Minion minion = (Minion) this.selectedCard;
        int x0 = minion.getXCoordinate();
        int y0 = minion.getYCoordinate();

        Cell cellFirst = this.board.getCells()[x0 - 1][y0 - 1];
        Cell cellDestination = this.board.getCells()[x - 1][y - 1];

        if (Cell.distance(cellDestination, cellFirst) > minion.getDistanceCanMove() || cellDestination.getCard() != null)
            return "invalid target  ";
        if (!minion.isCanMove())
            return "this minion can't move yet";
        cellDestination.setCard(minion);
        minion.setCanMove(false);
        cellFirst.setCard(null);
        minion.setCoordinate(x, y);
        if (cellDestination.getItem() != null) {
            whoseTurn().addItemToCollectAbleItems(cellDestination.getItem());
            cellDestination.setItem(null);
        }

        // cell has buff ?!

        if (cellDestination.hasFlag()) {
            minion.setHasFlag(true);
            cellDestination.setFlag(false);
            whoseTurn().changeNumberOfHoldingFlags(1);
            this.minionsHaveFlag.add(minion);
            return "minion successfully moved to " + x + " - " + y + " and captured the flag";
        }
        return "minion successfully moved to " + x + " - " + y;
    }

    @Override
    public String insertingCardFromHand(String cardName, int x, int y) {
        Card card = whoseTurn().getCardFromHand(cardName);
        Cell cellTarget = this.board.getCells()[x - 1][y - 1];

        if (card == null)
            return "invalid card name";
        if (card instanceof Minion){
            if (cellTarget.getCard() != null || !board.isCoordinateAvailable(cellTarget, whoseTurn(), this))
                return "invalid target";
            if (whoseTurn().getMana() < ((Minion) card).getManaPoint())
                return "you don't have enough mana to insert this card";
            card.setUserName(whoseTurn().getUserName());
            cellTarget.setCard(card);
            ((Minion) card).setCoordinate(x, y);
            whoseTurn().lessMana(((Minion) card).getManaPoint());
            whoseTurn().removeCardFromHand(card);

            if (cellTarget.getItem() != null){
                whoseTurn().addItemToCollectAbleItems(cellTarget.getItem());
                cellTarget.setItem(null);
            }

            this.selectedCard = card;

            if (cellTarget.hasFlag()){

                cellTarget.setFlag(false);
                ((Minion) card).setHasFlag(true);
                whoseTurn().changeNumberOfHoldingFlags(1);
                minionsHaveFlag.add((Minion) card);

                return "card successfully inserted and captured the flag too";
            }
            return "card successfully inserted";
        }
        // spell
        return "card successfully inserted";
    }
}
