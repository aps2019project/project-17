package GameGround;

import Data.*;
import effects.Card;
import effects.Hero;
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
        setPrice();
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
        setPrice();
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
        String returnOFSuper = super.movingCard(x, y);
        if (!returnOFSuper.equals("ok"))
            return returnOFSuper;

        Minion minion = (Minion) this.selectedCard;
        Cell cellDestination = getCellFromBoard(x, y);
        // cell has buf ??

        if (minion.isHasFlag())
            cellOfFlag = cellDestination;

        if (cellDestination.hasFlag()) {
            minion.setHasFlag(true);
            cellDestination.setFlag(false);
            whoHasFlag = whoseTurn();
            timeHoldingFlag = 0;
            return selectedCard.getId() + " moved to " + x + " - " + y + " and capture the flag";
        }
        super.check();
        return selectedCard.getId() + " moved to " + x + " - " + y;
    }

    @Override
    public String insertingCardFromHand(String cardName, int x, int y) {
        String returningFromSuper = super.insertingCardFromHand(cardName, x, y);
        if (!returningFromSuper.equals("ok"))
            return returningFromSuper;

        Card card = whoHasFlag.getCardFromHand(cardName);
        Cell cellDestination = getCellFromBoard(x, y);

        if (card instanceof Minion) {
            if (cellDestination.hasFlag()) {
                ((Minion) card).setHasFlag(true);
                cellDestination.setFlag(false);
                whoHasFlag = whoseTurn();
                timeHoldingFlag = 0;
                return "card successfully inserted and capture the flag";
            }
            return "card successfully inserted";
        }
        //spell
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
            if (getAllMinion().get(i) instanceof Hero)
                continue;
            if (getAllMinion().get(i).getHealthPoint() > 0)
                continue;
            Minion minion = getAllMinion().get(i);
            whoseTurn().addCardToGraveYard(minion);
            if (minion.isHasFlag()) {
                whoHasFlag = null;
                timeHoldingFlag = 0;
                cellOfFlag = getCellFromBoard(minion.getXCoordinate(), minion.getYCoordinate());
                cellOfFlag.setFlag(true);
            }
            stringBuilder.append(minion.getName()).append(" died \n");
            Cell cell = getCellFromBoard(minion.getXCoordinate(), minion.getYCoordinate());
            cell.setFlag(true);
            cell.setCard(null);
        }
        return stringBuilder.toString();
    }

    @Override
    public void endGame() {
        if (whoHasFlag == null)
            return;

        if (timeHoldingFlag >= 6) {
            if (whoHasFlag.equals(playerOne)) {
                gameDataPlayerOne.setMatchState(MatchState.WIN);
                gameDataPlayerTwo.setMatchState(MatchState.LOSE);
                for (int i = 0; i < Account.getAccounts().size(); i++) {
                    if (Account.getAccounts().get(i).getUserName().equals(playerOne.getUserName())) {
                        Account.getAccounts().get(i).incrementNumbOfWins();
                        Account.getAccounts().get(i).addGamaData(gameDataPlayerOne);
                        Account.getAccounts().get(i).changeDaric(price);
                        continue;
                    }
                    if (Account.getAccounts().get(i).getUserName().equals(playerTwo.getUserName())) {
                        Account.getAccounts().get(i).incrementNumbOfLose();
                        Account.getAccounts().get(i).addGamaData(gameDataPlayerTwo);
                    }
                }
                situationOfGame = playerOne.getUserName() + " win from " + playerTwo.getUserName() + " and earn " + price;
            } else if (whoHasFlag.equals(playerTwo)) {
                gameDataPlayerTwo.setMatchState(MatchState.WIN);
                gameDataPlayerOne.setMatchState(MatchState.LOSE);
                for (int i = 0; i < Account.getAccounts().size(); i++) {
                    if (Account.getAccounts().get(i).getUserName().equals(playerTwo.getUserName())) {
                        Account.getAccounts().get(i).incrementNumbOfWins();
                        Account.getAccounts().get(i).addGamaData(gameDataPlayerTwo);
                        Account.getAccounts().get(i).changeDaric(price);
                        continue;
                    }
                    if (Account.getAccounts().get(i).getUserName().equals(playerOne.getUserName())) {
                        Account.getAccounts().get(i).incrementNumbOfLose();
                        Account.getAccounts().get(i).addGamaData(gameDataPlayerOne);
                    }
                }
                situationOfGame = playerTwo.getUserName() + " win from " + playerOne.getUserName() + " and earn " + price;
            }
        }
    }

    @Override
    public void endTurn() {
        if (whoHasFlag != null){
            this.timeHoldingFlag ++;
        }
        switch (gameMode){
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
