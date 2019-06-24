package GameGround;

import Appearance.ExceptionEndGame;
import Cards.Card;
import Data.AI;
import Data.GameData;
import Data.Player;
import Effects.enums.AttackType;
import Cards.Minion;

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
    public String movingCard(int x, int y) throws ExceptionEndGame {

        String toReturnFormSuper = super.movingCard(x, y);
        if (!toReturnFormSuper.equals("ok"))
            return toReturnFormSuper;
        super.check();
        return this.selectedCard.getId() + " successfully moved to " + x + " - " + y;
    }

    @Override
    public String insertingCardFromHand(String cardName, int x, int y) throws ExceptionEndGame {
        String toReturnFromSuper = super.insertingCardFromHand(cardName.trim().toLowerCase(), x, y);
        Card card = whoseTurn().getCardFromHand(cardName);
        if (!toReturnFromSuper.equals("ok"))
            return toReturnFromSuper;

        whoseTurn().removeCardFromHand(card);
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
    public void endGame() throws ExceptionEndGame {
        if (playerOne.getMainDeck().getHero().getHealthPoint() <= 0) {
            System.out.println("health player one : " + playerOne.getMainDeck().getHero().getHealthPoint() + "  name : " + playerOne.getMainDeck().getHero().getName());
            playerTwoWon();
            return;
        }
        if (playerTwo.getMainDeck().getHero().getHealthPoint() <= 0) {
            System.out.println("health player two : " + playerTwo.getMainDeck().getHero().getHealthPoint() + "  name : " + playerTwo.getMainDeck().getHero().getName());
            playerOneWon();
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
    public void endTurn() throws ExceptionEndGame {
        super.endingTurn();
    }
}
