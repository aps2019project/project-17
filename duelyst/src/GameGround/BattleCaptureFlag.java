package GameGround;

import Data.AI;
import Data.GameData;
import Data.MatchState;
import Data.Player;
import controller.GameController;
import effects.*;

import java.util.ArrayList;
import java.util.Random;

public class BattleCaptureFlag extends Battle {

    private int numberOfFlags;
    private ArrayList<Minion> minionsHaveFlag;
    private SinglePlayerModes singlePlayerModes;

    public BattleCaptureFlag(Player playerOne, Player playerTwo, int numberOfFlags) {
        super(playerOne, playerTwo, GameMode.MULTI_PLAYER);
        this.numberOfFlags = numberOfFlags;
        this.minionsHaveFlag = new ArrayList<>();
        this.singlePlayerModes = null;
        setGameDate();
        setFlagsInBoard();
        setPrice();
        currentBattle = this;
    }

    public BattleCaptureFlag(Player playerOne, int numberOfFlags, SinglePlayerModes singlePlayerModes) {
        super(playerOne, AI.getCurrentAIPlayer(), GameMode.SINGLE_PLAYER);
        this.numberOfFlags = numberOfFlags;
        this.minionsHaveFlag = new ArrayList<>();
        this.singlePlayerModes = singlePlayerModes;
        setGameDate();
        setFlagsInBoard();
        setPrice();
        currentBattle = this;
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
            int x = r.nextInt(5) + 1;
            int y = r.nextInt(5) + 1;
            while (x < 0 || y < 0) {
                x = r.nextInt() % 5 + 1;
                y = r.nextInt() % 5 + 1;
            }
            Cell cell = this.board.getCells()[x - 1][y - 1];
            cell.setFlag(true);
        }
        for (int i = 0; i < this.numberOfFlags - this.numberOfFlags / 2; i++) {
            int x = r.nextInt(5);
            int y = r.nextInt(5);
            while (x < 0 || y < 0) {
                x = r.nextInt() % 5 + 1;
                y = r.nextInt() % 5 + 1;
            }
            Cell cell = this.board.getCells()[5 + x - 1][y - 1];

            while ((x == 3 && y == 9) || cell.hasFlag()) {
                x = r.nextInt(10);
                y = r.nextInt(6);
                cell = this.board.getCells()[5 + x - 1][y - 1];
            }
            cell.setFlag(true);
        }
        super.check();
    }

    @Override
    public String movingCard(int x, int y) {
        String toReturn = super.movingCard(x, y);
        if (!toReturn.equals("ok"))
            return toReturn;
        Cell cellDestination = getCellFromBoard(x, y);
        Minion minion = (Minion) this.selectedCard;
        if (cellDestination.hasFlag()) {
            minion.setHasFlag(true);
            cellDestination.setFlag(false);
            whoseTurn().changeNumberOfHoldingFlags(1);
            this.minionsHaveFlag.add(minion);
            return "minion successfully moved to " + x + " - " + y + " and captured the flag";
        }
        super.check();
        return "minion successfully moved to " + x + " - " + y;
    }

    @Override
    public String insertingCardFromHand(String cardName, int x, int y) {
        super.check();
        String returning = super.insertingCardFromHand(cardName, x, y);
        if (!returning.equals("ok"))
            return returning;

        Cell cellTarget = getCellFromBoard(x, y);
        Card card = whoseTurn().getCardFromHand(cardName);

        if (card instanceof Minion) {

            this.selectedCard = card;
            if (cellTarget.hasFlag()) {

                cellTarget.setFlag(false);
                ((Minion) card).setHasFlag(true);
                whoseTurn().changeNumberOfHoldingFlags(1);
                minionsHaveFlag.add((Minion) card);

                return "card successfully inserted and captured the flag too";
            }
            return "card successfully inserted";
        }
        super.check();
        return "card successfully inserted";
    }

    @Override
    protected void setPrice() {
        switch (gameMode) {
            case SINGLE_PLAYER:
                switch (singlePlayerModes) {
                    case STORY:
                        this.price = 1500;
                        break;
                    case CUSTOM:
                        this.price = 1000;
                        break;
                }
                break;
            case MULTI_PLAYER:
                this.price = 1000;
                break;
        }
    }

    @Override
    public String deletedDeadMinions() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getAllMinion().size(); i++) {
            if (getAllMinion().get(i).getHealthPoint() <= 0) {
                if (getAllMinion().get(i) instanceof Hero)
                    continue;
                whoseTurn().addCardToGraveYard(getAllMinion().get(i));
                if (getAllMinion().get(i).isHasFlag()) {
                    if (getAllMinion().get(i).getAttackType().equals(AttackType.ON_DEATH))
                        getAllMinion().get(i).useSpecialPower(getAllMinion().get(i).getXCoordinate(), getAllMinion().get(i).getYCoordinate());
                    minionsHaveFlag.remove(getAllMinion().get(i));
                    Cell cell = getCellFromBoard(getAllMinion().get(i).getXCoordinate(), getAllMinion().get(i).getYCoordinate());
                    cell.setFlag(true);
                    Player player = playerOne;
                    if (getAllMinion().get(i).getUserName().equals(playerTwo.getUserName()))
                        player = playerTwo;
                    player.changeNumberOfHoldingFlags(-1);
                }
                stringBuilder.append(getAllMinion().get(i).getName()).append(" died \n");
                getCellFromBoard(getAllMinion().get(i).getXCoordinate(), getAllMinion().get(i).getYCoordinate()).setCard(null);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void endGame() {
        if (playerOne.getHoldingFlags() >= numberOfFlags / 2) {
            playerOneWon();
            return;
        }
        if (playerTwo.getHoldingFlags() >= numberOfFlags / 2) {
            playerTwoWon();
        }
    }

    @Override
    public void endTurn() {
        super.endTurn();
        switch (gameMode) {
            case SINGLE_PLAYER:
                super.endTurn();
                ((AI) AI.getCurrentAIPlayer()).action();
                super.endTurn();
                break;
            case MULTI_PLAYER:
                super.endTurn();
                break;
        }
    }
}