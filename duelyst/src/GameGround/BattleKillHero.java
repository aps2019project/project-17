package GameGround;

import Data.AI;
import Data.GameData;
import Data.Player;
import effects.AttackType;
import effects.Minion;

public class BattleKillHero extends Battle {

    private SinglePlayerModes singlePlayerModes;

    public BattleKillHero(Player playerOne, Player playerTwo) {
        super(playerOne, playerTwo, GameMode.MULTI_PLAYER);
        setGameData();
        setPrice();
        currentBattle = this;
    }

    public BattleKillHero(Player playerOne, SinglePlayerModes singlePlayerModes) {
        super(playerOne, AI.getCurrentAIPlayer(), GameMode.SINGLE_PLAYER);
        this.singlePlayerModes = singlePlayerModes;
        setGameData();
        setPrice();
        currentBattle = this;
    }

    private void setGameData() {
        switch (this.gameMode) {
            case SINGLE_PLAYER:
                gameDataPlayerTwo = null;
                switch (singlePlayerModes) {
                    case STORY:
                        gameDataPlayerOne = new GameData("mode one");
                        break;
                    case CUSTOM:
                        gameDataPlayerOne = new GameData("custom game ->  mode : kill hero");
                        break;
                }
                break;
            case MULTI_PLAYER:
                gameDataPlayerOne = new GameData(playerTwo.getUserName());
                gameDataPlayerTwo = new GameData(playerOne.getUserName());
        }
    }


    @Override
    public StringBuilder showGameInfo() {
        StringBuilder toPrint = super.showGameInfo();
        toPrint.append("hero of first player : ").append(playerOne.getMainDeck().getHero().getHealthPoint()).append("\n");
        toPrint.append("hero of second player : ").append(playerTwo.getMainDeck().getHero().getHealthPoint()).append("\n");
        return toPrint;
    }

    @Override
    public String movingCard(int x, int y) {

        String toReturnFormSuper = super.movingCard(x, y);
        if (!toReturnFormSuper.equals("ok"))
            return toReturnFormSuper;
        super.check();
        // if cell has buff ?!
        return this.selectedCard.getId() + " moved to " + x + " - " + y;
    }

    @Override
    public String insertingCardFromHand(String cardName, int x, int y) {
        String toReturnFromSuper = super.insertingCardFromHand(cardName, x, y);
        if (!toReturnFromSuper.equals("ok"))
            return toReturnFromSuper;
        super.check();
        return "card successfully inserted";
    }

    @Override
    protected void setPrice() {
        switch (gameMode) {
            case SINGLE_PLAYER:
                switch (singlePlayerModes) {
                    case STORY:
                        this.price = 500;
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
    public void endGame() {
        if (playerOne.getMainDeck().getHero().getHealthPoint() <= 0) {
            playerTwoWon();
            return;
        }
        if (playerTwo.getMainDeck().getHero().getHealthPoint() <= 0) {
            playerTwoWon();
            currentBattle = null;
        }
    }

    @Override
    public String deletedDeadMinions() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getAllMinion().size(); i++) {
            Minion minion = getAllMinion().get(i);
            if (minion.getHealthPoint() <= 0) {
                if (minion.getAttackType().equals(AttackType.ON_DEATH))
                    minion.useSpecialPower(minion.getXCoordinate(), minion.getYCoordinate());

                Cell cell = getCellFromBoard(minion.getXCoordinate(), minion.getYCoordinate());
                whoseTurn().addCardToGraveYard(minion);
                stringBuilder.append(minion.getName()).append(" died \n");
                cell.setCard(null);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void endTurn() {
        super.endingTurn();
    }
}
